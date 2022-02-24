package io.github.elizayami.galaxia.common.worldgen.meteorite;

import java.util.Set;

import com.google.common.math.StatsAccumulator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class MeteoriteStructureStart extends StructureStart<NoFeatureConfig>
{
	public MeteoriteStructureStart(Structure<NoFeatureConfig> feature, int chunkX, int chunkZ, MutableBoundingBox box,
			int references, long seed)
	{
		super(feature, chunkX, chunkZ, box, references, seed);
	}

	@Override
	public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator generator,
			TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config)
	{
		final int centerX = chunkX * 16 + this.rand.nextInt(16);
		final int centerZ = chunkZ * 16 + this.rand.nextInt(16);
		final float meteoriteRadius = this.rand.nextFloat() * 6.0f + 2;
		final int yOffset = (int) Math.ceil(meteoriteRadius) + 1;

		final Set<Biome> t2 = generator.getBiomeProvider().getBiomes(centerX, 0, centerZ, 0);
		final Biome spawnBiome = t2.stream().findFirst().orElse(biome);

		final Type heightmapType = Heightmap.Type.WORLD_SURFACE_WG;

		// Accumulate stats about the surrounding heightmap
		StatsAccumulator stats = new StatsAccumulator();
		int scanRadius = (int) Math.max(1, meteoriteRadius * 2);
		for (int x = -scanRadius; x <= scanRadius; x++)
		{
			for (int z = -scanRadius; z <= scanRadius; z++)
			{
				int h = generator.getHeight(centerX + x, centerZ + z, heightmapType);
				stats.add(h);
			}
		}

		int centerY = (int) stats.mean();
		// Spawn it down a bit further with a high variance.
		if (stats.populationVariance() > 5)
		{
			centerY -= (stats.mean() - stats.min()) * .75;
		}

		// Offset caused by the meteorsize
		centerY -= yOffset;
		// Limit to not spawn below y32
		centerY = Math.max(32, centerY);

		BlockPos actualPos = new BlockPos(centerX, centerY, centerZ);

		components.add(
				new MeteoriteStructurePiece(actualPos, meteoriteRadius));

		this.recalculateStructureSize();
	}
}
