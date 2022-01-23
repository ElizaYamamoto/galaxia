package io.github.elizayami.galaxia.core.init;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.blocks.GalaxiaSignBlock;
import io.github.elizayami.galaxia.common.tileentity.GalaxiaBarrelTileEntity;
import io.github.elizayami.galaxia.common.tileentity.GalaxiaChestTileEntity;
import io.github.elizayami.galaxia.common.tileentity.GalaxiaSignTileEntity;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
			DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Galaxia.MOD_ID);


	public static final RegistryObject<TileEntityType<GalaxiaChestTileEntity>> CHEST = TILE_ENTITY_TYPES.register("chest",
			() -> TileEntityType.Builder.create(GalaxiaChestTileEntity::new, getChests()).build(null));

	public static final RegistryObject<TileEntityType<GalaxiaSignTileEntity>> SIGN = TILE_ENTITY_TYPES.register("sign",
			() -> TileEntityType.Builder.create(GalaxiaSignTileEntity::new, getSigns()).build(null));

	public static final RegistryObject<TileEntityType<GalaxiaBarrelTileEntity>> BARREL = TILE_ENTITY_TYPES.register(
			"barrel", () -> TileEntityType.Builder.create(GalaxiaBarrelTileEntity::new, getBarrels()).build(null));

	static Block[] getChests()
	{
		List<Block> result = Lists.newArrayList();
		ItemInit.ITEMS.getEntries().forEach((item) ->
		{
			if (item.get() instanceof BlockItem)
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof ChestBlock)
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[]
		{});
	}

	static Block[] getSigns()
	{
		List<Block> result = Lists.newArrayList();
		ItemInit.ITEMS.getEntries().forEach((item) ->
		{
			if (item.get() instanceof BlockItem)
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof GalaxiaSignBlock)
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[]
		{});
	}

	static Block[] getBarrels()
	{
		List<Block> result = Lists.newArrayList();
		ItemInit.ITEMS.getEntries().forEach((item) ->
		{
			if (item.get() instanceof BlockItem)
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof BarrelBlock)
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[]
		{});
	}

}
