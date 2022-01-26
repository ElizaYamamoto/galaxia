package io.github.elizayami.galaxia.common.abstracts.materials;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.items.PaxelItem;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
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

public class MetalMaterial 
{
	public final RegistryObject<Block> ore;
	public final RegistryObject<Block> block;
	public final RegistryObject<Block> tile;
	public final RegistryObject<Block> door;
	public final RegistryObject<Block> trapdoor;
	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	
	public final RegistryObject<Item> nugget;
	public final RegistryObject<Item> ingot;
	
	public final RegistryObject<Item> shovel;
	public final RegistryObject<Item> sword;
	public final RegistryObject<Item> pickaxe;
	public final RegistryObject<Item> axe;
	public final RegistryObject<Item> hoe;
	public final RegistryObject<Item> paxel;
	
	
	public final RegistryObject<Item> helmet;
	public final RegistryObject<Item> chestplate;
	public final RegistryObject<Item> leggings;
	public final RegistryObject<Item> boots;
	
	public final boolean hasOre;
	
	public final String name;
	
	public static MetalMaterial makeNormal(String name, MaterialColor color, IItemTier material, IArmorMaterial armor) 
	{
		return new MetalMaterial(name, true, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}
	
	public static MetalMaterial makeNormal(String name, MaterialColor color, float hardness, float resistance, IItemTier material, IArmorMaterial armor) 
	{
		return new MetalMaterial(name, true, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(hardness, resistance).sound(SoundType.METAL), new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}
	
	public static MetalMaterial makeOreless(String name, MaterialColor color, IItemTier material, IArmorMaterial armor)
	{
		return new MetalMaterial(name, false, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}
	
	public static MetalMaterial makeOreless(String name, MaterialColor color, float hardness, float resistance, IItemTier material, IArmorMaterial armor) {
		return new MetalMaterial(name, false, AbstractBlock.Properties.create(Material.IRON, color).setRequiresTool().hardnessAndResistance(hardness, resistance).sound(SoundType.METAL), new Item.Properties().group(Galaxia.galaxiaGroup), material, armor);
	}
	
	public MetalMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings, Item.Properties itemSettings, IItemTier material, IArmorMaterial armor) 
	{		
		this.hasOre = hasOre;
		
		this.name = name;
		
		final int anvilLevel = material.getHarvestLevel();
		
		ore = hasOre ? BlockInit.registerBlockWithDefaultItem(name + "_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE))) : null;
		block = BlockInit.registerBlockWithDefaultItem(name + "_block", () -> new Block(blockSettings));
		tile = BlockInit.registerBlockWithDefaultItem(name + "_tile", () -> new Block(blockSettings));
		stairs = BlockInit.registerBlockWithDefaultItem(name + "_stairs", () -> new StairsBlock(() -> tile.get().getDefaultState(), blockSettings));
		slab = BlockInit.registerBlockWithDefaultItem(name + "_slab", () -> new SlabBlock(blockSettings));
		door = BlockInit.registerBlockWithDefaultItem(name + "_door", () -> new DoorBlock(AbstractBlock.Properties.from(block.get()).notSolid()));
		trapdoor = BlockInit.registerBlockWithDefaultItem(name + "_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.from(block.get()).notSolid()));
		
		nugget = ItemInit.registerItem(name + "_nugget", () -> new Item(itemSettings));
		ingot = ItemInit.registerItem(name + "_ingot", () -> new Item(itemSettings));
		
		shovel = ItemInit.registerItem(name + "_shovel", () -> new ShovelItem(material, 1.5F, -3.0F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		sword = ItemInit.registerItem(name + "_sword", () -> new SwordItem(material, 3, -2.4F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		pickaxe = ItemInit.registerItem(name + "_pickaxe", () -> new PickaxeItem(material, 1, -2.8F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		axe = ItemInit.registerItem(name + "_axe", () -> new AxeItem(material, 6.0F, -3.0F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		hoe = ItemInit.registerItem(name + "_hoe", () -> new HoeItem(material, -3, 0.0F, new Item.Properties().group(Galaxia.galaxiaGroup)));
		paxel = ItemInit.registerItem(name + "_paxel", () -> new PaxelItem(material,  1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));
		
		helmet = ItemInit.registerItem(name + "_helmet", () -> new ArmorItem(armor, EquipmentSlotType.HEAD, itemSettings));
		chestplate = ItemInit.registerItem(name + "_chestplate", () -> new ArmorItem(armor, EquipmentSlotType.CHEST, itemSettings));
		leggings = ItemInit.registerItem(name + "_leggings", () -> new ArmorItem(armor, EquipmentSlotType.LEGS, itemSettings));
		boots = ItemInit.registerItem(name + "_boots", () -> new ArmorItem(armor, EquipmentSlotType.FEET, itemSettings));
	}
}
