package io.github.elizayami.galaxia.core.world;

import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.List;

import static io.github.elizayami.galaxia.core.world.util.WorldGenRegistrationHelper.createFeature;

public class GalaxiaFeatures
{

	public static List<Feature<?>> features = new ArrayList<>();
	
    public static final Feature<SimpleBlockProviderConfig> PILLARS = createFeature("pillar", new ConfigurablePillar(SimpleBlockProviderConfig.CODEC.stable()));

	public static void init()
	{
	}
}
