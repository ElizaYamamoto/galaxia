/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package io.github.elizayami.galaxia.common.worldgen.meteorite.fallout;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import io.github.elizayami.galaxia.common.worldgen.meteorite.MeteoriteBlockPutter;
import io.github.elizayami.galaxia.core.init.BlockInit;

public class Fallout
{
	private final MeteoriteBlockPutter putter;
	private final BlockState meteorOre;

	public Fallout(final MeteoriteBlockPutter putter, final BlockState MeteorOre)
	{
		this.putter = putter;
		this.meteorOre = MeteorOre;
	}

	public int adjustCrater()
	{
		return 0;
	}

	public void getRandomFall(final IWorld w, BlockPos pos)
	{
		final double a = Math.random();
		if (a > 0.9)
		{
			this.putter.put(w, pos, BlockInit.IMPACTSANDSTONE.smooth.get().getDefaultState());
		}
		else if (a > 0.8)
		{
			this.putter.put(w, pos, BlockInit.IMPACTSANDSTONE.stone.get().getDefaultState());
		}
		else if (a > 0.7)
		{
			this.putter.put(w, pos, BlockInit.IMPACTSANDSTONE.sand.get().getDefaultState());
		}
		else
		{
			this.putter.put(w, pos, BlockInit.IMPACTSANDSTONE.sand.get().getDefaultState());
		}
	}

	public void getRandomInset(final IWorld w, BlockPos pos)
	{
		final double a = Math.random();
		if (a > 0.9)
		{
			this.putter.put(w, pos, BlockInit.IMPACTSANDSTONE.stone.get().getDefaultState());
		}
		else if (a > 0.8)
		{
			this.putter.put(w, pos, BlockInit.IMPACTSANDSTONE.smooth.get().getDefaultState());
		}
		else if (a > 0.7)
		{
			this.putter.put(w, pos, this.meteorOre);
		}
		else if (a > 0.6)
		{
			this.putter.put(w, pos, BlockInit.IMPACTSANDSTONE.sand.get().getDefaultState());
		}
		else
		{
			this.putter.put(w, pos, Blocks.AIR.getDefaultState());
		}
	}
}
