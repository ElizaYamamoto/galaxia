package io.github.elizayami.galaxia.common;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Galaxia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy
{

	public void setup()
	{
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
	}

	public Object getArmorModel(int armorId)
	{
		return null;
	}

	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
	}

	@OnlyIn(Dist.CLIENT)
	public void postInit()
	{

	}

	@OnlyIn(Dist.CLIENT)
	public void setupClient()
	{

	}

	public void spawnParticle(String name, double x, double y, double z, double motX, double motY, double motZ)
	{
		spawnParticle(name, x, y, z, motX, motY, motZ, 1.0F);
	}

	public void spawnParticle(String name, double x, double y, double z, double motX, double motY, double motZ,
			float size)
	{
	}
}
