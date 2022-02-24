package io.github.elizayami.galaxia.common.world.dimension.nether;

import io.github.elizayami.galaxia.common.world.dimension.DatapackLayer;
import io.github.elizayami.galaxia.config.GalaxiaWorldConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.function.LongFunction;

public class GalaxiaNetherLayerProvider
{
	public static DatapackLayer stackLayers(Registry<Biome> biomeRegistry, long seed)
	{
		LongFunction<IExtendedNoiseRandom<LazyArea>> randomProvider = salt -> new LazyAreaLayerContext(1, seed, salt);

		IAreaFactory<LazyArea> netherLayer = new GalaxiaNetherMasterLayer(biomeRegistry)
				.apply(randomProvider.apply(485868686L));

		for (int netherBiomeSize = 0; netherBiomeSize <= GalaxiaWorldConfig.BIOME_SIZE_NETHER.get(); netherBiomeSize++)
		{
			netherLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(28585L + netherBiomeSize), netherLayer);
		}
		netherLayer = ZoomLayer.FUZZY.apply(randomProvider.apply(958687L), netherLayer);
		netherLayer = ZoomLayer.NORMAL.apply(randomProvider.apply(19375756L), netherLayer);

		return new DatapackLayer(netherLayer);
	}
}
