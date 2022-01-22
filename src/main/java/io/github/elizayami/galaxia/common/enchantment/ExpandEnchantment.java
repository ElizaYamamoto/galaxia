package io.github.elizayami.galaxia.common.enchantment;

import io.github.elizayami.galaxia.common.abstracts.items.LargeToolItem;
import io.github.elizayami.galaxia.core.init.enchantments.GalaxiaEnchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ExpandEnchantment extends GalaxiaEnchantment 
{
    public ExpandEnchantment(Rarity veryRare, EnchantmentType digger, EquipmentSlotType[] equipmentSlotTypes) 
    {
        super("expand", Rarity.UNCOMMON, EnchantmentType.DIGGER, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
    }

    @Override
    public int getMinEnchantability(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof LargeToolItem;
    }
}
