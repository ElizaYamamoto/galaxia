package io.github.elizayami.galaxia.config.json.endbiomedata.sub;

import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndSubBiome;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class EndSubBiomeDataListHolder
{

	List<EndSubBiomeData> endSubBiomeData;
	private final List<EndSubBiomeData> voidSubBiomeData;

	public EndSubBiomeDataListHolder(List<EndSubBiomeData> endSubBiomeData, List<EndSubBiomeData> voidSubBiomeData)
	{
		this.endSubBiomeData = endSubBiomeData;
		this.voidSubBiomeData = voidSubBiomeData;
	}

	public List<EndSubBiomeData> getEndSubBiomeData()
	{
		return endSubBiomeData;
	}

	public List<EndSubBiomeData> getVoidSubBiomeData()
	{
		return voidSubBiomeData;
	}

	public static void createDefaults()
	{
		for (GalaxiaEndSubBiome bygBiome : GalaxiaEndSubBiome.Galaxia_END_SUB_BIOMES)
		{
			List<BiomeDictionary.Type> typeList = Arrays.asList(bygBiome.getBiomeDictionary());
			typeList.sort(Comparator.comparing(Object::toString));
			GalaxiaEndSubBiome.endSubBiomeData.add(new EndSubBiomeData(WorldGenRegistries.BIOME.getKey(bygBiome.getBiome()),
					typeList.toArray(new BiomeDictionary.Type[0]), null));
		}
	}

	public static void fillBiomeLists()
	{
		for (EndSubBiomeData endSubBiomeData : GalaxiaEndSubBiome.endSubBiomeData)
		{
			if (endSubBiomeData.getEdgeBiome() != null)
				GalaxiaEndBiome.BIOME_TO_EDGE.put(endSubBiomeData.getBiome(), endSubBiomeData.getEdgeBiome());
		}

		for (EndSubBiomeData endSubBiomeData : GalaxiaEndSubBiome.voidSubBiomeData)
		{
			if (endSubBiomeData.getEdgeBiome() != null)
				GalaxiaEndBiome.BIOME_TO_EDGE.put(endSubBiomeData.getBiome(), endSubBiomeData.getEdgeBiome());
		}

		GalaxiaEndBiome.BIOME_TO_EDGE.entrySet().removeIf(Objects::isNull);
		GalaxiaEndBiome.BIOME_TO_HILLS.entrySet().removeIf(Objects::isNull);
	}
}
