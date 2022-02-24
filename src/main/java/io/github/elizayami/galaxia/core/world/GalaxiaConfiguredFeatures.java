package io.github.elizayami.galaxia.core.world;

import com.google.common.collect.ImmutableSet;
import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;

import static io.github.elizayami.galaxia.core.world.util.WorldGenRegistrationHelper.createConfiguredFeature;

public class GalaxiaConfiguredFeatures
{
	public static final ConfiguredFeature<?, ?> STATIC_PATCH = createConfiguredFeature("boric_fire_patch",
			Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
					new SimpleBlockStateProvider(BlockInit.STATIC.get().getDefaultState()), new SimpleBlockPlacer()))
							.tries(64)
							.whitelist(ImmutableSet.of(BlockInit.STATIRACK.stone.get(), BlockInit.STATIC_ASH.get()))
							.func_227317_b_().build()));

	public static final ConfiguredFeature<?, ?> ORE_ANTHRACITE = createConfiguredFeature("ore_anthracite",
			Feature.ORE
					.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK,
							BlockInit.BOLTRINE.ore.get().getDefaultState(), 14))
					.withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).square().func_242731_b(12));

	public static final ConfiguredFeature<?, ?> BRIMSTONE_PILLARS = createConfiguredFeature("brimstone_pillars",
			GalaxiaFeatures.PILLARS
					.withConfiguration((new SimpleBlockProviderConfig(
							new SimpleBlockStateProvider(BlockInit.STATIRACK.stone.get().getDefaultState()))))
					.range(128).square().func_242731_b(80));

	public static final ConfiguredFeature<?, ?> STATIC = createConfiguredFeature("boric_fire",
			STATIC_PATCH.withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
}
