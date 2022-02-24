package io.github.elizayami.galaxia.config.json.endbiomedata;

import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndBiome;
import io.github.elizayami.galaxia.common.world.dimension.end.GalaxiaEndBiomeProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.*;
import java.util.stream.Collectors;

public class EndBiomeDataListHolder
{

	List<EndBiomeData> endBiomeData;
	private final List<EndBiomeData> voidBiomeData;

	public EndBiomeDataListHolder(List<EndBiomeData> endBiomeData, List<EndBiomeData> voidBiomeData)
	{
		this.endBiomeData = endBiomeData;
		this.voidBiomeData = voidBiomeData;
	}

	public List<EndBiomeData> getEndBiomeData()
	{
		return endBiomeData;
	}

	public List<EndBiomeData> getVoidBiomeData()
	{
		return voidBiomeData;
	}

	public static void createDefaults(Registry<Biome> biomeRegistry)
	{
		for (GalaxiaEndBiome bygBiome : GalaxiaEndBiome.Galaxia_END_BIOMES)
		{
			List<BiomeDictionary.Type> typeList = Arrays.asList(bygBiome.getBiomeDictionary());
			typeList.sort(Comparator.comparing(Object::toString));
			GalaxiaEndBiome.endBiomeData.add(new EndBiomeData(WorldGenRegistries.BIOME.getKey(bygBiome.getBiome()),
					bygBiome.getWeight(), typeList.toArray(new BiomeDictionary.Type[0]), bygBiome.getHills(),
					WorldGenRegistries.BIOME.getKey(bygBiome.getEdge())));
		}

		for (ResourceLocation location : biomeRegistry.keySet().stream()
				.filter(location -> !location.toString().contains("byg")
						&& !location.toString().equals("minecraft:the_end")
						&& !location.toString().equals("minecraft:small_end_islands")
						&& !location.toString().equals("minecraft:end_barrens"))
				.collect(Collectors.toSet()))
		{
			if (biomeRegistry.getOptional(location).get().getCategory() == Biome.Category.THEEND)
				GalaxiaEndBiome.endBiomeData.add(new EndBiomeData(location, 5, new BiomeDictionary.Type[]
				{ BiomeDictionary.Type.END }, new WeightedList<>(), null));

			GalaxiaEndBiome.endBiomeData.removeIf(endBiomeData1 -> endBiomeData1.getBiome() == null);
			// Sort entries alphabetically
			GalaxiaEndBiome.endBiomeData.sort(Comparator.comparing(data -> data.getBiome().toString()));
		}
	}

	public static void fillBiomeLists()
	{
		WeightedList<ResourceLocation> end_biomes = new WeightedList<>();
		WeightedList<ResourceLocation> void_biomes = new WeightedList<>();

		Map<ResourceLocation, WeightedList<ResourceLocation>> biome_to_hills = new HashMap<>();
		Map<ResourceLocation, ResourceLocation> biome_to_edge = new HashMap<>();

		for (EndBiomeData endBiomeData : GalaxiaEndBiome.endBiomeData)
		{
			WeightedList<ResourceLocation> biomeWeightedList = endBiomeData.getBiomeWeightedList();
			if (biomeWeightedList != null)
			{
				biomeWeightedList.field_220658_a.removeIf(biomeEntry -> biomeEntry.field_220652_c <= 0);
				biome_to_hills.put(endBiomeData.getBiome(), biomeWeightedList);
			}
			if (endBiomeData.getEdgeBiome() != null)
				biome_to_edge.put(endBiomeData.getBiome(), endBiomeData.getEdgeBiome());

			end_biomes.func_226313_a_(endBiomeData.getBiome(), endBiomeData.getBiomeWeight());
		}

		for (EndBiomeData endBiomeData : GalaxiaEndBiome.voidBiomeData)
		{
			WeightedList<ResourceLocation> biomeWeightedList = endBiomeData.getBiomeWeightedList();
			if (biomeWeightedList != null)
			{
				biomeWeightedList.field_220658_a.removeIf(biomeEntry -> biomeEntry.field_220652_c <= 0);
				biome_to_hills.put(endBiomeData.getBiome(), biomeWeightedList);
			}
			if (endBiomeData.getEdgeBiome() != null)
				biome_to_edge.put(endBiomeData.getBiome(), endBiomeData.getEdgeBiome());

			void_biomes.func_226313_a_(endBiomeData.getBiome(), endBiomeData.getBiomeWeight());
		}

		biome_to_hills.entrySet().removeIf(Objects::isNull);
		biome_to_edge.entrySet().removeIf(Objects::isNull);
		end_biomes.field_220658_a.removeIf(Objects::isNull);
		void_biomes.field_220658_a.removeIf(Objects::isNull);

		GalaxiaEndBiomeProvider.END_BIOMES = end_biomes;
		GalaxiaEndBiomeProvider.VOID_BIOMES = void_biomes;
		GalaxiaEndBiome.BIOME_TO_HILLS = biome_to_hills;
		GalaxiaEndBiome.BIOME_TO_EDGE = biome_to_edge;
	}
}
