package io.github.elizayami.galaxia.common.tileentity;

import io.github.elizayami.galaxia.core.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ChestTileEntity;

public class GalaxiaChestTileEntity extends ChestTileEntity {
	private Block chest = Blocks.AIR;
	
	public GalaxiaChestTileEntity() {
		super(TileEntityInit.CHEST.get());
	}

	public void setChest(Block chest) {
		this.chest = chest;
	}
	
	public Block getChest() {
		return chest;
	}
	
	public boolean hasChest() {
		return !chest.getDefaultState().isAir();
	}
}
