package io.github.elizayami.galaxia.common.world.dimension.layers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import java.util.Map;

public class GalaxiaEdgeLayer implements ICastleTransformer
{

	private final Map<ResourceLocation, ResourceLocation> edgeMap;
	private final Registry<Biome> biomeRegistry;

	public GalaxiaEdgeLayer(Registry<Biome> biomeRegistry, Map<ResourceLocation, ResourceLocation> edgeMap)
	{
		this.edgeMap = edgeMap;
		this.biomeRegistry = biomeRegistry;
	}

	@Override
	public int apply(INoiseRandom context, int n, int w, int s, int e, int centre)
	{
		final int[] ArrayNESW =
		{ n, w, s, e };

		for (int idx : ArrayNESW)
		{
			ResourceLocation centreKey = biomeRegistry.getKey(biomeRegistry.getByValue(centre));
			ResourceLocation idxKey = biomeRegistry.getKey(biomeRegistry.getByValue(idx));

			if (edgeMap.containsKey(centreKey) && idxKey != centreKey)
			{
				return biomeRegistry.getId(biomeRegistry.getOrDefault(edgeMap.get(centreKey)));
			}
		}
		return centre;
	}
}