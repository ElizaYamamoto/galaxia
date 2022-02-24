package io.github.elizayami.galaxia.common.worldgen.meteorite;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public final class PlacedMeteoriteSettings
{

	private final BlockPos pos;
	private final float meteoriteRadius;

	public PlacedMeteoriteSettings(BlockPos pos, float meteoriteRadius)
	{
		this.pos = pos;
		this.meteoriteRadius = meteoriteRadius;
	}

	public BlockPos getPos()
	{
		return pos;
	}
	public float getMeteoriteRadius()
	{
		return meteoriteRadius;
	}
	
	public CompoundNBT write(CompoundNBT tag)
	{
		tag.putLong(Constants.TAG_POS, pos.toLong());

		tag.putFloat(Constants.TAG_RADIUS, meteoriteRadius);
		return tag;
	}

	public static PlacedMeteoriteSettings read(CompoundNBT tag)
	{
		BlockPos pos = BlockPos.fromLong(tag.getLong(Constants.TAG_POS));
		float meteoriteRadius = tag.getFloat(Constants.TAG_RADIUS);

		return new PlacedMeteoriteSettings(pos, meteoriteRadius);
	}

	@Override
	public String toString()
	{
		return "PlacedMeteoriteSettings [pos=" + pos + ", meteoriteRadius=" + meteoriteRadius + "]";
	}

}
