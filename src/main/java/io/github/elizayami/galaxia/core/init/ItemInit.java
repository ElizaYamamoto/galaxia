package io.github.elizayami.galaxia.core.init;

import java.util.function.Supplier;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.items.GalaxiaBoatItem;
import io.github.elizayami.galaxia.common.entities.boat.GalaxiaBoatEntity;
import io.github.elizayami.galaxia.common.item.DragonCharge;
import io.github.elizayami.galaxia.common.item.GalaxiumStarItem;
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
			() -> new GalaxiumStarItem(BlockInit.GALAXIUM_STAR,
					new Item.Properties().group(Galaxia.galaxiaGroup).maxDamage(4)));

	public static final RegistryObject<BlockItem> STATIC_ASH = ITEMS.register("static_ash",
			() -> new BlockItem(BlockInit.STATIC_ASH.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));

	// Ores

	public static final RegistryObject<BlockItem> SOAL_ORE = ITEMS.register("soal_ore",
			() -> new BlockItem(BlockInit.SOAL_ORE.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));

	public static final RegistryObject<BlockItem> SOAL_BLOCK = ITEMS.register("soal_block",
			() -> new SoalBlock(BlockInit.SOAL_BLOCK.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));

	// Boats

	public static final RegistryObject<Item> SHADOWSPIKE_BOAT = ITEMS.register("shadowspike_boat",
			() -> new GalaxiaBoatItem(GalaxiaBoatEntity.GalaxiaType.SHADOWSPIKE,
					new Item.Properties().group(Galaxia.galaxiaGroup).maxStackSize(1)));

	public static final RegistryObject<Item> GROUNDSTALK_BOAT = ITEMS.register("groundstalk_boat",
			() -> new GalaxiaBoatItem(GalaxiaBoatEntity.GalaxiaType.GROUNDSTALK,
					new Item.Properties().group(Galaxia.galaxiaGroup).maxStackSize(1)));

	public static final RegistryObject<Item> SEAWOOD_BOAT = ITEMS.register("seawood_boat",
			() -> new GalaxiaBoatItem(GalaxiaBoatEntity.GalaxiaType.SEAWOOD,
					new Item.Properties().group(Galaxia.galaxiaGroup).maxStackSize(1)));

	public static final RegistryObject<Item> SCORCHWOOD_BOAT = ITEMS.register("scorchwood_boat",
			() -> new GalaxiaBoatItem(GalaxiaBoatEntity.GalaxiaType.SCORCHWOOD,
					new Item.Properties().group(Galaxia.galaxiaGroup).maxStackSize(1)));

	public static final RegistryObject<Item> GHOSTWOOD_BOAT = ITEMS.register("ghostwood_boat",
			() -> new GalaxiaBoatItem(GalaxiaBoatEntity.GalaxiaType.GHOSTWOOD,
					new Item.Properties().group(Galaxia.galaxiaGroup).maxStackSize(1)));

	public static final RegistryObject<Item> GROVEWOOD_BOAT = ITEMS.register("grovewood_boat",
			() -> new GalaxiaBoatItem(GalaxiaBoatEntity.GalaxiaType.GROVEWOOD,
					new Item.Properties().group(Galaxia.galaxiaGroup).maxStackSize(1)));

	// helper

	public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<? extends T> itemSupplier)
	{
		return ITEMS.register(name, itemSupplier);
	}
}
