package io.github.elizayami.galaxia.core.init;

import java.util.function.Supplier;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit 
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Galaxia.MOD_ID);
	
	public static final RegistryObject<Item> STARDUST = ITEMS.register("stardust",
			() -> new Item(new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	public static final RegistryObject<Item> SOAL = ITEMS.register("soal",
			() -> new Item(new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	//Tools
	
	//Blocks
	public static final RegistryObject<BlockItem> COMETSTONE = ITEMS.register("cometstone",
			() -> new BlockItem(BlockInit.COMETSTONE.get(),
					new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	public static final RegistryObject<BlockItem> METEORITE = ITEMS.register("meteorite",
			() -> new BlockItem(BlockInit.METEORITE.get(),
					new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	public static final RegistryObject<BlockItem> SOAL_ORE = ITEMS.register("soal_ore",
			() -> new BlockItem(BlockInit.SOAL_ORE.get(),
					new Item.Properties().group(Galaxia.galaxiaGroup)));

	public static final RegistryObject<BlockItem> SOAL_BLOCK = ITEMS.register("soal_block",
			() -> new BlockItem(BlockInit.SOAL_BLOCK.get(),
					new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	//helper
	
	public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<? extends T> itemSupplier)
	{
		return ITEMS.register(name, itemSupplier);
	}
}
