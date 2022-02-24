package io.github.elizayami.galaxia.common.world.dimension.nether;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.elizayami.galaxia.common.world.dimension.DatapackLayer;
import io.github.elizayami.galaxia.config.GalaxiaWorldConfig;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

import java.util.*;
import java.util.stream.Collectors;

public class GalaxiaNetherBiomeProvider extends BiomeProvider
{
	public static final Codec<GalaxiaNetherBiomeProvider> GalaxiaNETHERCODEC = RecordCodecBuilder
			.create((instance) -> instance.group(
					RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY)
							.forGetter((theEndBiomeSource) -> theEndBiomeSource.biomeRegistry),
					Codec.LONG.fieldOf("seed").stable().forGetter((theEndBiomeSource) -> theEndBiomeSource.seed))
					.apply(instance, instance.stable(GalaxiaNetherBiomeProvider::new)));

	private static final List<String> NETHER_BIOME_IDS = Arrays
			.asList(GalaxiaWorldConfig.BLACKLIST_NETHER.get().trim().replace(" ", "").split(","));

	private final DatapackLayer biomeLayer;
	private final long seed;
	private final Registry<Biome> biomeRegistry;
	public static List<ResourceLocation> NETHER_BIOMES = new ArrayList<>();

	public GalaxiaNetherBiomeProvider(Registry<Biome> registry, long seed)
	{
		super(createNetherBiomeList(registry).stream().map(registry::getOrDefault).collect(Collectors.toList()));
		this.seed = seed;
		biomeRegistry = registry;
		NETHER_BIOMES = createNetherBiomeList(registry);
		this.biomeLayer = GalaxiaNetherLayerProvider.stackLayers(this.biomeRegistry, seed);
	}

	public static List<ResourceLocation> createNetherBiomeList(Registry<Biome> biomeRegistry)
	{
		List<ResourceLocation> NETHER_BIOMES = new ArrayList<>();

		for (Map.Entry<RegistryKey<Biome>, Biome> biomeEntry : biomeRegistry.getEntries())
		{
			if (biomeEntry.getValue().getCategory() == Biome.Category.NETHER)
			{
				ResourceLocation locationKey = biomeEntry.getKey().getLocation();

				if (GalaxiaWorldConfig.IS_BLACKLIST_NETHER.get())
				{
					if (!NETHER_BIOMES.contains(locationKey) && !NETHER_BIOME_IDS.contains(locationKey.toString()))
					{
						NETHER_BIOMES.add(locationKey);
					}
				}
				else
				{
					for (String id : NETHER_BIOME_IDS)
					{
						if (id.equals(locationKey.toString()))
						{
							NETHER_BIOMES.add(locationKey);
						}
					}
				}
			}
		}
		NETHER_BIOMES.removeIf(Objects::isNull);
		return NETHER_BIOMES;
	}

	@Override
	protected Codec<? extends BiomeProvider> getBiomeProviderCodec()
	{
		return GalaxiaNETHERCODEC;
	}

	@Override
	public BiomeProvider getBiomeProvider(long seed)
	{
		return new GalaxiaNetherBiomeProvider(biomeRegistry, seed);
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return biomeLayer.sampleNether(biomeRegistry, x, z);
	}

}
