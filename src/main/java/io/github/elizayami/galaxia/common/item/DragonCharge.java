package io.github.elizayami.galaxia.common.item;


import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class DragonCharge extends Item
{
	public DragonCharge(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public int getBurnTime(ItemStack i)
	{
		return 1600;
	}

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.galaxia.dragon_charge_description").mergeStyle(TextFormatting.LIGHT_PURPLE));
    }
}
