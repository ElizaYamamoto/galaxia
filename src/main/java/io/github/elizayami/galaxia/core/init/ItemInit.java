package io.github.elizayami.galaxia.core.init;

import java.util.function.Supplier;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.item.DragonCharge;
import io.github.elizayami.galaxia.common.item.Soal;
import io.github.elizayami.galaxia.common.item.SoalBlock;
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
			() -> new Soal(new Item.Properties().group(Galaxia.galaxiaGroup)));

	public static final RegistryObject<Item> DRAGON_CHARGE = ITEMS.register("dragon_charge",
			() -> new DragonCharge(new Item.Properties().group(Galaxia.galaxiaGroup)));

	public static final RegistryObject<Item> GALAXIUM_STAR = ITEMS.register("galaxium_star",
			() -> new BlockItem(BlockInit.GALAXIUM_STAR, new Item.Properties().group(Galaxia.galaxiaGroup)));

	public static final RegistryObject<BlockItem> IMPACT_SAND = ITEMS.register("impact_sand",
			() -> new BlockItem(BlockInit.IMPACT_SAND.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));
	// Ores

	public static final RegistryObject<BlockItem> SOAL_ORE = ITEMS.register("soal_ore",
			() -> new BlockItem(BlockInit.SOAL_ORE.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));

	public static final RegistryObject<BlockItem> SOAL_BLOCK = ITEMS.register("soal_block",
			() -> new SoalBlock(BlockInit.SOAL_BLOCK.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));
	
	// Boats

	public static final RegistryHelper RH = Galaxia.BOAT_HELPER.getItemHelper();

	public static final RegistryObject<Item> SHADOWSPIKE_BOAT = RH.createBoatItem("shadowspike", BlockInit.SHADOWSPIKE.planks);
	public static final RegistryObject<Item> GROUNDSTALK_BOAT = RH.createBoatItem("groundstalk", BlockInit.SHADOWSPIKE.planks);
	public static final RegistryObject<Item> SEAWOOD_BOAT = RH.createBoatItem("seawood", BlockInit.SHADOWSPIKE.planks);
	public static final RegistryObject<Item> SCORCHWOOD_BOAT = RH.createBoatItem("scorchwood", BlockInit.SHADOWSPIKE.planks);
	public static final RegistryObject<Item> GHOSTWOOD_BOAT = RH.createBoatItem("ghostwood", BlockInit.SHADOWSPIKE.planks);
	public static final RegistryObject<Item> GROVEWOOD_BOAT = RH.createBoatItem("grovewood", BlockInit.SHADOWSPIKE.planks);

	// helper

	public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<? extends T> itemSupplier)
	{
		return ITEMS.register(name, itemSupplier);
	}
}
