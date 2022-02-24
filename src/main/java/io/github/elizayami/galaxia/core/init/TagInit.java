package io.github.elizayami.galaxia.core.init;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TagInit
{
	// MOD BLOCK TAGS
	public static final ITag.INamedTag<Block> BLOCK_BARRELS = makeBlockTag("forge", "barrels/wooden");

	// CUSTOM FORGE ITEM TAGS
	public static final ITag.INamedTag<Item> ITEM_WORKBENCH = makeItemTag("forge", "workbench");

	// Used by the Metal Barrels mod
	public static final ITag.INamedTag<Item> ITEM_BARRELS = makeItemTag("forge", "barrels/wooden");

	public static ITag.INamedTag<Block> makeModBlockTag(final String name)
	{
		return BlockTags.makeWrapperTag(new ResourceLocation(Galaxia.MOD_ID, name).toString());
	}

	public static ITag.INamedTag<Item> makeModItemTag(final String name)
	{
		return ItemTags.makeWrapperTag(new ResourceLocation(Galaxia.MOD_ID, name).toString());
	}

	public static ITag.INamedTag<Block> makeBlockTag(String namespace, String name)
	{
		return BlockTags.makeWrapperTag(new ResourceLocation(namespace, name).toString());
	}

	public static ITag.INamedTag<Item> makeItemTag(String namespace, String name)
	{
		return ItemTags.makeWrapperTag(new ResourceLocation(namespace, name).toString());
	}
}
