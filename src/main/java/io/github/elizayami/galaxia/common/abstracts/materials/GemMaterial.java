package io.github.elizayami.galaxia.common.abstracts.materials;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.items.BackhawItem;
import io.github.elizayami.galaxia.common.abstracts.items.BackhoeItem;
import io.github.elizayami.galaxia.common.abstracts.items.HammerItem;
import io.github.elizayami.galaxia.common.abstracts.items.PaxelItem;
import io.github.elizayami.galaxia.common.abstracts.items.SawItem;
import io.github.elizayami.galaxia.common.abstracts.items.TillerItem;
import io.github.elizayami.galaxia.common.item.BoltrineArmor;
import io.github.elizayami.galaxia.common.item.GalaxiumArmor;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;

public class GemMaterial
{
	public final RegistryObject<Block> ore;
	public final RegistryObject<Block> block;
	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> tile;
	public final RegistryObject<Block> slab;

	public final RegistryObject<Item> gem;

	public final RegistryObject<Item> shovel;
	public final RegistryObject<Item> sword;
	public final RegistryObject<Item> pickaxe;
	public final RegistryObject<Item> axe;
	public final RegistryObject<Item> hoe;
	public final RegistryObject<Item> paxel;

	public final RegistryObject<Item> hammer;
	public final RegistryObject<Item> saw;
	public final RegistryObject<Item> backhoe;
	public final RegistryObject<Item> tiller;
	public final RegistryObject<Item> backhaw;

	public final RegistryObject<Item> helmet;
	public final RegistryObject<Item> chestplate;
	public final RegistryObject<Item> leggings;
	public final RegistryObject<Item> boots;

	public final boolean hasOre;

	public final String name;

	public static GemMaterial makeNormal(String name, MaterialColor color, IItemTier material, IArmorMaterial armor)
	{
		return new GemMaterial(name, true,
				AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool()
						.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL),
				new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}

	public static GemMaterial makeNormal(String name, MaterialColor color, float hardness, float resistance,
			IItemTier material, IArmorMaterial armor)
	{
		return new GemMaterial(name, true,
				AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool()
						.hardnessAndResistance(hardness, resistance).sound(SoundType.METAL),
				new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}

	public static GemMaterial makeOreless(String name, MaterialColor color, IItemTier material, IArmorMaterial armor)
	{
		return new GemMaterial(name, false,
				AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool()
						.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL),
				new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}

	public static GemMaterial makeOreless(String name, MaterialColor color, float hardness, float resistance,
			IItemTier material, IArmorMaterial armor)
	{
		return new GemMaterial(name, false,
				AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool()
						.hardnessAndResistance(hardness, resistance).sound(SoundType.METAL),
				new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}

	public GemMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings,
			Item.Properties itemSettings, IItemTier material, IArmorMaterial armor)
	{
		this.hasOre = hasOre;

		this.name = name;

		final int anvilLevel = material.getHarvestLevel();

		ore = hasOre ? BlockInit.registerBlockWithDefaultItem(name + "_ore",
				() -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE))) : null;
		block = BlockInit.registerBlockWithDefaultItem(name + "_block", () -> new Block(blockSettings));

		if (name != "galaxium")
		{
			gem = ItemInit.registerItem(name, () -> new Item(itemSettings));
		}
		else
		{
			gem = ItemInit.registerItem("stardust", () -> new Item(itemSettings));
		}

		tile = BlockInit.registerBlockWithDefaultItem(name + "_tile", () -> new Block(blockSettings));
		stairs = BlockInit.registerBlockWithDefaultItem(name + "_stairs",
				() -> new StairsBlock(() -> tile.get().getDefaultState(), blockSettings));
		slab = BlockInit.registerBlockWithDefaultItem(name + "_slab", () -> new SlabBlock(blockSettings));

		shovel = ItemInit.registerItem(name + "_shovel",
				() -> new ShovelItem(material, 1.5F, -3.0F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		sword = ItemInit.registerItem(name + "_sword",
				() -> new SwordItem(material, 3, -2.4F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		pickaxe = ItemInit.registerItem(name + "_pickaxe",
				() -> new PickaxeItem(material, 1, -2.8F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		axe = ItemInit.registerItem(name + "_axe",
				() -> new AxeItem(material, 6.0F, -3.0F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		hoe = ItemInit.registerItem(name + "_hoe",
				() -> new HoeItem(material, -3, 0.0F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		paxel = ItemInit.registerItem(name + "_paxel",
				() -> new PaxelItem(material, 1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));

		hammer = ItemInit.registerItem(name + "_hammer",
				() -> new HammerItem(material, 1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));
		saw = ItemInit.registerItem(name + "_saw",
				() -> new SawItem(material, 1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));
		backhoe = ItemInit.registerItem(name + "_backhoe",
				() -> new BackhoeItem(material, 1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));
		tiller = ItemInit.registerItem(name + "_tiller",
				() -> new TillerItem(material, 1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));
		backhaw = ItemInit.registerItem(name + "_backhaw",
				() -> new BackhawItem(material, 1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));

		if (name == "galaxium")
		{
			String sub = "galaxia:galaxium";

			helmet = ItemInit.registerItem(name + "_helmet",
					() -> new GalaxiumArmor(armor, EquipmentSlotType.HEAD, itemSettings));
			chestplate = ItemInit.registerItem(name + "_chestplate",
					() -> new GalaxiumArmor(armor, EquipmentSlotType.CHEST, itemSettings));
			leggings = ItemInit.registerItem(name + "_leggings",
					() -> new GalaxiumArmor(armor, EquipmentSlotType.LEGS, itemSettings));
			boots = ItemInit.registerItem(name + "_boots",
					() -> new GalaxiumArmor(armor, EquipmentSlotType.FEET, itemSettings));

		}
		else if (name == "boltrine")
		{
			String sub = "boltrine";

			helmet = ItemInit.registerItem(name + "_helmet",
					() -> new BoltrineArmor(armor, EquipmentSlotType.HEAD, itemSettings));
			chestplate = ItemInit.registerItem(name + "_chestplate",
					() -> new BoltrineArmor(armor, EquipmentSlotType.CHEST, itemSettings));
			leggings = ItemInit.registerItem(name + "_leggings",
					() -> new BoltrineArmor(armor, EquipmentSlotType.LEGS, itemSettings));
			boots = ItemInit.registerItem(name + "_boots",
					() -> new BoltrineArmor(armor, EquipmentSlotType.FEET, itemSettings));

		}
		else
		{
			helmet = ItemInit.registerItem(name + "_helmet",
					() -> new ArmorItem(armor, EquipmentSlotType.HEAD, itemSettings));
			chestplate = ItemInit.registerItem(name + "_chestplate",
					() -> new ArmorItem(armor, EquipmentSlotType.CHEST, itemSettings));
			leggings = ItemInit.registerItem(name + "_leggings",
					() -> new ArmorItem(armor, EquipmentSlotType.LEGS, itemSettings));
			boots = ItemInit.registerItem(name + "_boots",
					() -> new ArmorItem(armor, EquipmentSlotType.FEET, itemSettings));
		}
	}
}
