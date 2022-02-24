package io.github.elizayami.galaxia.common.world.biome;

import io.github.elizayami.galaxia.config.json.biomedata.BiomeData;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GalaxiaBiome
{
	public static final List<GalaxiaBiome> Galaxia_BIOMES = new ArrayList<>();
	private final Biome biome;

	public static List<BiomeData> biomeData = new ArrayList<>();

	public static final Int2ObjectMap<WeightedList<Biome>> BIOME_TO_HILLS_LIST = new Int2ObjectArrayMap<>();
	public static final Int2ObjectMap<Biome> BIOME_TO_BEACH_LIST = new Int2ObjectArrayMap<>();
	public static final Int2ObjectMap<Biome> BIOME_TO_EDGE_LIST = new Int2ObjectArrayMap<>();
	public static final Int2ObjectMap<Biome> BIOME_TO_RIVER_LIST = new Int2ObjectArrayMap<>();

	public GalaxiaBiome(Biome.Climate climate, Biome.Category category, float depth, float scale, BiomeAmbience effects,
			BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo)
	{
		biome = new Biome(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
		Galaxia_BIOMES.add(this);
	}

	public GalaxiaBiome(Biome.Builder builder)
	{
		this.biome = builder.build();
		Galaxia_BIOMES.add(this);
	}

	public GalaxiaBiome(Biome biome)
	{
		this.biome = biome;
		Galaxia_BIOMES.add(this);
	}

	public Biome getBiome()
	{
		return this.biome;
	}

	public Biome getRiver()
	{
		return WorldGenRegistries.BIOME.getOrThrow(Biomes.RIVER);
	}

	@Nullable
	public WeightedList<Biome> getHills()
	{
		return null;
	}

	@Nullable
	public Biome getEdge()
	{
		return null;
	}

	@Nullable
	public Biome getBeach()
	{
		return WorldGenRegistries.BIOME.getOrThrow(Biomes.BEACH);
	}

	public BiomeDictionary.Type[] getBiomeDictionary()
	{
		return new BiomeDictionary.Type[]
		{ BiomeDictionary.Type.OVERWORLD };
	}

	public BiomeManager.BiomeType getBiomeType()
	{
		return BiomeManager.BiomeType.WARM;
	}

	public int getWeight()
	{
		return 5;
	}

	public RegistryKey<Biome> getKey()
	{
		return RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
				Objects.requireNonNull(WorldGenRegistries.BIOME.getKey(this.biome)));
	}
}
