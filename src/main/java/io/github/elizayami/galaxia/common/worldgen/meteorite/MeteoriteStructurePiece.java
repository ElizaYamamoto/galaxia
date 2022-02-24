package io.github.elizayami.galaxia.common.worldgen.meteorite;

import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class MeteoriteStructurePiece extends StructurePiece
{

	public static final IStructurePieceType TYPE = IStructurePieceType.register(MeteoriteStructurePiece::new,
			"galaxiameteor");

	public static void register()
	{
		// THIS MUST BE CALLED otherwise the static initializer above will not run,
		// unless world generation is actually invoked, which means that chunks may
		// be loaded without this being registered as a structure piece!
	}

	private PlacedMeteoriteSettings settings;

	protected MeteoriteStructurePiece(BlockPos center, float coreRadius)
	{
		super(TYPE, 0);
		this.settings = new PlacedMeteoriteSettings(center, coreRadius);

		int range = 4 * 16;

		ChunkPos chunkPos = new ChunkPos(center);
		this.boundingBox = new MutableBoundingBox(chunkPos.getXStart() - range, center.getY(),
				chunkPos.getZStart() - range, chunkPos.getXEnd() + range, center.getY(), chunkPos.getZEnd() + range);
	}

	public MeteoriteStructurePiece(TemplateManager templateManager, CompoundNBT tag)
	{
		super(TYPE, tag);

		BlockPos center = BlockPos.fromLong(tag.getLong(Constants.TAG_POS));
		float coreRadius = tag.getFloat(Constants.TAG_RADIUS);

		this.settings = new PlacedMeteoriteSettings(center, coreRadius);
	}

	public PlacedMeteoriteSettings getSettings()
	{
		return settings;
	}

	@Override
	protected void readAdditional(CompoundNBT tag)
	{
		tag.putFloat(Constants.TAG_RADIUS, settings.getMeteoriteRadius());
		tag.putLong(Constants.TAG_POS, settings.getPos().toLong());
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager p_230383_2_, ChunkGenerator chunkGeneratorIn,
			Random rand, MutableBoundingBox bounds, ChunkPos chunkPos, BlockPos p_230383_7_)
	{
		MeteoritePlacer placer = new MeteoritePlacer(world, settings, bounds);
		placer.place();
		return true;
	}
}
