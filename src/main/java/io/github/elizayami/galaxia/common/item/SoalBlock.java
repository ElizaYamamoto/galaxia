package io.github.elizayami.galaxia.common.item;

import javax.annotation.Nullable;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class SoalBlock extends BlockItem
{
	public SoalBlock(Block block, Properties builder)
	{
		super(block, builder);
	}

    @Override
    public int getBurnTime(ItemStack i) 
    {
        return 10800;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("block.galaxia.soal_block_description").mergeStyle(TextFormatting.AQUA));
    }
}
