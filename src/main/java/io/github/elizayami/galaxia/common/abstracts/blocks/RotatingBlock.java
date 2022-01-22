package io.github.elizayami.galaxia.common.abstracts.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Random;

/**
 * Created 7/10/2020 by SuperMartijn642
 */
public class RotatingBlock extends TileEntity
{
	public final int animationOffset = new Random().nextInt(20000);

	public RotatingBlock(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}
}
