package io.github.elizayami.galaxia.config.json.subbiomedata;

import io.github.elizayami.galaxia.common.world.biome.GalaxiaBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaSubBiome;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraftforge.common.BiomeDictionary;

import java.util.*;

public class SubBiomeDataListHolder {

    List<SubBiomeData> subBiomeData;


    public SubBiomeDataListHolder(List<SubBiomeData> subBiomeData) {
        this.subBiomeData = subBiomeData;
    }

    public List<SubBiomeData> getSubBiomeData() {
        return subBiomeData;
    }

    public static void createDefaults() {
        for (GalaxiaSubBiome bygSubBiome : GalaxiaSubBiome.GALAXIA_SUB_BIOMES) {
            List<BiomeDictionary.Type> typeList = Arrays.asList(bygSubBiome.getBiomeDictionary());
            typeList.sort(Comparator.comparing(Object::toString));
            GalaxiaSubBiome.subBiomeData.add(new SubBiomeData(bygSubBiome.getBiome(), typeList.toArray(new BiomeDictionary.Type[0]), bygSubBiome.getEdge(), bygSubBiome.getBeach(), bygSubBiome.getRiver()));
        }


        //Sort entries alphabetically
        GalaxiaSubBiome.subBiomeData.sort(Comparator.comparing(data -> WorldGenRegistries.BIOME.getKey(data.getBiome()).toString()));
    }

    public static void fillBiomeLists() {
        for (SubBiomeData biomeData : GalaxiaSubBiome.subBiomeData) {
            if (biomeData.getBeachBiome() != null)
                GalaxiaBiome.BIOME_TO_BEACH_LIST.put(WorldGenRegistries.BIOME.getId(biomeData.getBiome()), biomeData.getBeachBiome());
            if (biomeData.getEdgeBiome() != null)
                GalaxiaBiome.BIOME_TO_EDGE_LIST.put(WorldGenRegistries.BIOME.getId(biomeData.getBiome()), biomeData.getEdgeBiome());
            if (biomeData.getRiverBiome() != null)
                GalaxiaBiome.BIOME_TO_RIVER_LIST.put(WorldGenRegistries.BIOME.getId(biomeData.getBiome()), biomeData.getRiverBiome());
        }

        GalaxiaBiome.BIOME_TO_EDGE_LIST.remove(-1);
        GalaxiaBiome.BIOME_TO_BEACH_LIST.remove(-1);
        GalaxiaBiome.BIOME_TO_RIVER_LIST.remove(-1);
    }
}
