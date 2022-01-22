package io.github.elizayami.galaxia.common;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.item.GalaxiumArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Galaxia.MOD_ID)
public class ServerEvents 
{
    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) 
    {
        try {
            if (event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() instanceof GalaxiumArmor &&
            		event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() instanceof GalaxiumArmor &&
            		event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() instanceof GalaxiumArmor &&
            		event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET).getItem() instanceof GalaxiumArmor) 
            {
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.STRENGTH, 50, 1, false, false));
            }
        } catch (Exception e) {

        }
    }
}
