package io.github.elizayami.galaxia.client;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.client.models.ModelGalaxiumArmor;
import io.github.elizayami.galaxia.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Galaxia.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy
{

	private static final ModelGalaxiumArmor GALAXIUMM_ARMOR_MODEL = new ModelGalaxiumArmor(0.6F, false);
	private static final ModelGalaxiumArmor GALAXIUMM_ARMOR_MODEL_LEGS = new ModelGalaxiumArmor(0.3F, true);


	@OnlyIn(Dist.CLIENT)
	public Object getArmorModel(int armorId)
	{
		switch (armorId)
		{
		case 0:
			return GALAXIUMM_ARMOR_MODEL;
		case 1:
			return GALAXIUMM_ARMOR_MODEL_LEGS;
		}
		return null;
	}

    @OnlyIn(Dist.CLIENT)
    public void postInit() {

    }

    @OnlyIn(Dist.CLIENT)
    public void setupClient() 
    {
    	
    }

	public PlayerEntity getClientSidePlayer()
	{
		return Minecraft.getInstance().player;
	}
}
