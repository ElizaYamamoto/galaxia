package io.github.elizayami.galaxia.common.tileentity;

import io.github.elizayami.galaxia.common.abstracts.blocks.GalaxiaBarrelBlock;
import io.github.elizayami.galaxia.core.init.WoodTileEntityInit;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GalaxiaBarrelTileEntity extends LockableLootTileEntity {
	private NonNullList<ItemStack> inventory;
	private int viewerCount;

	private GalaxiaBarrelTileEntity(TileEntityType<?> type) {
		super(type);
		this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
	}
	
	public GalaxiaBarrelTileEntity() {
		this(WoodTileEntityInit.BARREL.get());
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
		if (!this.checkLootAndWrite(tag)) {
			ItemStackHelper.saveAllItems(tag, this.inventory);
		}

		return tag;
	}

	@Override
	public void read(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(tag)) {
			ItemStackHelper.loadAllItems(tag, this.inventory);
		}

	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> list) {
		this.inventory = list;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.barrel");
	}

	@Override
	protected Container createMenu(int syncId, PlayerInventory playerInventory) {
		return ChestContainer.createGeneric9X3(syncId, playerInventory, this);
	}

	@Override
	public void openInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.viewerCount < 0) {
				this.viewerCount = 0;
			}

			++this.viewerCount;
			BlockState blockState = this.getBlockState();
			boolean bl = (Boolean) blockState.get(BarrelBlock.PROPERTY_OPEN);
			if (!bl) {
				this.playSound(blockState, SoundEvents.BLOCK_BARREL_OPEN);
				this.setOpen(blockState, true);
			}

			this.scheduleTick();
		}

	}

	private void scheduleTick() {
		this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
	}

	public void tick() {
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		this.viewerCount = ChestTileEntity.calculatePlayersUsing(this.world, this, i, j, k);
		if (this.viewerCount > 0) {
			this.scheduleTick();
		} else {
			BlockState blockState = this.getBlockState();
			if (!(blockState.getBlock() instanceof GalaxiaBarrelBlock)) {
				this.remove();
				return;
			}

			boolean bl = (Boolean) blockState.get(BarrelBlock.PROPERTY_OPEN);
			if (bl) {
				this.playSound(blockState, SoundEvents.BLOCK_BARREL_CLOSE);
				this.setOpen(blockState, false);
			}
		}

	}

	@Override
	public void closeInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.viewerCount;
		}

	}

	private void setOpen(BlockState state, boolean open) {
		this.world.setBlockState(this.getPos(), (BlockState) state.with(BarrelBlock.PROPERTY_OPEN, open), 3);
	}

	private void playSound(BlockState blockState, SoundEvent soundEvent) {
		Vector3i vec3i = ((Direction) blockState.get(BarrelBlock.PROPERTY_FACING)).getDirectionVec();
		double d = (double) this.pos.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
		double e = (double) this.pos.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
		double f = (double) this.pos.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
		this.world.playSound((PlayerEntity) null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F,
				this.world.rand.nextFloat() * 0.1F + 0.9F);
	}
}
