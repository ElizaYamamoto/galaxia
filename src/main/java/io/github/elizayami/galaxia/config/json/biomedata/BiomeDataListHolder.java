package io.github.elizayami.galaxia.config.json.biomedata;

import io.github.elizayami.galaxia.common.world.biome.GalaxiaBiome;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BiomeDataListHolder
{

	List<BiomeData> biomeData;

	public BiomeDataListHolder(List<BiomeData> biomeData)
	{
		this.biomeData = biomeData;
	}

	public List<BiomeData> getBiomeData()
	{
		return biomeData;
	}

	public static void createDefaults()
	{
		for (GalaxiaBiome bygBiome : GalaxiaBiome.Galaxia_BIOMES)
		{
			List<BiomeDictionary.Type> typeList = Arrays.asList(bygBiome.getBiomeDictionary());
			typeList.sort(Comparator.comparing(Object::toString));
			GalaxiaBiome.biomeData.add(new BiomeData(bygBiome.getBiome(), bygBiome.getWeight(), bygBiome.getBiomeType(),
					typeList.toArray(new BiomeDictionary.Type[0]), bygBiome.getHills(), bygBiome.getEdge(),
					bygBiome.getBeach(), bygBiome.getRiver()));
		}

		// Sort entries alphabetically
		GalaxiaBiome.biomeData
				.sort(Comparator.comparing(data -> WorldGenRegistries.BIOME.getKey(data.getBiome()).toString()));
	}

	public static void fillBiomeLists()
	{
		for (BiomeData biomeData : GalaxiaBiome.biomeData)
		{
			WeightedList<Biome> biomeWeightedList = biomeData.getBiomeWeightedList();
			if (biomeWeightedList != null)
			{
				biomeWeightedList.field_220658_a.removeIf(biomeEntry -> biomeEntry.field_220652_c <= 0);
				GalaxiaBiome.BIOME_TO_HILLS_LIST.put(WorldGenRegistries.BIOME.getId(biomeData.getBiome()),
						biomeWeightedList);
			}
			if (biomeData.getBeachBiome() != null)
				GalaxiaBiome.BIOME_TO_BEACH_LIST.put(WorldGenRegistries.BIOME.getId(biomeData.getBiome()),
						biomeData.getBeachBiome());
			if (biomeData.getEdgeBiome() != null)
				GalaxiaBiome.BIOME_TO_EDGE_LIST.put(WorldGenRegistries.BIOME.getId(biomeData.getBiome()),
						biomeData.getEdgeBiome());
			if (biomeData.getRiverBiome() != null)
				GalaxiaBiome.BIOME_TO_RIVER_LIST.put(WorldGenRegistries.BIOME.getId(biomeData.getBiome()),
						biomeData.getRiverBiome());
		}

		GalaxiaBiome.BIOME_TO_EDGE_LIST.remove(-1);
		GalaxiaBiome.BIOME_TO_BEACH_LIST.remove(-1);
		GalaxiaBiome.BIOME_TO_RIVER_LIST.remove(-1);
	}
}
