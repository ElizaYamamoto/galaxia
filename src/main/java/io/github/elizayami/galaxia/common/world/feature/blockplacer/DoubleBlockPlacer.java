package io.github.elizayami.galaxia.common.world.feature.blockplacer;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class DoubleBlockPlacer extends BlockPlacer
{
	public static final Codec<DoubleBlockPlacer> CODEC = Codec.unit(DoubleBlockPlacer::new);

	protected BlockPlacerType<?> getBlockPlacerType()
	{
		return GalaxiaBlockPlacerTypes.DOUBLE_BLOCK;
	}

	@Override
	public void place(IWorld world, BlockPos pos, BlockState state, Random random)
	{
		placeAt(world, pos, state, 2);
	}

	public void placeAt(IWorld worldIn, BlockPos pos, BlockState state, int flags)
	{
		worldIn.setBlockState(pos, state.with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), flags);
		worldIn.setBlockState(pos.up(), state.with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER),
				flags);
	}
}
