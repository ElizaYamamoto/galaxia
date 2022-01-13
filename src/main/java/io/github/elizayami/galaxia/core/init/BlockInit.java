package io.github.elizayami.galaxia.core.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.core.enums.ArmorMaterials;
import io.github.elizayami.galaxia.core.enums.ToolMaterials;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit 
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Galaxia.MOD_ID);
	
	//ores
	
	public static final RegistryObject<Block> SOAL_ORE = BLOCKS.
			register("soal_ore",
					() -> new Block(AbstractBlock.Properties.from(Blocks.SOUL_SAND)));
	
	//storage

	public static final RegistryObject<Block> SOAL_BLOCK = BLOCKS.
			register("soal_block",
					() -> new Block(AbstractBlock.Properties.
							from(Blocks.COAL_BLOCK).
							setLightLevel(s -> 15).
							setNeedsPostProcessing((bs, br, bp) -> true)
							.setEmmisiveRendering((bs, br, bp) -> true)));
	
	
	//Materials
	private static List<WoodenMaterial> woodenMaterials;
	public static List<WoodenMaterial> getWoodenMaterials()
	{
		return ImmutableList.copyOf(woodenMaterials);
	}
	private static List<StoneMaterial> stoneMaterials;
	public static List<StoneMaterial> getStoneMaterials() 
	{
		return ImmutableList.copyOf(stoneMaterials);
	}
	private static List<MetalMaterial> metalMaterials;
	public static List<MetalMaterial> getMetalMaterials() 
	{
		return ImmutableList.copyOf(metalMaterials);
	}
	
    public static final MetalMaterial METEOR = createMetalMaterial("meteor", true, AbstractBlock.Properties.create(Material.IRON, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().group(Galaxia.galaxiaGroup), ToolMaterials.METEOR, ArmorMaterials.METEOR);
    public static final MetalMaterial COMETSTEEL = createMetalMaterial("cometsteel", true, AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE_TERRACOTTA).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL), new Item.Properties().group(Galaxia.galaxiaGroup), ToolMaterials.COMETSTEEL, ArmorMaterials.COMETSTEEL);
    
	//helpers
	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<? extends T> blockSupplier)
	{
		return BLOCKS.register(name, blockSupplier);
	}
	
	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));
		return block;
	}

	public static <T extends Block> RegistryObject<T> registerBlockWithNoItem(String name, Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		return block;
	}
	
	public static WoodenMaterial createWoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
		if (woodenMaterials == null)
			woodenMaterials = new ArrayList<>();
		
		WoodenMaterial material = new WoodenMaterial(name, woodColor, planksColor);
		woodenMaterials.add(material);
		return material;
	}
	
	public static StoneMaterial createStoneMaterial(String name, MaterialColor color) {
		if (stoneMaterials == null)
			stoneMaterials = new ArrayList<>();
		
		StoneMaterial material = new StoneMaterial(name, color);
		stoneMaterials.add(material);
		return material;
	}
	
	public static MetalMaterial createMetalMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings, Item.Properties itemSettings, IItemTier itemTier, IArmorMaterial armor) {
		if (metalMaterials == null)
			metalMaterials = new ArrayList<>();
		
		MetalMaterial material = new MetalMaterial(name, hasOre, blockSettings, itemSettings, itemTier, armor);
		metalMaterials.add(material);
		return material;
	}
							
}
