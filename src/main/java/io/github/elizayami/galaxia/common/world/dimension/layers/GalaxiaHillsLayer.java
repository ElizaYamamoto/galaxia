package io.github.elizayami.galaxia.common.world.dimension.layers;

import io.github.elizayami.galaxia.util.LayerRandomWeightedListUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;

import javax.annotation.Nullable;
import java.util.Map;

public class GalaxiaHillsLayer implements IAreaTransformer2, IDimOffset1Transformer
{

	private final Map<ResourceLocation, WeightedList<ResourceLocation>> hillMap;
	private final Registry<Biome> biomeRegistry;
	private final int hillReplacementChance;

	public GalaxiaHillsLayer(Registry<Biome> biomeRegistry,
			Map<ResourceLocation, WeightedList<ResourceLocation>> hillMap, int hillReplacementChance)
	{
		this.hillMap = hillMap;
		this.biomeRegistry = biomeRegistry;
		this.hillReplacementChance = hillReplacementChance;
	}

	@Override
	public int apply(INoiseRandom rand, IArea area1, IArea area2, int x, int z)
	{
		int i = area1.getValue(this.getOffsetX(x + 1), this.getOffsetZ(z + 1));

		if (hillMap.size() > 0)
		{
			if (rand.random(hillReplacementChance) == 0)
			{
				int l = i;
				if (hillMap.containsKey(biomeRegistry.getKey(biomeRegistry.getByValue(i))))
				{
					Biome hill = getHillBiomeValue(hillMap.get(biomeRegistry.getKey(biomeRegistry.getByValue(i))),
							rand);
					if (hill != null)
					{
						l = biomeRegistry.getId(hill);
					}
				}
				return l;
			}
		}
		return i;
	}

	@Nullable
	private Biome getHillBiomeValue(WeightedList<ResourceLocation> biomeHolder, INoiseRandom layerRandom)
	{
		if (biomeHolder.field_220658_a.size() > 0)
		{
			return biomeRegistry.getOrDefault(LayerRandomWeightedListUtil.getBiomeFromID(biomeHolder, layerRandom));
		}
		else
		{
			return null;
		}
	}
}