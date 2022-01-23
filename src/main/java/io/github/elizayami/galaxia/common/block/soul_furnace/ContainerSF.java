package io.github.elizayami.galaxia.common.block.soul_furnace;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.elizayami.galaxia.common.abstracts.furnace.FurnaceZoneContents;
import io.github.elizayami.galaxia.common.tileentity.TileEntitySoulFurnace;
import io.github.elizayami.galaxia.core.init.FurnaceInit;

public class ContainerSF extends Container {

	public static ContainerSF createContainerServerSide(int windowID, PlayerInventory playerInventory,
			FurnaceZoneContents inputZoneContents, FurnaceZoneContents outputZoneContents,
			FurnaceZoneContents fuelZoneContents, FurnaceStateData furnaceStateData) {
		return new ContainerSF(windowID, playerInventory, inputZoneContents, outputZoneContents, fuelZoneContents,
				furnaceStateData);
	}

	public static ContainerSF createContainerClientSide(int windowID, PlayerInventory playerInventory,
			net.minecraft.network.PacketBuffer extraData) {
		FurnaceZoneContents inputZoneContents = FurnaceZoneContents.createForClientSideContainer(INPUT_SLOTS_COUNT);
		FurnaceZoneContents outputZoneContents = FurnaceZoneContents.createForClientSideContainer(OUTPUT_SLOTS_COUNT);
		FurnaceZoneContents fuelZoneContents = FurnaceZoneContents.createForClientSideContainer(FUEL_SLOTS_COUNT);
		FurnaceStateData furnaceStateData = new FurnaceStateData();

		return new ContainerSF(windowID, playerInventory, inputZoneContents, outputZoneContents, fuelZoneContents,
				furnaceStateData);
	}

	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	public static final int FUEL_SLOTS_COUNT = TileEntitySoulFurnace.FUEL_SLOTS_COUNT;
	public static final int INPUT_SLOTS_COUNT = TileEntitySoulFurnace.INPUT_SLOTS_COUNT;
	public static final int OUTPUT_SLOTS_COUNT = TileEntitySoulFurnace.OUTPUT_SLOTS_COUNT;
	public static final int FURNACE_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int HOTBAR_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX;
	private static final int PLAYER_INVENTORY_FIRST_SLOT_INDEX = HOTBAR_FIRST_SLOT_INDEX + HOTBAR_SLOT_COUNT;
	private static final int FIRST_FUEL_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int FIRST_INPUT_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT;
	private static final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;

	public static final int PLAYER_INVENTORY_XPOS = 8;
	public static final int PLAYER_INVENTORY_YPOS = 125;

