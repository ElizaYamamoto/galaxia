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

public class Soal extends Item
{
	public Soal(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public int getBurnTime(ItemStack i)
	{
		return 1200;
	}

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.galaxia.soal_description").mergeStyle(TextFormatting.AQUA));
    }
}
