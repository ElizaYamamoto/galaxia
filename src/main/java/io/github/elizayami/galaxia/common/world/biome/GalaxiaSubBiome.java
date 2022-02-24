package io.github.elizayami.galaxia.common.world.biome;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.config.json.subbiomedata.SubBiomeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GalaxiaSubBiome
{
	public static final List<GalaxiaSubBiome> GALAXIA_SUB_BIOMES = new ArrayList<>();
	private final Biome biome;

	public static List<SubBiomeData> subBiomeData = new ArrayList<>();

	public GalaxiaSubBiome(Biome.Climate climate, Biome.Category category, float depth, float scale,
			BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo)
	{
		biome = new Biome(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
		GALAXIA_SUB_BIOMES.add(this);
	}

	public GalaxiaSubBiome(Biome.Builder builder)
	{
		this.biome = builder.build();
		GALAXIA_SUB_BIOMES.add(this);
	}

	public GalaxiaSubBiome(Biome biome)
	{
		this.biome = biome;
		GALAXIA_SUB_BIOMES.add(this);
	}

	public Biome getBeach()
	{
		return WorldGenRegistries.BIOME.getOrThrow(Biomes.BEACH);
	}

	@Nullable
	public Biome getEdge()
	{
		return null;
	}

	public Biome getBiome()
	{
		return this.biome;
	}

	public Biome getRiver()
	{
		return WorldGenRegistries.BIOME.getOrThrow(Biomes.RIVER);
	}

	public BiomeDictionary.Type[] getBiomeDictionary()
	{
		return new BiomeDictionary.Type[]
		{ BiomeDictionary.Type.OVERWORLD };
	}

	public RegistryKey<Biome> getKey()
	{
		return RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
				Objects.requireNonNull(WorldGenRegistries.BIOME.getKey(this.biome)));
	}
}
