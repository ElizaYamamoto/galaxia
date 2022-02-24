package io.github.elizayami.galaxia.core.world.util;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.core.world.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class WorldGenRegistrationHelper {

    public static <SBC extends ISurfaceBuilderConfig, SB extends SurfaceBuilder<SBC>> SB createSurfaceBuilder(String id, SB surfaceBuilder) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (Registry.SURFACE_BUILDER.keySet().contains(bygID))
            throw new IllegalStateException("Surface Builder ID: \"" + bygID.toString() + "\" already exists in the Surface Builder registry!");

//        Registry.register(Registry.SURFACE_BUILDER, bygID, surfaceBuilder);
        surfaceBuilder.setRegistryName(bygID); //Forge
        GalaxiaSurfaceBuilders.surfaceBuilders.add(surfaceBuilder);
        return surfaceBuilder;
    }

    public static <SC extends ISurfaceBuilderConfig, CSB extends ConfiguredSurfaceBuilder<SC>> CSB createConfiguredSurfaceBuilder(String id, CSB configuredSurfaceBuilder) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.keySet().contains(bygID))
            throw new IllegalStateException("Configured Surface Builder ID: \"" + bygID.toString() + "\" already exists in the Configured Surface Builder registry!");

        Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, bygID, configuredSurfaceBuilder);
        return configuredSurfaceBuilder;
    }

    public static <C extends IFeatureConfig, F extends Feature<C>> F createFeature(String id, F feature) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (Registry.FEATURE.keySet().contains(bygID))
            throw new IllegalStateException("Feature ID: \"" + bygID.toString() + "\" already exists in the Features registry!");

//        Registry.register(Registry.FEATURE, bygID, feature);
        feature.setRegistryName(bygID); //Forge
        GalaxiaFeatures.features.add(feature);
        return feature;
    }

    /*public static <C extends IFeatureConfig, F extends Structure<C>> F createStructure(String id, F structure, int minChunkDistance, int maxChunkDistance, int seedModifier, GenerationStage.Decoration decorationStage) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (Registry.STRUCTURE_FEATURE.keySet().contains(bygID))
            throw new IllegalStateException("Structure Feature ID: \"" + bygID.toString() + "\" already exists in the Structure Features registry!");
        structure.setRegistryName(bygID);
        GalaxiaStructures.structures.add(structure);
        Structure.NAME_STRUCTURE_BIMAP.put(bygID.toString(), structure);

        Structure.STEP.put(structure, decorationStage);

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, new StructureSeparationSettings(minChunkDistance, maxChunkDistance, seedModifier))
                        .build();
        return structure;
    }*/


    public static <C extends IFeatureConfig, CSF extends StructureFeature<C, ?>> CSF createConfiguredStructureFeature(String id, CSF configuredStructureFeature) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE.keySet().contains(bygID))
            throw new IllegalStateException("Configured Structure Feature ID: \"" + bygID.toString() + "\" already exists in the Configured Structure Features registry!");

        Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, bygID, configuredStructureFeature);
        return configuredStructureFeature;
    }


    public static IStructurePieceType createStructurePiece(String id, IStructurePieceType piece) {
        Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(Galaxia.MOD_ID, id), piece);
        return piece;
    }

    public static <FC extends IFeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF createConfiguredFeature(String id, CF configuredFeature) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(bygID))
            throw new IllegalStateException("Configured Feature ID: \"" + bygID.toString() + "\" already exists in the Configured Features registry!");

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, bygID, configuredFeature);
        return configuredFeature;
    }

    public static <DC extends IPlacementConfig, D extends Placement<DC>> D createDecorator(String id, D decorator) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (Registry.DECORATOR.keySet().contains(bygID))
            throw new IllegalStateException("Decorator ID: \"" + bygID.toString() + "\" already exists in the Decorator registry!");

//        Registry.register(Registry.DECORATOR, bygID, decorator);
        decorator.setRegistryName(bygID); //Forge
        GalaxiaDecorators.decorators.add(decorator);
        return decorator;
    }

    static Set<Integer> integerList = new HashSet<>();

    public static Biome createBiome(String id, Biome biome, int numericalID) {
        ResourceLocation bygID = new ResourceLocation(Galaxia.MOD_ID, id);
        if (WorldGenRegistries.BIOME.keySet().contains(bygID))
            throw new IllegalStateException("Biome ID: \"" + bygID.toString() + "\" already exists in the Biome registry!");

//        Registry.register(WorldGenRegistries.BIOME, bygID, biome);
        biome.setRegistryName(bygID); //Forge

        if (integerList.contains(numericalID))
            Galaxia.LOGGER.warn("Duplicate Biome Numerical ID: " + numericalID + " at byg:" + id);

        GalaxiaBiomes.biomeList.add(new GalaxiaBiomes.PreserveBiomeOrder(biome, numericalID));
        integerList.add(numericalID);
        return biome;
    }
}