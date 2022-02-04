package io.github.elizayami.galaxia.common.tileentity;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.common.abstracts.tileentities.FuserZoneContents;
import io.github.elizayami.galaxia.common.block.tool_fuser.ContainerTF;
import io.github.elizayami.galaxia.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class TileEntityToolFuser extends TileEntity implements INamedContainerProvider, ITickableTileEntity
{

	public static final int TOTAL_SLOTS_COUNT = 5;

	private FuserZoneContents pickZoneContents;
	private FuserZoneContents axeZoneContents;
	private FuserZoneContents shovelZoneContents;
	private FuserZoneContents hoeZoneContents;
	private FuserZoneContents outputZoneContents;

	public TileEntityToolFuser()
	{
		super(TileEntityInit.tileEntityTypeSF);
		pickZoneContents = FuserZoneContents.createForTileEntity(1, this::canPlayerAccessInventory, this::markDirty);
		axeZoneContents = FuserZoneContents.createForTileEntity(1, this::canPlayerAccessInventory, this::markDirty);
		shovelZoneContents = FuserZoneContents.createForTileEntity(1, this::canPlayerAccessInventory, this::markDirty);
		hoeZoneContents = FuserZoneContents.createForTileEntity(1, this::canPlayerAccessInventory, this::markDirty);
		outputZoneContents = FuserZoneContents.createForTileEntity(1, this::canPlayerAccessInventory, this::markDirty);
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

	@Override
	public void tick()
	{
		if (world.isRemote)
			return;
	}

	public boolean willItemStackFit(FuserZoneContents furnaceZoneContents, int slotIndex, ItemStack itemStackOrigin)
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

	static public boolean isItemValidForPickSlot(ItemStack itemStack)
	{
		return itemStack.getToolTypes().contains(ToolType.PICKAXE) &&
				!itemStack.getToolTypes().contains(ToolType.HOE) &&
				!itemStack.getToolTypes().contains(ToolType.AXE) &&
				!itemStack.getToolTypes().contains(ToolType.SHOVEL);
	}

	static public boolean isItemValidForAxeSlot(ItemStack itemStack)
	{
		return itemStack.getToolTypes().contains(ToolType.AXE) &&
				!itemStack.getToolTypes().contains(ToolType.PICKAXE) &&
				!itemStack.getToolTypes().contains(ToolType.HOE) &&
				!itemStack.getToolTypes().contains(ToolType.SHOVEL);
	}

	static public boolean isItemValidForShovelSlot(ItemStack itemStack)
	{
		return itemStack.getToolTypes().contains(ToolType.SHOVEL) &&
				!itemStack.getToolTypes().contains(ToolType.PICKAXE) &&
				!itemStack.getToolTypes().contains(ToolType.AXE) &&
				!itemStack.getToolTypes().contains(ToolType.HOE);
	}
	
	static public boolean isItemValidForHoeSlot(ItemStack itemStack)
	{
		return itemStack.getToolTypes().contains(ToolType.HOE) &&
				!itemStack.getToolTypes().contains(ToolType.PICKAXE) &&
				!itemStack.getToolTypes().contains(ToolType.AXE) &&
				!itemStack.getToolTypes().contains(ToolType.SHOVEL);
	}
	
	static public boolean isItemValidForOutputSlot(ItemStack itemStack)
	{
		return false;
	}

	private final String PICK_SLOTS_NBT = "pickSlot";
	private final String AXE_SLOTS_NBT = "axeSlot";
	private final String SHOVEL_SLOTS_NBT = "shovelSlot";
	private final String HOE_SLOTS_NBT = "hoeSlot";
	private final String OUTPUT_SLOTS_NBT = "outputSlot";

	@Override
	public CompoundNBT write(CompoundNBT parentNBTTagCompound)
	{
		super.write(parentNBTTagCompound);

		parentNBTTagCompound.put(PICK_SLOTS_NBT, pickZoneContents.serializeNBT());
		parentNBTTagCompound.put(AXE_SLOTS_NBT, axeZoneContents.serializeNBT());
		parentNBTTagCompound.put(SHOVEL_SLOTS_NBT, shovelZoneContents.serializeNBT());
		parentNBTTagCompound.put(HOE_SLOTS_NBT, hoeZoneContents.serializeNBT());
		parentNBTTagCompound.put(OUTPUT_SLOTS_NBT, outputZoneContents.serializeNBT());
		return parentNBTTagCompound;
	}

	@Override
	public void read(BlockState blockState, CompoundNBT nbtTagCompound)
	{
		super.read(blockState, nbtTagCompound);

		CompoundNBT inventoryNBT = nbtTagCompound.getCompound(PICK_SLOTS_NBT);
		pickZoneContents.deserializeNBT(inventoryNBT);

		inventoryNBT = nbtTagCompound.getCompound(AXE_SLOTS_NBT);
		axeZoneContents.deserializeNBT(inventoryNBT);

		inventoryNBT = nbtTagCompound.getCompound(SHOVEL_SLOTS_NBT);
		shovelZoneContents.deserializeNBT(inventoryNBT);

		inventoryNBT = nbtTagCompound.getCompound(HOE_SLOTS_NBT);
		hoeZoneContents.deserializeNBT(inventoryNBT);

		inventoryNBT = nbtTagCompound.getCompound(OUTPUT_SLOTS_NBT);
		outputZoneContents.deserializeNBT(inventoryNBT);
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
		InventoryHelper.dropInventoryItems(world, blockPos, pickZoneContents);
		InventoryHelper.dropInventoryItems(world, blockPos, axeZoneContents);
		InventoryHelper.dropInventoryItems(world, blockPos, shovelZoneContents);
		InventoryHelper.dropInventoryItems(world, blockPos, hoeZoneContents);
		InventoryHelper.dropInventoryItems(world, blockPos, outputZoneContents);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container.galaxia.tool_fuser");
	}

	@Nullable
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity)
	{
		return ContainerTF.createContainerServerSide(windowID, playerInventory, pickZoneContents, 
				axeZoneContents, 
				shovelZoneContents, 
				hoeZoneContents, 
				outputZoneContents);
	}

	private ItemStack currentlySmeltingItemLastTick = ItemStack.EMPTY;
}
