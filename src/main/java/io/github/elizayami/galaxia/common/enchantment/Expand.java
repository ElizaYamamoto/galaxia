package io.github.elizayami.galaxia.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class Expand extends Enchantment
{
	public Expand(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots)
	{
		super(rarityIn, typeIn, slots);
	}
	
	public int getMaxLevel() 
	{
		return 2;
	}
}
