package io.github.elizayami.galaxia.common.world.surfacebuilder;

import com.mojang.serialization.Codec;

import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class BrimstoneCavernsSB extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public BrimstoneCavernsSB(Codec<SurfaceBuilderConfig> p_i51312_1_)
	{
		super(p_i51312_1_);
	}

	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		BlockPos.Mutable block = new BlockPos.Mutable();
		int xPos = x & 15;
		int zPos = z & 15;
		for (int yPos = 256; yPos >= seaLevel; --yPos)
		{
			block.setPos(xPos, yPos, zPos);
			BlockState currentBlockToReplace = chunkIn.getBlockState(block);
			BlockState checkAirForSB = chunkIn.getBlockState(block.up());
			if (currentBlockToReplace == Blocks.NETHERRACK.getDefaultState()
					&& !(checkAirForSB.getBlock() == Blocks.AIR))
				chunkIn.setBlockState(block, BlockInit.STATIRACK.stone.get().getDefaultState(), false);
			if (currentBlockToReplace.getBlock() == Blocks.NETHERRACK && checkAirForSB.getBlock() == Blocks.AIR)
				chunkIn.setBlockState(block, BlockInit.STATIRACK.stone.get().getDefaultState(), false);
		}
	}
}