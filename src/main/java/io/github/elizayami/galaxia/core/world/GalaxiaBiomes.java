package io.github.elizayami.galaxia.core.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import io.github.elizayami.galaxia.common.world.biome.GalaxiaBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndSubBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaNetherBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaSubBiome;
import io.github.elizayami.galaxia.common.world.biome.nether.AshsparkDunes;
import io.github.elizayami.galaxia.config.json.biomedata.BiomeData;
import io.github.elizayami.galaxia.config.json.endbiomedata.EndBiomeData;
import io.github.elizayami.galaxia.config.json.endbiomedata.sub.EndSubBiomeData;
import io.github.elizayami.galaxia.config.json.subbiomedata.SubBiomeData;
import io.github.elizayami.galaxia.core.world.util.WorldGenRegistrationHelper;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class GalaxiaBiomes
{
	public static List<PreserveBiomeOrder> biomeList = new ArrayList<>();

	public static Biome ASHSPARK_DUNES = WorldGenRegistrationHelper.createBiome("ashspark_dunes",
			new AshsparkDunes().getBiome(), 156);

	public static void init()
	{
	}

	public static final IdentityHashMap<BiomeManager.BiomeType, WeightedList<ResourceLocation>> TRACKED_OCEANS = new IdentityHashMap<>();

	public static void addBiomeEntries()
	{
		for (BiomeData biomeData : GalaxiaBiome.biomeData)
		{
			List<BiomeDictionary.Type> dictionaryList = Arrays.stream(biomeData.getDictionaryTypes())
					.collect(Collectors.toList());
			ResourceLocation key = WorldGenRegistries.BIOME.getKey(biomeData.getBiome());

			if (biomeData.getBiomeWeight() > 0)
			{
				BiomeManager.addBiome(biomeData.getBiomeType(), new BiomeManager.BiomeEntry(
						RegistryKey.getOrCreateKey(Registry.BIOME_KEY, key), biomeData.getBiomeWeight()));
			}
		}

		addDefaultOceans();
	}

	private static void addDefaultOceans()
	{
		TRACKED_OCEANS.computeIfAbsent(BiomeManager.BiomeType.ICY, (biometype) -> new WeightedList<>())
				.func_226313_a_(Biomes.FROZEN_OCEAN.getRegistryName(), 5);
		TRACKED_OCEANS.computeIfAbsent(BiomeManager.BiomeType.COOL, (biometype) -> new WeightedList<>())
				.func_226313_a_(Biomes.DEEP_COLD_OCEAN.getRegistryName(), 5);
		TRACKED_OCEANS.computeIfAbsent(null, (biometype) -> new WeightedList<>())
				.func_226313_a_(Biomes.DEEP_OCEAN.getRegistryName(), 5);
		TRACKED_OCEANS.computeIfAbsent(BiomeManager.BiomeType.WARM, (biometype) -> new WeightedList<>())
				.func_226313_a_(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName(), 5);
		TRACKED_OCEANS.computeIfAbsent(BiomeManager.BiomeType.DESERT, (biometype) -> new WeightedList<>())
				.func_226313_a_(Biomes.DEEP_WARM_OCEAN.getRegistryName(), 5);
	}

	public static void fillBiomeDictionary(Registry<Biome> biomeRegistry)
	{
		for (EndBiomeData endBiomeData : GalaxiaEndBiome.endBiomeData)
		{
			RegistryKey<Biome> key = biomeRegistry
					.getOptionalKey(biomeRegistry.getOptional(endBiomeData.getBiome()).get()).get();
			for (int idx = 0; idx < endBiomeData.getDictionaryTypes().length; idx++)
			{
				BiomeDictionary.Type type = endBiomeData.getDictionaryTypes()[idx];
				if (!(BiomeDictionary.hasType(key, type)))
					BiomeDictionary.addTypes(key, type);
			}
		}
		for (EndBiomeData endBiomeData : GalaxiaEndBiome.voidBiomeData)
		{
			RegistryKey<Biome> key = biomeRegistry
					.getOptionalKey(biomeRegistry.getOptional(endBiomeData.getBiome()).get()).get();
			for (int idx = 0; idx < endBiomeData.getDictionaryTypes().length; idx++)
			{
				BiomeDictionary.Type type = endBiomeData.getDictionaryTypes()[idx];
				if (!(BiomeDictionary.hasType(key, type)))
					BiomeDictionary.addTypes(key, type);
			}
		}

		for (EndSubBiomeData endBiomeData : GalaxiaEndSubBiome.endSubBiomeData)
		{
			RegistryKey<Biome> key = biomeRegistry
					.getOptionalKey(biomeRegistry.getOptional(endBiomeData.getBiome()).get()).get();
			for (int idx = 0; idx < endBiomeData.getDictionaryTypes().length; idx++)
			{
				BiomeDictionary.Type type = endBiomeData.getDictionaryTypes()[idx];
				if (!(BiomeDictionary.hasType(key, type)))
					BiomeDictionary.addTypes(key, type);
			}
		}

		for (EndSubBiomeData endBiomeData : GalaxiaEndSubBiome.voidSubBiomeData)
		{
			RegistryKey<Biome> key = biomeRegistry
					.getOptionalKey(biomeRegistry.getOptional(endBiomeData.getBiome()).get()).get();
			for (int idx = 0; idx < endBiomeData.getDictionaryTypes().length; idx++)
			{
				BiomeDictionary.Type type = endBiomeData.getDictionaryTypes()[idx];
				if (!(BiomeDictionary.hasType(key, type)))
					BiomeDictionary.addTypes(key, type);
			}
		}
	}

	public static void fillBiomeDictionary()
	{
		for (BiomeData bygBiome : GalaxiaBiome.biomeData)
		{
			BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
					WorldGenRegistries.BIOME.getKey(bygBiome.getBiome())), bygBiome.getDictionaryTypes());
		}
		for (SubBiomeData bygSubBiome : GalaxiaSubBiome.subBiomeData)
		{
			BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
					WorldGenRegistries.BIOME.getKey(bygSubBiome.getBiome())), bygSubBiome.getDictionaryTypes());
		}

		for (GalaxiaNetherBiome bygNetherBiome : GalaxiaNetherBiome.Galaxia_NETHER_BIOMES)
			BiomeDictionary.addTypes(bygNetherBiome.getKey(), bygNetherBiome.getBiomeDictionary());
	}

	public static void addFeatureToBiomeFirst(Biome biome, ConfiguredFeature<?, ?> configuredFeature)
	{
		ConvertImmutableFeatures(biome);
		List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = biome.getGenerationSettings().features;

		List<Supplier<ConfiguredFeature<?, ?>>> suppliers = biomeFeatures
				.get(GenerationStage.Decoration.RAW_GENERATION.ordinal());

		List<Supplier<ConfiguredFeature<?, ?>>> suppliersCopy = new ArrayList<>(suppliers);
		suppliers.clear();
		suppliers.add(() -> configuredFeature);
		suppliers.addAll(suppliersCopy);
	}

	// Use these to add our features to vanilla's biomes.
	public static void addFeatureToBiome(Biome biome, GenerationStage.Decoration feature,
			ConfiguredFeature<?, ?> configuredFeature)
	{
		ConvertImmutableFeatures(biome);
		List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = biome.getGenerationSettings().features;
		while (biomeFeatures.size() <= feature.ordinal())
		{
			biomeFeatures.add(Lists.newArrayList());
		}
		biomeFeatures.get(feature.ordinal()).add(() -> configuredFeature);
	}

	private static void ConvertImmutableFeatures(Biome biome)
	{
		if (biome.getGenerationSettings().features instanceof ImmutableList)
		{
			biome.getGenerationSettings().features = biome.getGenerationSettings().features.stream()
					.map(Lists::newArrayList).collect(Collectors.toList());
		}
	}

	// Use these to add our features to vanilla's biomes.
	public static void addStructureToBiome(Biome biome, StructureFeature<?, ?> configuredStructure)
	{
		convertImmutableStructures(biome);
		List<Supplier<StructureFeature<?, ?>>> biomeFeatures = biome.getGenerationSettings().structures;
		biomeFeatures.add(() -> configuredStructure);
	}

	private static void convertImmutableStructures(Biome biome)
	{
		biome.getGenerationSettings().structures = new ArrayList<>(biome.getGenerationSettings().structures);
	}

	public static class PreserveBiomeOrder
	{
		private final Biome biome;
		private final int orderPosition;

		public PreserveBiomeOrder(Biome biome, int orderPosition)
		{
			this.biome = biome;
			this.orderPosition = orderPosition;
		}

		public Biome getBiome()
		{
			return biome;
		}

		public int getOrderPosition()
		{
			return orderPosition;
		}
	}
}
