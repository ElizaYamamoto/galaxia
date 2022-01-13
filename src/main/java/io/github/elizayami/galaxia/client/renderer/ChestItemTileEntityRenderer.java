package io.github.elizayami.galaxia.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.elizayami.galaxia.common.tileentity.GalaxiaChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class ChestItemTileEntityRenderer extends ItemStackTileEntityRenderer {
	private GalaxiaChestTileEntity chest;

	@Override
	public void func_239207_a_(ItemStack stack, TransformType p_239207_2_, MatrixStack matrixStack,
			IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		if (chest == null)
			chest = new GalaxiaChestTileEntity();
		chest.setChest(Block.getBlockFromItem(stack.getItem()));
		TileEntityRendererDispatcher.instance.renderItem(chest, matrixStack, buffer, combinedLight, combinedOverlay);
	}

}
