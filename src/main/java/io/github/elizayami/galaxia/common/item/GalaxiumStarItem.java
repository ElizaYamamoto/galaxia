package io.github.elizayami.galaxia.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GalaxiumStarItem extends BlockItem
{
	public GalaxiumStarItem(Block block, Properties builder)
	{
		super(block, builder);
	}

	@Override
	public boolean hasContainerItem(ItemStack itemStack)
	{
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		ItemStack result = itemStack.copy();
		result.attemptDamageItem(1, Item.random, null);
		return result;
	}
}
