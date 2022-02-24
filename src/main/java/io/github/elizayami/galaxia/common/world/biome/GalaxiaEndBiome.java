package io.github.elizayami.galaxia.common.world.biome;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.config.json.endbiomedata.EndBiomeData;

import java.util.*;

public class GalaxiaEndBiome
{
	public static final List<GalaxiaEndBiome> Galaxia_END_BIOMES = new ArrayList<>();
	private final Biome biome;

	public static List<EndBiomeData> endBiomeData = new ArrayList<>();
	public static List<EndBiomeData> voidBiomeData = new ArrayList<>();

	public static Map<ResourceLocation, WeightedList<ResourceLocation>> BIOME_TO_HILLS = new HashMap<>();
	public static Map<ResourceLocation, ResourceLocation> BIOME_TO_EDGE = new HashMap<>();

	public GalaxiaEndBiome(Biome.Climate climate, Biome.Category category, float depth, float scale,
			BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo)
	{
		biome = new Biome(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
		Galaxia_END_BIOMES.add(this);
	}

	public GalaxiaEndBiome(Biome.Builder builder)
	{
		this.biome = builder.build();
		Galaxia_END_BIOMES.add(this);
	}

	public GalaxiaEndBiome(Biome biome)
	{
		this.biome = biome;
		Galaxia_END_BIOMES.add(this);
	}

	public Biome getBiome()
	{
		return this.biome;
	}

	@Nullable
	public WeightedList<ResourceLocation> getHills()
	{
		return null;
	}

	@Nullable
	public Biome getEdge()
	{
		return null;
	}

	public int getWeight()
	{
		return 5;
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
