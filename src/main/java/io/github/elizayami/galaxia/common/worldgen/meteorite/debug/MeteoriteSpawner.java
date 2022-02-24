package io.github.elizayami.galaxia.common.worldgen.meteorite.debug;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import io.github.elizayami.galaxia.common.worldgen.meteorite.PlacedMeteoriteSettings;

public class MeteoriteSpawner
{

	public MeteoriteSpawner()
	{
	}

	public PlacedMeteoriteSettings trySpawnMeteoriteAtSuitableHeight(IWorldReader world, BlockPos startPos,
			float coreRadius, boolean worldGen)
	{
		int stepSize = Math.min(5, (int) Math.ceil(coreRadius) + 1);
		int minY = 10 + stepSize;
		BlockPos.Mutable mutablePos = startPos.toMutable();

		mutablePos.move(Direction.DOWN, stepSize);

		while (mutablePos.getY() > minY)
		{
			PlacedMeteoriteSettings spawned = trySpawnMeteorite(world, mutablePos, coreRadius);
			if (spawned != null)
			{
				return spawned;
			}

			mutablePos.setY(mutablePos.getY() - stepSize);
		}

		return null;
	}

	@Nullable
	public PlacedMeteoriteSettings trySpawnMeteorite(IWorldReader world, BlockPos pos, float coreRadius)
	{
		if (!areSurroundingsSuitable(world, pos))
		{
			return null;
		}

		int skyMode = countBlockWithSkyLight(world, pos);
		boolean placeCrater = skyMode > 10;

		boolean solid = !isAirBelowSpawnPoint(world, pos);

		return new PlacedMeteoriteSettings(pos, coreRadius);
	}

	private static boolean isAirBelowSpawnPoint(IWorldReader w, BlockPos pos)
	{
		BlockPos.Mutable testPos = pos.toMutable();
		for (int j = pos.getY() - 15; j < pos.getY() - 1; j++)
		{
			testPos.setY(j);
			if (w.isAirBlock(testPos))
			{
				return true;
			}
		}
		return false;
	}

	private int countBlockWithSkyLight(IWorldReader w, BlockPos pos)
	{
		int skyMode = 0;

		BlockPos.Mutable testPos = new BlockPos.Mutable();
		for (int i = pos.getX() - 15; i < pos.getX() + 15; i++)
		{
			testPos.setX(i);
			for (int j = pos.getY() - 15; j < pos.getY() + 11; j++)
			{
				testPos.setY(j);
				for (int k = pos.getZ() - 15; k < pos.getZ() + 15; k++)
				{
					testPos.setZ(k);
					if (w.canBlockSeeSky(testPos))
					{
						skyMode++;
					}
				}
			}
		}
		return skyMode;
	}

	private boolean areSurroundingsSuitable(IWorldReader w, BlockPos pos)
	{
		int realValidBlocks = 0;

		BlockPos.Mutable testPos = new BlockPos.Mutable();
		for (int i = pos.getX() - 6; i < pos.getX() + 6; i++)
		{
			testPos.setX(i);
			for (int j = pos.getY() - 6; j < pos.getY() + 6; j++)
			{
				testPos.setY(j);
				for (int k = pos.getZ() - 6; k < pos.getZ() + 6; k++)
				{
					testPos.setZ(k);
					Block block = w.getBlockState(testPos).getBlock();
					realValidBlocks++;
				}
			}
		}

		int validBlocks = 0;
		for (int i = pos.getX() - 15; i < pos.getX() + 15; i++)
		{
			testPos.setX(i);
			for (int j = pos.getY() - 15; j < pos.getY() + 15; j++)
			{
				testPos.setY(j);
				for (int k = pos.getZ() - 15; k < pos.getZ() + 15; k++)
				{
					testPos.setZ(k);
					Block testBlk = w.getBlockState(testPos).getBlock();
					validBlocks++;
				}
			}
		}

		final int minBlocks = 200;
		return validBlocks > minBlocks && realValidBlocks > 80;
	}

}
