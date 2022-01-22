package io.github.elizayami.galaxia.common.biome;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GalaxiaSurfaceBuilders 
{
	private static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Galaxia.MOD_ID);

	private static boolean isInitialised;

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> LOGGING_SURFACE = SURFACE_BUILDERS.register(
			"logging_surface",
			() -> new LoggingSurfaceBuilder<>(() -> SurfaceBuilder.DEFAULT, SurfaceBuilderConfig.field_237203_a_)
	);

	public static void initialise(final IEventBus modEventBus) {
		if (isInitialised) {
			throw new IllegalStateException("Already initialised");
		}

		SURFACE_BUILDERS.register(modEventBus);

		isInitialised = true;
	}
}