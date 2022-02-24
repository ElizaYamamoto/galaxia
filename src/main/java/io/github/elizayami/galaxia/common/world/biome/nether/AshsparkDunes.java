package io.github.elizayami.galaxia.common.world.biome.nether;

import io.github.elizayami.galaxia.common.world.biome.GalaxiaDefaultBiomeFeatures;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaNetherBiome;
import io.github.elizayami.galaxia.common.world.biome.BiomeUtil;
import io.github.elizayami.galaxia.core.world.GalaxiaConfiguredFeatures;
import io.github.elizayami.galaxia.core.world.GalaxiaSurfaceBuilders;
import io.github.elizayami.galaxia.core.world.util.WorldGenRegistrationHelper;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class AshsparkDunes extends GalaxiaNetherBiome
{
	static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = WorldGenRegistrationHelper
			.createConfiguredSurfaceBuilder("ashspark_dunes", new ConfiguredSurfaceBuilder<>(
					GalaxiaSurfaceBuilders.ASHSPARK_DUNES, GalaxiaSurfaceBuilders.Configs.BRIMSTONE));
	static final Biome.RainType PRECIPATATION = Biome.RainType.RAIN;
	static final Biome.Category CATEGORY = Biome.Category.NETHER;
	static final float DEPTH = 0.125F;
	static final float SCALE = 0.05F;
	static final float TEMPERATURE = 0.8F;
	static final float DOWNFALL = 0.4F;
	static final int WATER_COLOR = 4159204;
	static final int WATER_FOG_COLOR = 329011;

	static final Biome.Climate WEATHER = new Biome.Climate(PRECIPATATION, TEMPERATURE, Biome.TemperatureModifier.NONE,
			DOWNFALL);
	static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder();
	static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder())
			.withSurfaceBuilder(SURFACE_BUILDER);

	public AshsparkDunes()
	{
		super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR)
				.setWaterFogColor(WATER_FOG_COLOR).setFogColor(4738078).withSkyColor(BiomeUtil.calcSkyColor(2.0F))
				.setParticle(new ParticleEffectAmbience(ParticleTypes.ASH, 0.01428F))
				.setAmbientSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
				.setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
				.setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
				.setMusic(
						BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_BASALT_DELTAS))
				.build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy());
	}

	static
	{
		GENERATION_SETTINGS.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_LAVA);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
				Features.BROWN_MUSHROOM_NETHER);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
				Features.RED_MUSHROOM_NETHER);
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
		DefaultBiomeFeatures.withCommonNetherBlocks(GENERATION_SETTINGS); // Ores
		GENERATION_SETTINGS.withStructure(StructureFeatures.RUINED_PORTAL_NETHER); // NetherPortal
		GENERATION_SETTINGS.withStructure(StructureFeatures.FORTRESS); // Fortress
		GENERATION_SETTINGS.withStructure(StructureFeatures.BASTION_REMNANT); // BastionRemnant
		GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
				GalaxiaConfiguredFeatures.ORE_ANTHRACITE);
		GalaxiaDefaultBiomeFeatures.addStatic(GENERATION_SETTINGS);
		GalaxiaDefaultBiomeFeatures.addBrimstonePillars(GENERATION_SETTINGS);

		SPAWN_SETTINGS.withSpawner(EntityClassification.MONSTER,
				new MobSpawnInfo.Spawners(EntityType.ZOMBIFIED_PIGLIN, 80, 4, 4));
		SPAWN_SETTINGS.withSpawner(EntityClassification.MONSTER,
				new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 100, 2, 5));
		SPAWN_SETTINGS.withSpawner(EntityClassification.MONSTER,
				new MobSpawnInfo.Spawners(EntityType.PIGLIN, 15, 4, 4));
		SPAWN_SETTINGS.withSpawner(EntityClassification.CREATURE,
				new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2));

	}
}
