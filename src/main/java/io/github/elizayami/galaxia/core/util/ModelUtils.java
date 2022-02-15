package io.github.elizayami.galaxia.core.util;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.util.ResourceLocation;

public class ModelUtils
{
	public static ResourceLocation entityModel(String name)
	{
		return new ResourceLocation(Galaxia.MOD_ID, "geo/entity/" + name + ".geo.json");
	}
	
	public static ResourceLocation itemModel(String name)
	{
		return new ResourceLocation(Galaxia.MOD_ID, "geo/item/" + name + ".geo.json");
	}
	
	public static ResourceLocation blockModel(String name)
	{
		return new ResourceLocation(Galaxia.MOD_ID, "geo/block/" + name + ".geo.json");
	}
	
	public static ResourceLocation animation(String name)
	{
		return new ResourceLocation(Galaxia.MOD_ID, "animation/" + name + ".animation.json");
	}
}
