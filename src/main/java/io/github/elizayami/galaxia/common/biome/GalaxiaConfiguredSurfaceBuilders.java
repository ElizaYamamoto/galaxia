package io.github.elizayami.galaxia.common.biome;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class GalaxiaConfiguredSurfaceBuilders 
{
	public static final RegistryKey<ConfiguredSurfaceBuilder<?>> IMPACT_WASTES = key("impact_wastes");
	
	public static final RegistryKey<ConfiguredSurfaceBuilder<?>> ASHSPARK_DUNES = key("ashspark_dunes");

	private static RegistryKey<ConfiguredSurfaceBuilder<?>> key(final String name) 
	{
		return RegistryKey.getOrCreateKey(Registry.CONFIGURED_SURFACE_BUILDER_KEY, new ResourceLocation(Galaxia.MOD_ID, name));
	}

	@Mod.EventBusSubscriber(modid = Galaxia.MOD_ID, bus = Bus.MOD)
	public static class RegistrationHandler 
	{
		
		@SubscribeEvent(priority = EventPriority.LOW)
		public static void register(final RegistryEvent.Register<SurfaceBuilder<?>> event) {
			register(IMPACT_WASTES,
					GalaxiaSurfaceBuilders.LOGGING_SURFACE.get().func_242929_a(new SurfaceBuilderConfig(
							BlockInit.IMPACT_SAND.get().getDefaultState(), 
							BlockInit.IMPACTSANDSTONE.stone.get().getDefaultState(), 
							BlockInit.IMPACTSANDSTONE.smooth.get().getDefaultState()))
			);
			
			register(IMPACT_WASTES,
					GalaxiaSurfaceBuilders.LOGGING_SURFACE.get().func_242929_a(new SurfaceBuilderConfig(
							BlockInit.STATIC_ASH.get().getDefaultState(), 
							BlockInit.STATIRACK.stone.get().getDefaultState(), 
							BlockInit.STATIRACK.stone.get().getDefaultState()))
			);
		}
		

		private static void register(final RegistryKey<ConfiguredSurfaceBuilder<?>> key, final ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder) {
			Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, key.getLocation(), configuredSurfaceBuilder);
		}
	}
}