package io.github.elizayami.galaxia.common.world.dimension.layers;

import io.github.elizayami.galaxia.util.LayerRandomWeightedListUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import javax.annotation.Nullable;

public class GalaxiaLayerUtils
{

	@Nullable
	public static Biome getBiomeFromWeightedList(WeightedList<ResourceLocation> oceanBiomeList,
			INoiseRandom layerRandom, Registry<Biome> biomeRegistry)
	{
		if (oceanBiomeList.field_220658_a.size() > 0)
		{
			return biomeRegistry.getOrDefault(LayerRandomWeightedListUtil.getBiomeFromID(oceanBiomeList, layerRandom));
		}
		else
		{
			return null;
		}
	}
}
