package io.github.elizayami.galaxia.client;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.client.models.armor.ModelBoltrineArmor;
import io.github.elizayami.galaxia.client.models.armor.ModelGalaxiumArmor;
import io.github.elizayami.galaxia.common.CommonProxy;
import io.github.elizayami.galaxia.common.particle.ParticleSpark;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Galaxia.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy
{

	private static final ModelGalaxiumArmor GALAXIUM_ARMOR_MODEL = new ModelGalaxiumArmor(0.6F, false);
	private static final ModelGalaxiumArmor GALAXIUM_ARMOR_MODEL_LEGS = new ModelGalaxiumArmor(0.3F, true);

	private static final ModelBoltrineArmor BOLTRINE_ARMOR_MODEL = new ModelBoltrineArmor(0.6F, false);
	private static final ModelBoltrineArmor BOLTRINE_ARMOR_MODEL_LEGS = new ModelBoltrineArmor(0.3F, true);

	@OnlyIn(Dist.CLIENT)
	public Object getArmorModel(int armorId)
	{
		switch (armorId)
		{
		case 0:
			return GALAXIUM_ARMOR_MODEL;
		case 1:
			return GALAXIUM_ARMOR_MODEL_LEGS;
		case 2:
			return BOLTRINE_ARMOR_MODEL;
		case 3:
			return BOLTRINE_ARMOR_MODEL_LEGS;
		}
		return null;
	}

	@OnlyIn(Dist.CLIENT)
	public void postInit()
	{

	}

	@OnlyIn(Dist.CLIENT)
	public void setupClient()
	{

	}

	@OnlyIn(Dist.CLIENT)
	public void spawnParticle(String name, double x, double y, double z, double motX, double motY, double motZ, float size)
	{
		ClientWorld world = Minecraft.getInstance().world;
		if (world == null)
		{
			return;
		}
		net.minecraft.client.particle.Particle particle = null;
		if (name.equals("spark"))
		{
			particle = new ParticleSpark(world, x, y, z);
		}
		if (particle != null)
		{
			Minecraft.getInstance().particles.addEffect(particle);
		}
	}

	public PlayerEntity getClientSidePlayer()
	{
		return Minecraft.getInstance().player;
	}
}
