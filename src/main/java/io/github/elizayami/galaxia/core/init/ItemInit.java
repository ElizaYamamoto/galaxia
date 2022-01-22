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
	
	public static final RegistryObject<Item> COMET_TRAIL_DUST = ITEMS.register("comet_trail_dust",
			() -> new Item(new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	public static final RegistryObject<Item> SOAL = ITEMS.register("soal",
			() -> new Item(new Item.Properties().group(Galaxia.galaxiaGroup)));

	public static final RegistryObject<Item> GALAXIUM_STAR = ITEMS.register("galaxium_star",
			() -> new BlockItem(BlockInit.GALAXIUM_STAR,
					new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	public static final RegistryObject<BlockItem> IMPACT_SAND = ITEMS.register("impact_sand",
			() -> new BlockItem(BlockInit.IMPACT_SAND.get(),
					new Item.Properties().group(Galaxia.galaxiaGroup)));
	//Ores
	
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
