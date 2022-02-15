package io.github.elizayami.galaxia.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.elizayami.galaxia.client.models.entity.ArcModel;
import io.github.elizayami.galaxia.common.entities.ArcEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ArcRenderer extends GeoEntityRenderer<ArcEntity>
{
	public ArcRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn, new ArcModel());
	}

	@Override
	public RenderType getRenderType(ArcEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation)
	{
		return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
	}

	@Override
	public ResourceLocation getEntityTexture(ArcEntity entity)
	{
		return this.getTextureLocation(entity);
	}
}