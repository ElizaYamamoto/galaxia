package io.github.elizayami.galaxia.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.elizayami.galaxia.common.abstracts.blocks.RotatingBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.Random;

public class StarRenderer extends TileEntityRenderer<RotatingBlock>
{
	private final Block block;
	private final boolean fullRotation;

	public StarRenderer(TileEntityRendererDispatcher rendererDispatcherIn, Block block, boolean fullRotation)
	{
		super(rendererDispatcherIn);
		this.block = block;
		this.fullRotation = fullRotation;
	}

	@Override
	public void render(RotatingBlock tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer,
			int combinedLight, int combinedOverlay)
	{
		matrixStack.push();

		double offset = Math.sin((System.currentTimeMillis() + tile.animationOffset) % 5000 / 5000f * 2 * Math.PI)
				* 0.1;
		matrixStack.translate(0, offset, 0);

		matrixStack.translate(0.5, 0.5, 0.5);
		if (fullRotation)
		{
			float angleX = (System.currentTimeMillis() + tile.animationOffset) % 13000 / 13000f * 360f;
			float angleY = (System.currentTimeMillis() + tile.animationOffset) % 15000 / 15000f * 360f;
			float angleZ = (System.currentTimeMillis() + tile.animationOffset) % 16000 / 16000f * 360f;
			matrixStack.rotate(new Quaternion(angleX, angleY, angleZ, true));
		}
		else
		{
			float angle = (System.currentTimeMillis() + tile.animationOffset) % 11000 / 11000f * 360f;
			matrixStack.rotate(new Quaternion(0, angle, 0, true));
		}
		matrixStack.translate(-0.5, -0.5, -0.5);

		for (RenderType type : RenderType.getBlockRenderTypes())
		{
			if (RenderTypeLookup.canRenderInLayer(block.getDefaultState(), type))
			{
				BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
				IBakedModel model = blockRenderer.getModelForState(block.getDefaultState());
				blockRenderer.getBlockModelRenderer().renderModel(tile.getWorld(), model, block.getDefaultState(),
						tile.getPos(), matrixStack, buffer.getBuffer(type), false, new Random(), 0, combinedOverlay,
						EmptyModelData.INSTANCE);
			}
		}

		matrixStack.pop();
	}
}
