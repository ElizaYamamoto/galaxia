package io.github.elizayami.galaxia.common.biome;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.function.Supplier;

public class GalaxiaBiomes
{
	private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,
			Galaxia.MOD_ID);

	private static boolean isInitialised = false;

	public static final RegistryObject<Biome> IMPACT_WASTES = BIOMES.register("impact_wastes",
			() -> Utils.makeDesertBiome(Utils.surfaceBuilder(GalaxiaConfiguredSurfaceBuilders.IMPACT_WASTES), 0.125f,
					0.05f, true, true, true));

	public static final RegistryObject<Biome> ASHSPARK_DUNES = BIOMES.register("ashspark_dunes",
			() -> Utils.makeDesertBiome(Utils.surfaceBuilder(GalaxiaConfiguredSurfaceBuilders.IMPACT_WASTES), 0.125f,
					0.05f, true, true, true));

	public static void initialise(final IEventBus modEventBus)
	{
		if (isInitialised)
		{
			throw new IllegalStateException("Already initialised");
		}

		BIOMES.register(modEventBus);

		isInitialised = true;
	}

	@Mod.EventBusSubscriber(modid = Galaxia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistrationHandler
	{
		@SubscribeEvent
		public static void setupBiomes(final FMLCommonSetupEvent event)
		{
			event.enqueueWork(() ->
			{
				setupBiome(IMPACT_WASTES.get(), BiomeManager.BiomeType.DESERT, 1000, BiomeDictionary.Type.HOT,
						BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.OVERWORLD);

				setupBiome(IMPACT_WASTES.get(), BiomeManager.BiomeType.DESERT, 1000, BiomeDictionary.Type.HOT,
						BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.OVERWORLD);
			});
		}

		private static void setupBiome(final Biome biome, final BiomeManager.BiomeType biomeType, final int weight,
				final BiomeDictionary.Type... types)
		{
			BiomeDictionary.addTypes(key(biome), types);
			BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(key(biome), weight));
		}

		private static RegistryKey<Biome> key(final Biome biome)
		{
			return RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES,
					Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome), "Biome registry name was null"));
		}
	}

	private static class Utils
	{
		private static final Method GET_SKY_COLOR_WITH_TEMPERATURE_MODIFIER = ObfuscationReflectionHelper
				.findMethod(BiomeMaker.class, "getSkyColorWithTemperatureModifier", float.class);

		public static Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder(
				final RegistryKey<ConfiguredSurfaceBuilder<?>> key)
		{
			return () -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(key);
		}

		public static Biome makeAshsparkDunes()
		{
			double d0 = 0.7D;
			double d1 = 0.15D;
			MobSpawnInfo mobspawninfo = (new MobSpawnInfo.Builder())
					.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 20, 5, 5))
					.withSpawnCost(EntityType.SKELETON, 0.7D, 0.15D)
					.copy();
			BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder())
					.withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244187_s)
					.withStructure(StructureFeatures.FORTRESS)
					.withStructure(StructureFeatures.NETHER_FOSSIL)
					.withStructure(StructureFeatures.RUINED_PORTAL_NETHER)
					.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f)
					.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA)
					.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.BASALT_PILLAR)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_CRIMSON_ROOTS)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED)
					.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_SOUL_SAND);
			DefaultBiomeFeatures.withCommonNetherBlocks(biomegenerationsettings$builder);

			final int skyColour;
			try
			{
				skyColour = (int) GET_SKY_COLOR_WITH_TEMPERATURE_MODIFIER.invoke(null, 2);
			}
			catch (final IllegalAccessException | InvocationTargetException e)
			{
				throw new RuntimeException("Unable to get sky colour", e);
			}
			
			return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F)
					.scale(0.2F).temperature(2.0F).downfall(0.0F)
					.setEffects((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011)
							.setFogColor(0x5d6051).withSkyColor(skyColour)
							.setParticle(new ParticleEffectAmbience(ParticleTypes.ASH, 0.00625F))
							.setAmbientSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
							.setMoodSound(
									new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
							.setAdditionsSound(
									new SoundAdditionsAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
							.setMusic(BackgroundMusicTracks
									.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_BASALT_DELTAS))
							.build())
					.withMobSpawnSettings(mobspawninfo).withGenerationSettings(biomegenerationsettings$builder.build())
					.build();
		}

		public static Biome makeDesertBiome(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder,
				final float depth, final float scale, final boolean hasVillageAndOutpost,
				final boolean hasDesertPyramid, final boolean hasFossils)
		{
			final MobSpawnInfo.Builder mobSpawnInfoBuilder = new MobSpawnInfo.Builder();
			DefaultBiomeFeatures.withDesertMobs(mobSpawnInfoBuilder);

			final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
					.withSurfaceBuilder(surfaceBuilder);

			if (hasFossils)
			{
				DefaultBiomeFeatures.withFossils(biomeGenerationSettingBuilder);
			}

			DefaultBiomeFeatures.withStrongholdAndMineshaft(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withLavaLakes(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withMonsterRoom(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withCommonOverworldBlocks(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withOverworldOres(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withDisks(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withBadlandsGrassAndBush(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withDesertVegetation(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withNormalMushroomGeneration(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withDesertDeadBushes(biomeGenerationSettingBuilder);
			DefaultBiomeFeatures.withFrozenTopLayer(biomeGenerationSettingBuilder);

			biomeGenerationSettingBuilder.withStructure(StructureFeatures.RUINED_PORTAL_DESERT);

			final int skyColour;
			try
			{
				skyColour = (int) GET_SKY_COLOR_WITH_TEMPERATURE_MODIFIER.invoke(null, 2);
			}
			catch (final IllegalAccessException | InvocationTargetException e)
			{
				throw new RuntimeException("Unable to get sky colour", e);
			}

			return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.DESERT).depth(depth)
					.scale(scale).temperature(3).downfall(0)
					.setEffects(new BiomeAmbience.Builder().setWaterColor(0x515560).setWaterFogColor(0x373a42)
							.setFogColor(0x5c626c).withSkyColor(skyColour).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
							.build())
					.withMobSpawnSettings(mobSpawnInfoBuilder.copy())
					.withGenerationSettings(biomeGenerationSettingBuilder.build()).build();
		}
	}
}