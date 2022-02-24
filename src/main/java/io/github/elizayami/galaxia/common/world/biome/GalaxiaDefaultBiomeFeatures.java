package io.github.elizayami.galaxia.common.world.biome;

import io.github.elizayami.galaxia.core.world.GalaxiaConfiguredFeatures;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;

public class GalaxiaDefaultBiomeFeatures
{
	public static void addMarshGrass(BiomeGenerationSettings.Builder gen)
	{
		gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_TALL_GRASS_2);
	}

	public static void addTallGrass(BiomeGenerationSettings.Builder gen)
	{
		gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_TALL_GRASS);
	}

	public static void addBrimstonePillars(BiomeGenerationSettings.Builder gen)
	{
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, GalaxiaConfiguredFeatures.BRIMSTONE_PILLARS);
	}

	public static void addStatic(BiomeGenerationSettings.Builder gen)
	{
		gen.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, GalaxiaConfiguredFeatures.STATIC);
	}
}
