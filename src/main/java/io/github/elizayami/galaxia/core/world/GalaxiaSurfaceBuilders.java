package io.github.elizayami.galaxia.core.world;

import io.github.elizayami.galaxia.common.world.surfacebuilder.*;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.world.util.WorldGenRegistrationHelper;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.ArrayList;
import java.util.List;

public class GalaxiaSurfaceBuilders
{
	public static List<SurfaceBuilder<?>> surfaceBuilders = new ArrayList<>();

	public static final SurfaceBuilder<SurfaceBuilderConfig> ASHSPARK_DUNES = WorldGenRegistrationHelper
			.createSurfaceBuilder("ashspark_dunes", new BrimstoneCavernsSB(SurfaceBuilderConfig.field_237203_a_));

	public static void init()
	{
	}

	public static class Configs
	{
		public static final SurfaceBuilderConfig SAND = new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(),
				Blocks.SAND.getDefaultState(), Blocks.SANDSTONE.getDefaultState());
		public static final SurfaceBuilderConfig STONE = new SurfaceBuilderConfig(Blocks.STONE.getDefaultState(),
				Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());
		public static final SurfaceBuilderConfig COARSE = new SurfaceBuilderConfig(Blocks.COARSE_DIRT.getDefaultState(),
				Blocks.COARSE_DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState());
		public static final SurfaceBuilderConfig WARPED_NYLIUM = new SurfaceBuilderConfig(
				Blocks.WARPED_NYLIUM.getDefaultState(), Blocks.NETHERRACK.getDefaultState(),
				Blocks.NETHERRACK.getDefaultState());

		public static final SurfaceBuilderConfig SAND_CF = new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(),
				Blocks.SAND.getDefaultState(), Blocks.SAND.getDefaultState());
		public static final SurfaceBuilderConfig REDSAND_CF = new SurfaceBuilderConfig(
				Blocks.RED_SAND.getDefaultState(), Blocks.RED_SAND.getDefaultState(),
				Blocks.RED_SAND.getDefaultState());
		public static final SurfaceBuilderConfig SOULSAND = new SurfaceBuilderConfig(Blocks.SOUL_SAND.getDefaultState(),
				Blocks.SOUL_SAND.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
		public static final SurfaceBuilderConfig SOUL_SOIL = new SurfaceBuilderConfig(
				Blocks.SOUL_SOIL.getDefaultState(), Blocks.SOUL_SOIL.getDefaultState(),
				Blocks.NETHERRACK.getDefaultState());
		public static final SurfaceBuilderConfig MAGMA_BLOCK = new SurfaceBuilderConfig(
				Blocks.MAGMA_BLOCK.getDefaultState(), Blocks.MAGMA_BLOCK.getDefaultState(),
				Blocks.MAGMA_BLOCK.getDefaultState());

		public static final SurfaceBuilderConfig AIR_CF = new SurfaceBuilderConfig(Blocks.AIR.getDefaultState(),
				Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState());

		public static final SurfaceBuilderConfig GRASSMOUNTAIN_CF = new SurfaceBuilderConfig(
				Blocks.GRASS_BLOCK.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState());

		public static final SurfaceBuilderConfig TERRACOTTA_CF = new SurfaceBuilderConfig(
				Blocks.TERRACOTTA.getDefaultState(), Blocks.TERRACOTTA.getDefaultState(),
				Blocks.TERRACOTTA.getDefaultState());

		public static final SurfaceBuilderConfig END = new SurfaceBuilderConfig(Blocks.END_STONE.getDefaultState(),
				Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState());
		public static final SurfaceBuilderConfig MYCELIUM = new SurfaceBuilderConfig(Blocks.MYCELIUM.getDefaultState(),
				Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState());

		public static final SurfaceBuilderConfig SNOW_CF = new SurfaceBuilderConfig(Blocks.SNOW_BLOCK.getDefaultState(),
				Blocks.SNOW_BLOCK.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState());
		public static final SurfaceBuilderConfig PACKED_ICE_CF = new SurfaceBuilderConfig(
				Blocks.PACKED_ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(),
				Blocks.PACKED_ICE.getDefaultState());
		public static final SurfaceBuilderConfig BLUE_ICE_CF = new SurfaceBuilderConfig(
				Blocks.BLUE_ICE.getDefaultState(), Blocks.BLUE_ICE.getDefaultState(),
				Blocks.BLUE_ICE.getDefaultState());
		
		public static final SurfaceBuilderConfig BRIMSTONE = new SurfaceBuilderConfig(BlockInit.STATIRACK.stone.get().getDefaultState(), BlockInit.STATIRACK.stone.get().getDefaultState(), BlockInit.STATIRACK.stone.get().getDefaultState());
        

	}
}
