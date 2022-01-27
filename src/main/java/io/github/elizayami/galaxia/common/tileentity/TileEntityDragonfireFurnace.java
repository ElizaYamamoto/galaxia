package io.github.elizayami.galaxia.common.tileentity;

import java.util.Optional;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.common.block.dragonfire_furnace.FurnaceStateData;
import io.github.elizayami.galaxia.common.abstracts.tileentities.FurnaceZoneContents;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.ContainerDFF;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.Dragonfire_Furnace;
import io.github.elizayami.galaxia.core.init.TileEntityInit;
import io.github.elizayami.galaxia.core.enums.SetBlockStateFlag;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class TileEntityDragonfireFurnace extends TileEntity implements INamedContainerProvider, ITickableTileEntity
{

	public static final int FUEL_SLOTS_COUNT = 3;
	public static final int INPUT_SLOTS_COUNT = 3;
	public static final int OUTPUT_SLOTS_COUNT = 3;
	public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

	private FurnaceZoneContents fuelZoneContents;
	private FurnaceZoneContents inputZoneContents;
	private FurnaceZoneContents outputZoneContents;

	private final FurnaceStateData furnaceStateData = new FurnaceStateData();

	public TileEntityDragonfireFurnace()
	{
		super(TileEntityInit.tileEntityTypeDFF);
		fuelZoneContents = FurnaceZoneContents.createForTileEntity(FUEL_SLOTS_COUNT, this::canPlayerAccessInventory,
				this::markDirty);
		inputZoneContents = FurnaceZoneContents.createForTileEntity(INPUT_SLOTS_COUNT, this::canPlayerAccessInventory,
				this::markDirty);
		outputZoneContents = FurnaceZoneContents.createForTileEntity(OUTPUT_SLOTS_COUNT, this::canPlayerAccessInventory,
				this::markDirty);
	}

	public boolean canPlayerAccessInventory(PlayerEntity player)
	{
		if (this.world.getTileEntity(this.pos) != this)
			return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET,
				pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	public int numberOfBurningFuelSlots()
	{
		int burningCount = 0;
		for (int burnTime : furnaceStateData.burnTimeRemainings)
		{
			if (burnTime > 0)
				++burningCount;
		}
		return burningCount;
	}

	@Override
	public void tick()
	{
		if (world.isRemote)
			return;

		ItemStack currentlySmeltingItem = getCurrentlySmeltingInputItem();

		if (!ItemStack.areItemsEqual(currentlySmeltingItem, currentlySmeltingItemLastTick))
		{
			furnaceStateData.cookTimeElapsed = 0;
		}
		currentlySmeltingItemLastTick = currentlySmeltingItem.copy();

		if (!currentlySmeltingItem.isEmpty())
		{
			int numberOfFuelBurning = burnFuel();

			if (numberOfFuelBurning > 0)
			{
				furnaceStateData.cookTimeElapsed += numberOfFuelBurning;
			}
			else
			{
				furnaceStateData.cookTimeElapsed -= 2;
			}
			if (furnaceStateData.cookTimeElapsed < 0)
			{
				furnaceStateData.cookTimeElapsed = 0;
			}

			int cookTimeForCurrentItem = getCookTime(this.world, currentlySmeltingItem);
			furnaceStateData.cookTimeForCompletion = cookTimeForCurrentItem;

			if (furnaceStateData.cookTimeElapsed >= cookTimeForCurrentItem)
			{
				smeltFirstSuitableInputItem();
				furnaceStateData.cookTimeElapsed = 0;
			}
		}
		else
		{
			furnaceStateData.cookTimeElapsed = 0;
		}

		int numberBurning = numberOfBurningFuelSlots();

		BlockState currentBlockState = world.getBlockState(this.pos);

		BlockState newBlockState = currentBlockState.with(Dragonfire_Furnace.NUMBER_BURNING, numberBurning);
		if (!newBlockState.equals(currentBlockState))
		{
			final int FLAGS = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
			world.setBlockState(this.pos, newBlockState, FLAGS);
			markDirty();
		}
	}

	private int burnFuel()
	{
		int burningCount = 0;
		boolean inventoryChanged = false;
		boolean isBurning = false;

		for (int fuelIndex = 0; fuelIndex < FUEL_SLOTS_COUNT; fuelIndex++)
		{
			if (furnaceStateData.burnTimeRemainings[fuelIndex] > 0)
			{
				--furnaceStateData.burnTimeRemainings[fuelIndex];
				++burningCount;
				isBurning = true;
			}

			if (furnaceStateData.burnTimeRemainings[fuelIndex] == 0)
			{
				ItemStack fuelItemStack = fuelZoneContents.getStackInSlot(fuelIndex);
				if (!fuelItemStack.isEmpty() && getItemBurnTime(this.world, fuelItemStack) > 0)
				{
					int burnTimeForItem = getItemBurnTime(this.world, fuelItemStack);
					furnaceStateData.burnTimeRemainings[fuelIndex] = burnTimeForItem;
					furnaceStateData.burnTimeInitialValues[fuelIndex] = burnTimeForItem;
					fuelZoneContents.decrStackSize(fuelIndex, 1);
					++burningCount;
					inventoryChanged = true;

					if (fuelItemStack.isEmpty())
					{
						ItemStack containerItem = fuelItemStack.getContainerItem();
						fuelZoneContents.setInventorySlotContents(fuelIndex, containerItem);
					}
				}
			}
		}

		BlockState currentBlockState = world.getBlockState(this.pos);
		BlockState burnBlockState = currentBlockState.with(Dragonfire_Furnace.IS_BURNING, isBurning);
		if (burnBlockState != currentBlockState)
		{
			final int FLAGS = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
			world.setBlockState(this.pos, burnBlockState, FLAGS);
			markDirty();
		}

		if (inventoryChanged)
			markDirty();
		return burningCount;
	}

	private ItemStack getCurrentlySmeltingInputItem()
	{
		return smeltFirstSuitableInputItem(false);
	}

	private void smeltFirstSuitableInputItem()
	{
		smeltFirstSuitableInputItem(true);
	}

	private ItemStack smeltFirstSuitableInputItem(boolean performSmelt)
	{
		Integer firstSuitableInputSlot = null;
		Integer firstSuitableOutputSlot = null;
		ItemStack result = ItemStack.EMPTY;

		for (int inputIndex = 0; inputIndex < INPUT_SLOTS_COUNT; inputIndex++)
		{
			ItemStack itemStackToSmelt = inputZoneContents.getStackInSlot(inputIndex);
			if (!itemStackToSmelt.isEmpty())
			{
				result = getSmeltingResultForItem(this.world, itemStackToSmelt);
				if (!result.isEmpty())
				{

					for (int outputIndex = 0; outputIndex < OUTPUT_SLOTS_COUNT; outputIndex++)
					{
						if (willItemStackFit(outputZoneContents, outputIndex, result))
						{
							firstSuitableInputSlot = inputIndex;
							firstSuitableOutputSlot = outputIndex;
							break;
						}
					}
					if (firstSuitableInputSlot != null)
						break;
				}
			}
		}

		if (firstSuitableInputSlot == null)
			return ItemStack.EMPTY;

		ItemStack returnvalue = inputZoneContents.getStackInSlot(firstSuitableInputSlot).copy();
		if (!performSmelt)
			return returnvalue;

		inputZoneContents.decrStackSize(firstSuitableInputSlot, 1);
		outputZoneContents.increaseStackSize(firstSuitableOutputSlot, result);

		markDirty();
		return returnvalue;
	}

	public boolean willItemStackFit(FurnaceZoneContents furnaceZoneContents, int slotIndex, ItemStack itemStackOrigin)
	{
		ItemStack itemStackDestination = furnaceZoneContents.getStackInSlot(slotIndex);

		if (itemStackDestination.isEmpty() || itemStackOrigin.isEmpty())
		{
			return true;
		}

		if (!itemStackOrigin.isItemEqual(itemStackDestination))
		{
			return false;
		}

		int sizeAfterMerge = itemStackDestination.getCount() + itemStackOrigin.getCount();
		if (sizeAfterMerge <= furnaceZoneContents.getInventoryStackLimit()
				&& sizeAfterMerge <= itemStackDestination.getMaxStackSize())
		{
			return true;
		}
		return false;
	}

	public static ItemStack getSmeltingResultForItem(World world, ItemStack itemStack)
	{
		Optional<FurnaceRecipe> matchingRecipe = getMatchingRecipeForInput(world, itemStack);
		if (!matchingRecipe.isPresent())
			return ItemStack.EMPTY;
		return matchingRecipe.get().getRecipeOutput().copy();
	}

	public static int getItemBurnTime(World world, ItemStack stack)
	{
		int burntime = net.minecraftforge.common.ForgeHooks.getBurnTime(stack);
		return burntime;
	}

	public static Optional<FurnaceRecipe> getMatchingRecipeForInput(World world, ItemStack itemStack)
	{
		RecipeManager recipeManager = world.getRecipeManager();
		Inventory singleItemInventory = new Inventory(itemStack);
		Optional<FurnaceRecipe> matchingRecipe = recipeManager.getRecipe(IRecipeType.SMELTING, singleItemInventory,
				world);
		return matchingRecipe;
	}

	public static int getCookTime(World world, ItemStack itemStack)
	{
		Optional<FurnaceRecipe> matchingRecipe = getMatchingRecipeForInput(world, itemStack);
		if (!matchingRecipe.isPresent())
			return 0;
		return matchingRecipe.get().getCookTime();
	}

	static public boolean isItemValidForFuelSlot(ItemStack itemStack)
	{
		return true;
	}

	static public boolean isItemValidForInputSlot(ItemStack itemStack)
	{
		return true;
	}

	static public boolean isItemValidForOutputSlot(ItemStack itemStack)
	{
		return false;
	}

	private final String FUEL_SLOTS_NBT = "fuelSlots";
	private final String INPUT_SLOTS_NBT = "inputSlots";
	private final String OUTPUT_SLOTS_NBT = "outputSlots";

	@Override
	public CompoundNBT write(CompoundNBT parentNBTTagCompound)
	{
		super.write(parentNBTTagCompound);

		furnaceStateData.putIntoNBT(parentNBTTagCompound);
		parentNBTTagCompound.put(FUEL_SLOTS_NBT, fuelZoneContents.serializeNBT());
		parentNBTTagCompound.put(INPUT_SLOTS_NBT, inputZoneContents.serializeNBT());
		parentNBTTagCompound.put(OUTPUT_SLOTS_NBT, outputZoneContents.serializeNBT());
		return parentNBTTagCompound;
	}

	@Override
	public void read(BlockState blockState, CompoundNBT nbtTagCompound)
	{
		super.read(blockState, nbtTagCompound);

		furnaceStateData.readFromNBT(nbtTagCompound);

		CompoundNBT inventoryNBT = nbtTagCompound.getCompound(FUEL_SLOTS_NBT);
		fuelZoneContents.deserializeNBT(inventoryNBT);

		inventoryNBT = nbtTagCompound.getCompound(INPUT_SLOTS_NBT);
		inputZoneContents.deserializeNBT(inventoryNBT);

		inventoryNBT = nbtTagCompound.getCompound(OUTPUT_SLOTS_NBT);
		outputZoneContents.deserializeNBT(inventoryNBT);

		if (fuelZoneContents.getSizeInventory() != FUEL_SLOTS_COUNT
				|| inputZoneContents.getSizeInventory() != INPUT_SLOTS_COUNT
				|| outputZoneContents.getSizeInventory() != OUTPUT_SLOTS_COUNT)
			throw new IllegalArgumentException("Corrupted NBT: Number of inventory slots did not match expected.");
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT updateTagDescribingTileEntityState = getUpdateTag();
		final int METADATA = 42;
		return new SUpdateTileEntityPacket(this.pos, METADATA, updateTagDescribingTileEntityState);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		CompoundNBT updateTagDescribingTileEntityState = pkt.getNbtCompound();
		BlockState blockState = world.getBlockState(pos);
		handleUpdateTag(blockState, updateTagDescribingTileEntityState);
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(BlockState blockState, CompoundNBT tag)
	{
		read(blockState, tag);
	}

	public void dropAllContents(World world, BlockPos blockPos)
	{
		InventoryHelper.dropInventoryItems(world, blockPos, fuelZoneContents);
		InventoryHelper.dropInventoryItems(world, blockPos, inputZoneContents);
		InventoryHelper.dropInventoryItems(world, blockPos, outputZoneContents);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container.galaxia.dragonfire_furnace");
	}

	@Nullable
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity)
	{
		return ContainerDFF.createContainerServerSide(windowID, playerInventory, inputZoneContents, outputZoneContents,
				fuelZoneContents, furnaceStateData);
	}

	private ItemStack currentlySmeltingItemLastTick = ItemStack.EMPTY;
}
