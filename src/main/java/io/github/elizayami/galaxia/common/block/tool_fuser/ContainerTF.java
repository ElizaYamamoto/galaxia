package io.github.elizayami.galaxia.common.block.tool_fuser;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.elizayami.galaxia.common.abstracts.tileentities.FuserZoneContents;
import io.github.elizayami.galaxia.common.tileentity.TileEntityToolFuser;
import io.github.elizayami.galaxia.core.init.TileEntityInit;

public class ContainerTF extends Container
{

	public static ContainerTF createContainerServerSide(int windowID, PlayerInventory playerInventory,
			FuserZoneContents PickZoneContents, 
			FuserZoneContents AxeZoneContents,
			FuserZoneContents ShovelZoneContents,
			FuserZoneContents HoeZoneContents, 
			FuserZoneContents OutputZoneContents, 
			FuserStateData fuserStateData)
	{
		return new ContainerTF(windowID, 
				playerInventory, 
				PickZoneContents, 
				AxeZoneContents, 
				ShovelZoneContents, 
				HoeZoneContents, 
				OutputZoneContents,
				fuserStateData);
	}

	public static ContainerTF createContainerClientSide(int windowID, PlayerInventory playerInventory,
			net.minecraft.network.PacketBuffer extraData)
	{
		FuserZoneContents PickZoneContents = FuserZoneContents.createForClientSideContainer(1);
		FuserZoneContents AxeZoneContents = FuserZoneContents.createForClientSideContainer(1);
		FuserZoneContents ShovelZoneContents = FuserZoneContents.createForClientSideContainer(1);
		FuserZoneContents HoeZoneContents = FuserZoneContents.createForClientSideContainer(1);
		FuserZoneContents outputZoneContents = FuserZoneContents.createForClientSideContainer(1);
		FuserStateData furnaceStateData = new FuserStateData();

		return new ContainerTF(windowID, 
				playerInventory,
				PickZoneContents, 
				AxeZoneContents, 
				ShovelZoneContents, 
				HoeZoneContents, 
				outputZoneContents,
				furnaceStateData);
	}

	// 0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 -
	// 8)
	// 9 - 35 = player inventory slots (which map to the InventoryPlayer slot
	// numbers 9 - 35)
	// 36 - 39 = fuel slots (furnaceStateData 0 - 3)
	// 40 - 44 = input slots (furnaceStateData 4 - 8)
	// 45 - 49 = output slots (furnaceStateData 9 - 13)

	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	public static final int FUSER_SLOTS_COUNT = 5;

	// slot index is the unique index for all slots in this container i.e. 0 - 35
	// for invPlayer then 36 - 49 for furnaceContents
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int HOTBAR_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX;
	private static final int PLAYER_INVENTORY_FIRST_SLOT_INDEX = HOTBAR_FIRST_SLOT_INDEX + HOTBAR_SLOT_COUNT;
	private static final int FIRST_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int PICK_SLOT_INDEX = FIRST_SLOT_INDEX + 1;
	private static final int AXE_SLOT_INDEX = FIRST_SLOT_INDEX + 2;
	private static final int SHOVEL_SLOT_INDEX = FIRST_SLOT_INDEX + 3;
	private static final int HOE_SLOT_INDEX = FIRST_SLOT_INDEX + 4;
	private static final int OUTPUT_SLOT_INDEX = FIRST_SLOT_INDEX + 5;

	public static final int PLAYER_INVENTORY_XPOS = 8;
	public static final int PLAYER_INVENTORY_YPOS = 129;

	// invPlayer slots 0 - 35 (hotbar 0 - 8 then main inventory 9 to 35)

