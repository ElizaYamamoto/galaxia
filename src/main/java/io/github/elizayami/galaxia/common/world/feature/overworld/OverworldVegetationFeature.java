package io.github.elizayami.galaxia.common.world.feature.overworld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import java.util.Random;

public class OverworldVegetationFeature
{
	public OverworldVegetationFeature()
	{
	}

	public static boolean placeBonemeal(IWorld world, Random rand, BlockPos pos,
			BlockStateProvidingFeatureConfig config, int spreadRandom1, int spreadRandom2, Block self)
	{
		Block block = world.getBlockState(pos.down()).getBlock();
		if (block != self)
		{
			return false;
		}
		else
		{
			int i = pos.getY();
			if (i >= 1 && i + 1 < world.getHeight())
			{
				int j = 0;

				for (int k = 0; k < spreadRandom1 * spreadRandom1; ++k)
				{
					BlockPos blockpos = pos.add(rand.nextInt(spreadRandom1) - rand.nextInt(spreadRandom1),
							rand.nextInt(spreadRandom2) - rand.nextInt(spreadRandom2),
							rand.nextInt(spreadRandom1) - rand.nextInt(spreadRandom1));
					BlockState blockstate = config.field_227268_a_.getBlockState(rand, blockpos);
					if (world.isAirBlock(blockpos) && blockstate.isValidPosition(world, blockpos))
					{
						world.setBlockState(blockpos, blockstate, 2);
						++j;
					}
				}
				return j > 0;
			}
			else
			{
				return false;
			}
		}
	}
}