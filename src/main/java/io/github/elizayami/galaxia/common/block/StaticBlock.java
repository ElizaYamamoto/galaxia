package io.github.elizayami.galaxia.common.block;

import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class StaticBlock extends AbstractFireBlock
{

	public StaticBlock(AbstractBlock.Properties properties)
	{
		super(properties, 3.5F);
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos)
	{
		return this.canSurvive(stateIn, worldIn, currentPos) ? this.getDefaultState() : Blocks.AIR.getDefaultState();
	}

	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return shouldLightStatic(worldIn.getBlockState(pos.down()).getBlock());
	}

	public static boolean shouldLightStatic(Block block)
	{
		return block == BlockInit.STATIRACK.stone.get() || block == BlockInit.STATIC_ASH.get();
	}

	protected boolean canBurn(BlockState state)
	{
		return true;
	}
}
