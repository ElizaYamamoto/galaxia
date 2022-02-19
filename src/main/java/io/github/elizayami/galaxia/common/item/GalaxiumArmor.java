package io.github.elizayami.galaxia.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GalaxiumArmor extends ModArmor
{
    public GalaxiumArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn)
    {
        super(materialIn, slot, builderIn, "galaxium", true);
    }
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return "galaxia:textures/models/armor/galaxium"
				+ (slot == EquipmentSlotType.LEGS ? "_layer_2.png" : "_layer_1.png");
	}
}
