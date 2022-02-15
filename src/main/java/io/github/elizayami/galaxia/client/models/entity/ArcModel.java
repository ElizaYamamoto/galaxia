package io.github.elizayami.galaxia.client.models.entity;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.entities.ArcEntity;
import io.github.elizayami.galaxia.core.util.ModelUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class ArcModel extends AnimatedGeoModel<ArcEntity>
{
	private ResourceLocation DEFAULT = new ResourceLocation(Galaxia.MOD_ID, "textures/entity/arc.png");

	@Override
	public ResourceLocation getModelLocation(ArcEntity entity)
	{
		return ModelUtils.entityModel("arc");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ArcEntity entity)
	{
		return ModelUtils.animation("arc");
	}

	@Override
	public ResourceLocation getTextureLocation(ArcEntity entity)
	{
		return DEFAULT;
	}
}