	public ContainerSF(int windowID, PlayerInventory invPlayer, FurnaceZoneContents inputZoneContents,
			FurnaceZoneContents outputZoneContents, FurnaceZoneContents fuelZoneContents,
			FurnaceStateData furnaceStateData) {
		super(FurnaceInit.containerTypeSF, windowID);
		if (FurnaceInit.containerTypeSF == null)
			throw new IllegalStateException("Must initialise containerTypeSF before constructing a Soul Furnace!");
		this.inputZoneContents = inputZoneContents;
		this.outputZoneContents = outputZoneContents;
		this.fuelZoneContents = fuelZoneContents;
		this.furnaceStateData = furnaceStateData;
		this.world = invPlayer.player.world;

		trackIntArray(furnaceStateData);

		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 183;

		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
			int slotNumber = x;
			addSlot(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
		}

		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
			for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlot(new Slot(invPlayer, slotNumber, xpos, ypos));
			}
		}

		final int FUEL_SLOTS_XPOS = 53;
		final int FUEL_SLOTS_YPOS = 96;

		for (int x = 0; x < FUEL_SLOTS_COUNT; x++) {
			int slotNumber = x;
			addSlot(new SlotFuel(fuelZoneContents, slotNumber, FUEL_SLOTS_XPOS + SLOT_X_SPACING * x, FUEL_SLOTS_YPOS));
		}

		final int INPUT_SLOTS_XPOS = 26;
		final int INPUT_SLOTS_YPOS = 24;

		for (int y = 0; y < INPUT_SLOTS_COUNT; y++) {
			int slotNumber = y;
			addSlot(new SlotSmeltableInput(inputZoneContents, slotNumber, INPUT_SLOTS_XPOS,
					INPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
		}

		final int OUTPUT_SLOTS_XPOS = 134;
		final int OUTPUT_SLOTS_YPOS = 24;

		for (int y = 0; y < OUTPUT_SLOTS_COUNT; y++) {
			int slotNumber = y;
			addSlot(new SlotOutput(outputZoneContents, slotNumber, OUTPUT_SLOTS_XPOS,
					OUTPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return fuelZoneContents.isUsableByPlayer(player) && inputZoneContents.isUsableByPlayer(player)
				&& outputZoneContents.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
		Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack())
			return ItemStack.EMPTY;
		ItemStack sourceItemStack = sourceSlot.getStack();
		ItemStack sourceStackBeforeMerge = sourceItemStack.copy();
		boolean successfulTransfer = false;

		SlotZone sourceZone = SlotZone.getZoneFromIndex(sourceSlotIndex);

		switch (sourceZone) {
		case OUTPUT_ZONE:

			successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, true);
			if (!successfulTransfer) {
				successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, true);
			}
			if (successfulTransfer) {
				sourceSlot.onSlotChange(sourceItemStack, sourceStackBeforeMerge);
			}
			break;

		case INPUT_ZONE:
		case FUEL_ZONE:

			successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
			if (!successfulTransfer) {
				successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
			}
			break;

		case PLAYER_HOTBAR:
		case PLAYER_MAIN_INVENTORY:
			if (!TileEntitySoulFurnace.getSmeltingResultForItem(world, sourceItemStack).isEmpty()) {
				successfulTransfer = mergeInto(SlotZone.INPUT_ZONE, sourceItemStack, false);
			}
			if (!successfulTransfer && TileEntitySoulFurnace.getItemBurnTime(world, sourceItemStack) > 0) {
				successfulTransfer = mergeInto(SlotZone.FUEL_ZONE, sourceItemStack, true);
			}
			if (!successfulTransfer) {
				if (sourceZone == SlotZone.PLAYER_HOTBAR) {
					successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
				} else {
					successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
				}
			}
			break;

		default:
			throw new IllegalArgumentException("unexpected sourceZone:" + sourceZone);
		}
		if (!successfulTransfer)
			return ItemStack.EMPTY;

		if (sourceItemStack.isEmpty()) {
			sourceSlot.putStack(ItemStack.EMPTY);
		} else {
			sourceSlot.onSlotChanged();
		}

		if (sourceItemStack.getCount() == sourceStackBeforeMerge.getCount()) {
			return ItemStack.EMPTY;
		}
		sourceSlot.onTake(player, sourceItemStack);
		return sourceStackBeforeMerge;
	}

	private boolean mergeInto(SlotZone destinationZone, ItemStack sourceItemStack, boolean fillFromEnd) {
		return mergeItemStack(sourceItemStack, destinationZone.firstIndex, destinationZone.lastIndexPlus1, fillFromEnd);
	}

	public double fractionOfFuelRemaining(int fuelSlot) {
		if (furnaceStateData.burnTimeInitialValues[fuelSlot] <= 0)
			return 0;
		double fraction = furnaceStateData.burnTimeRemainings[fuelSlot]
				/ (double) furnaceStateData.burnTimeInitialValues[fuelSlot];
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}

	public int secondsOfFuelRemaining(int fuelSlot) {
		if (furnaceStateData.burnTimeRemainings[fuelSlot] <= 0)
			return 0;
		return furnaceStateData.burnTimeRemainings[fuelSlot] / 20; // 20 ticks per second
	}

	public double fractionOfCookTimeComplete() {
		if (furnaceStateData.cookTimeForCompletion == 0)
			return 0;
		double fraction = furnaceStateData.cookTimeElapsed / (double) furnaceStateData.cookTimeForCompletion;
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}

	public class SlotFuel extends Slot {
		public SlotFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntitySoulFurnace.isItemValidForFuelSlot(stack);
		}
	}

	public class SlotSmeltableInput extends Slot {
		public SlotSmeltableInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntitySoulFurnace.isItemValidForInputSlot(stack);
		}
	}

	public class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntitySoulFurnace.isItemValidForOutputSlot(stack);
		}
	}

	private FurnaceZoneContents inputZoneContents;
	private FurnaceZoneContents outputZoneContents;
	private FurnaceZoneContents fuelZoneContents;
	private FurnaceStateData furnaceStateData;

	private World world;
	private static final Logger LOGGER = LogManager.getLogger();

	private enum SlotZone {
		FUEL_ZONE(FIRST_FUEL_SLOT_INDEX, FUEL_SLOTS_COUNT), INPUT_ZONE(FIRST_INPUT_SLOT_INDEX, INPUT_SLOTS_COUNT),
		OUTPUT_ZONE(FIRST_OUTPUT_SLOT_INDEX, OUTPUT_SLOTS_COUNT),
		PLAYER_MAIN_INVENTORY(PLAYER_INVENTORY_FIRST_SLOT_INDEX, PLAYER_INVENTORY_SLOT_COUNT),
		PLAYER_HOTBAR(HOTBAR_FIRST_SLOT_INDEX, HOTBAR_SLOT_COUNT);

		SlotZone(int firstIndex, int numberOfSlots) {
			this.firstIndex = firstIndex;
			this.slotCount = numberOfSlots;
			this.lastIndexPlus1 = firstIndex + numberOfSlots;
		}

		public final int firstIndex;
		public final int slotCount;
		public final int lastIndexPlus1;

		public static SlotZone getZoneFromIndex(int slotIndex) {
			for (SlotZone slotZone : SlotZone.values()) {
				if (slotIndex >= slotZone.firstIndex && slotIndex < slotZone.lastIndexPlus1)
					return slotZone;
			}
			throw new IndexOutOfBoundsException("Unexpected slotIndex");
		}
	}
}
