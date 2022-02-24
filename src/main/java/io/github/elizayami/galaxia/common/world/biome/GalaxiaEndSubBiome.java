package io.github.elizayami.galaxia.common.world.biome;

import io.github.elizayami.galaxia.config.json.endbiomedata.sub.EndSubBiomeData;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("deprecation")
public class GalaxiaEndSubBiome
{
	public static final List<GalaxiaEndSubBiome> Galaxia_END_SUB_BIOMES = new ArrayList<>();
	private final Biome biome;

	public static List<EndSubBiomeData> endSubBiomeData = new ArrayList<>();
	public static List<EndSubBiomeData> voidSubBiomeData = new ArrayList<>();

	public GalaxiaEndSubBiome(Biome.Climate climate, Biome.Category category, float depth, float scale,
			BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo)
	{
		biome = new Biome(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
		Galaxia_END_SUB_BIOMES.add(this);
	}

	public GalaxiaEndSubBiome(Biome.Builder builder)
	{
		this.biome = builder.build();
		Galaxia_END_SUB_BIOMES.add(this);
	}

	public GalaxiaEndSubBiome(Biome biome)
	{
		this.biome = biome;
		Galaxia_END_SUB_BIOMES.add(this);
	}

	public Biome getBiome()
	{
		return this.biome;
	}

	@Nullable
	public Biome getEdge()
	{
		return null;
	}

	public BiomeDictionary.Type[] getBiomeDictionary()
	{
		return new BiomeDictionary.Type[]
		{ BiomeDictionary.Type.END };
	}

	public RegistryKey<Biome> getKey()
	{
		return RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
				Objects.requireNonNull(WorldGenRegistries.BIOME.getKey(this.biome)));
	}
}
