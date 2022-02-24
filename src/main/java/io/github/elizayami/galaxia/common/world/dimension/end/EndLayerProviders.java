package io.github.elizayami.galaxia.common.world.dimension.end;

import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndBiome;
import io.github.elizayami.galaxia.common.world.dimension.DatapackLayer;
import io.github.elizayami.galaxia.common.world.dimension.layers.GalaxiaEdgeLayer;
import io.github.elizayami.galaxia.common.world.dimension.layers.GalaxiaHillsLayer;
import io.github.elizayami.galaxia.common.world.dimension.layers.WeightedMasterLayer;
import io.github.elizayami.galaxia.config.GalaxiaWorldConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.function.LongFunction;

public class EndLayerProviders
{
	public static DatapackLayer stackLayers(Registry<Biome> biomeRegistry, long seed)
	{
		LongFunction<IExtendedNoiseRandom<LazyArea>> randomProvider = salt -> new LazyAreaLayerContext(1, seed, salt);

		IAreaFactory<LazyArea> endLayer = new WeightedMasterLayer(biomeRegistry, GalaxiaEndBiomeProvider.END_BIOMES)
				.apply(randomProvider.apply(1003958L));

		for (int endBiomeSize = 0; endBiomeSize <= GalaxiaWorldConfig.BIOME_SIZE_END.get(); endBiomeSize++)
		{
			endLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(49596L + endBiomeSize), endLayer);
		}

		endLayer = new GalaxiaHillsLayer(biomeRegistry, GalaxiaEndBiome.BIOME_TO_HILLS, 3)
				.apply(randomProvider.apply(2848586786L), endLayer, endLayer);
		endLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(495949586L), endLayer);
		endLayer = ZoomLayer.FUZZY.apply(randomProvider.apply(34885L), endLayer);
		endLayer = new GalaxiaEdgeLayer(biomeRegistry, GalaxiaEndBiome.BIOME_TO_EDGE)
				.apply(randomProvider.apply(2848586786L), endLayer);
		endLayer = ZoomLayer.FUZZY.apply(randomProvider.apply(395885L), endLayer);
		endLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(28475786L), endLayer);
		return new DatapackLayer(endLayer);
	}

	public static DatapackLayer stackVoidLayers(Registry<Biome> biomeRegistry, long seed)
	{
		LongFunction<IExtendedNoiseRandom<LazyArea>> randomProvider = salt -> new LazyAreaLayerContext(1, seed, salt);

		IAreaFactory<LazyArea> endLayer = new WeightedMasterLayer(biomeRegistry, GalaxiaEndBiomeProvider.VOID_BIOMES)
				.apply(randomProvider.apply(1003958L));

		for (int endBiomeSize = 0; endBiomeSize <= GalaxiaWorldConfig.VOID_BIOME_SIZE.get(); endBiomeSize++)
		{
			endLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(49596L + endBiomeSize), endLayer);
		}

		endLayer = new GalaxiaHillsLayer(biomeRegistry, GalaxiaEndBiome.BIOME_TO_HILLS, 3)
				.apply(randomProvider.apply(2848586786L), endLayer, endLayer);
		endLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(495949586L), endLayer);
		endLayer = ZoomLayer.FUZZY.apply(randomProvider.apply(34885L), endLayer);
		endLayer = new GalaxiaEdgeLayer(biomeRegistry, GalaxiaEndBiome.BIOME_TO_EDGE)
				.apply(randomProvider.apply(2848586786L), endLayer);
		endLayer = ZoomLayer.FUZZY.apply(randomProvider.apply(395885L), endLayer);
		endLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(28475786L), endLayer);

		return new DatapackLayer(endLayer);
	}
}