	public ContainerTF(int windowID, PlayerInventory invPlayer, 
			FuserZoneContents pickZoneContents,
			FuserZoneContents axeZoneContents, 
			FuserZoneContents shovelZoneContents, 
			FuserZoneContents hoeZoneContents, 
			FuserZoneContents outputZoneContents,
			FuserStateData fusereStateData)
	{
		super(TileEntityInit.containerTypeDFF, windowID);
		if (TileEntityInit.containerTypeDFF == null)
			throw new IllegalStateException(
					"Must initialise containerTypeTF before constructing a Tool Fuser!");
		this.pickZoneContents = pickZoneContents;
		this.axeZoneContents = axeZoneContents;
		this.shovelZoneContents = shovelZoneContents;
		this.hoeZoneContents = hoeZoneContents;
		this.outputZoneContents = outputZoneContents;
		this.world = invPlayer.player.world;

		trackIntArray(fusereStateData);

		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 183;
		// Add the players hotbar to the gui - the [xpos, ypos] location of each item
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++)
		{
			int slotNumber = x;
			addSlot(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
		}

		// Add the rest of the players inventory to the gui
		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++)
		{
			for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++)
			{
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlot(new Slot(invPlayer, slotNumber, xpos, ypos));
			}
		}
		
		addSlot(new SlotPick(pickZoneContents, 1, 80, 19));

		addSlot(new SlotAxe(axeZoneContents, 1, 49, 50));
		
		addSlot(new SlotShovel(shovelZoneContents, 1, 111, 50));

		addSlot(new SlotHoe(hoeZoneContents, 1, 80, 81));

		addSlot(new SlotOutput(outputZoneContents, 1, 80, 50));
	}

	@Override
	public boolean canInteractWith(PlayerEntity player)
	{
		return pickZoneContents.isUsableByPlayer(player) && 
				axeZoneContents.isUsableByPlayer(player) && 
				shovelZoneContents.isUsableByPlayer(player) && 
				hoeZoneContents.isUsableByPlayer(player) && 
				outputZoneContents.isUsableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex)
	{
		Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack())
			return ItemStack.EMPTY;
		ItemStack sourceItemStack = sourceSlot.getStack();
		ItemStack sourceStackBeforeMerge = sourceItemStack.copy();
		boolean successfulTransfer = false;

		SlotZone sourceZone = SlotZone.getZoneFromIndex(sourceSlotIndex);

		switch (sourceZone)
		{
		case OUTPUT_ZONE: 

			successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, true);
			if (!successfulTransfer)
			{
				successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, true);
			}
			if (successfulTransfer)
			{
				sourceSlot.onSlotChange(sourceItemStack, sourceStackBeforeMerge);
			}
			break;

		case PICK_ZONE: 
			
			successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
			if (!successfulTransfer)
			{
				successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
			}
			break;

		case AXE_ZONE:
			
			successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
			if (!successfulTransfer)
			{
				successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
			}
			break;

		case SHOVEL_ZONE:
			
			successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
			if (!successfulTransfer)
			{
				successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
			}
			break;

		case HOE_ZONE: 
			
			successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
			if (!successfulTransfer)
			{
				successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
			}
			break;

		case PLAYER_HOTBAR:
		case PLAYER_MAIN_INVENTORY: // taking out of inventory - find the appropriate furnace zone
			if (TileEntityToolFuser.isItemValidForPickSlot(sourceItemStack))
			{
				successfulTransfer = mergeInto(SlotZone.PICK_ZONE, sourceItemStack, false);
			}
			if (TileEntityToolFuser.isItemValidForAxeSlot(sourceItemStack))
			{
				successfulTransfer = mergeInto(SlotZone.AXE_ZONE, sourceItemStack, false);
			}
			if (TileEntityToolFuser.isItemValidForShovelSlot(sourceItemStack))
			{
				successfulTransfer = mergeInto(SlotZone.SHOVEL_ZONE, sourceItemStack, false);
			}
			if (TileEntityToolFuser.isItemValidForHoeSlot(sourceItemStack))
			{
				successfulTransfer = mergeInto(SlotZone.HOE_ZONE, sourceItemStack, false);
			}
			if (!successfulTransfer)
			{
				if (sourceZone == SlotZone.PLAYER_HOTBAR)
				{
					successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
				} else
				{
					successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
				}
			}
			break;

		default:
			throw new IllegalArgumentException("unexpected sourceZone:" + sourceZone);
		}
		if (!successfulTransfer)
			return ItemStack.EMPTY;

		if (sourceItemStack.isEmpty())
		{
			sourceSlot.putStack(ItemStack.EMPTY);
		} else
		{
			sourceSlot.onSlotChanged();
		}

		if (sourceItemStack.getCount() == sourceStackBeforeMerge.getCount())
		{
			return ItemStack.EMPTY;
		}
		sourceSlot.onTake(player, sourceItemStack);
		return sourceStackBeforeMerge;
	}

	private boolean mergeInto(SlotZone destinationZone, ItemStack sourceItemStack, boolean fillFromEnd)
	{
		return mergeItemStack(sourceItemStack, destinationZone.firstIndex, destinationZone.lastIndexPlus1, fillFromEnd);
	}


	public class SlotPick extends Slot
	{
		public SlotPick(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return TileEntityToolFuser.isItemValidForPickSlot(stack);
		}
	}

	public class SlotAxe extends Slot
	{
		public SlotAxe(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return TileEntityToolFuser.isItemValidForAxeSlot(stack);
		}
	}
	
	public class SlotShovel extends Slot
	{
		public SlotShovel(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return TileEntityToolFuser.isItemValidForShovelSlot(stack);
		}
	}
	
	public class SlotHoe extends Slot
	{
		public SlotHoe(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return TileEntityToolFuser.isItemValidForHoeSlot(stack);
		}
	}
	
	public class SlotOutput extends Slot
	{
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return TileEntityToolFuser.isItemValidForOutputSlot(stack);
		}
	}

	private FuserZoneContents pickZoneContents;
	private FuserZoneContents axeZoneContents;
	private FuserZoneContents shovelZoneContents;
	private FuserZoneContents hoeZoneContents;
	private FuserZoneContents outputZoneContents;
	private FuserStateData fuserStateData;

	private World world;
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Helper enum to make the code more readable
	 */
	private enum SlotZone
	{
		PICK_ZONE(PICK_SLOT_INDEX, 1), 
		AXE_ZONE(AXE_SLOT_INDEX, 1),
		SHOVEL_ZONE(SHOVEL_SLOT_INDEX, 1),
		HOE_ZONE(HOE_SLOT_INDEX, 1),
		OUTPUT_ZONE(OUTPUT_SLOT_INDEX, 1),
		
		PLAYER_MAIN_INVENTORY(PLAYER_INVENTORY_FIRST_SLOT_INDEX, PLAYER_INVENTORY_SLOT_COUNT),
		PLAYER_HOTBAR(HOTBAR_FIRST_SLOT_INDEX, HOTBAR_SLOT_COUNT);

		SlotZone(int firstIndex, int numberOfSlots)
		{
			this.firstIndex = firstIndex;
			this.slotCount = numberOfSlots;
			this.lastIndexPlus1 = firstIndex + numberOfSlots;
		}

		public final int firstIndex;
		public final int slotCount;
		public final int lastIndexPlus1;

		public static SlotZone getZoneFromIndex(int slotIndex)
		{
			for (SlotZone slotZone : SlotZone.values())
			{
				if (slotIndex >= slotZone.firstIndex && slotIndex < slotZone.lastIndexPlus1)
					return slotZone;
			}
			throw new IndexOutOfBoundsException("Unexpected slotIndex");
		}
	}
}
