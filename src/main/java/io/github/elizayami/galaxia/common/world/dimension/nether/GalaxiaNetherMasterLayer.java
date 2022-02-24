package io.github.elizayami.galaxia.common.world.dimension.nether;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class GalaxiaNetherMasterLayer implements IAreaTransformer0
{
	private final Registry<Biome> biomeRegistry;

	public GalaxiaNetherMasterLayer(Registry<Biome> biomeRegistry)
	{
		this.biomeRegistry = biomeRegistry;
	}

	@Override
	public int apply(INoiseRandom rand, int x, int y)
	{
		return getRandomNetherBiomes(this.biomeRegistry, rand);
	}

	public static int getRandomNetherBiomes(Registry<Biome> biomeRegistry, INoiseRandom rand)
	{
		return biomeRegistry.getId(biomeRegistry
				.getOptional(GalaxiaNetherBiomeProvider.NETHER_BIOMES
						.get(rand.random(GalaxiaNetherBiomeProvider.NETHER_BIOMES.size())))
				.orElseThrow(RuntimeException::new));
	}
}
