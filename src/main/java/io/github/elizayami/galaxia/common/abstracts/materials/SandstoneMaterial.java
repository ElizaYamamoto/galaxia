package io.github.elizayami.galaxia.common.abstracts.materials;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

public class SandstoneMaterial 
{
	private static final List<SandstoneMaterial> MATERIALS = new ArrayList<>();
	
	public final String name;

	public final RegistryObject<Block> stone;
	
	public final RegistryObject<Block> chiseled;
	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	public final RegistryObject<Block> wall;
	
	public final RegistryObject<Block> smooth;
	public final RegistryObject<Block> smooth_stairs;
	public final RegistryObject<Block> smooth_slab;
	public final RegistryObject<Block> smooth_wall;
	
	public SandstoneMaterial(String name, MaterialColor color) 
	{
		this.name = name;
		
		AbstractBlock.Properties material = AbstractBlock.Properties.create(Material.ROCK, color).
				                                                     setRequiresTool().
				                                                     hardnessAndResistance(3.0F, 9.0F);

		stone = BlockInit.registerBlockWithDefaultItem(name, 
				() -> new Block(material));
		chiseled = BlockInit.registerBlockWithDefaultItem(name + "_chiseled", 
				() -> new Block(material));
		stairs = BlockInit.registerBlockWithDefaultItem(name + "_stairs", 
				() -> new StairsBlock(() -> stone.get().getDefaultState(), material));
		slab = BlockInit.registerBlockWithDefaultItem(name + "_slab", 
				() -> new SlabBlock(material));
		wall = BlockInit.registerBlockWithDefaultItem(name + "_wall", 
				() -> new WallBlock(material));
		smooth = BlockInit.registerBlockWithDefaultItem(name + "_smooth",
				() -> new Block(material));
		smooth_stairs = BlockInit.registerBlockWithDefaultItem(name + "_smooth_stairs", 
				() -> new StairsBlock(() -> smooth.get().getDefaultState(), material));
		smooth_slab = BlockInit.registerBlockWithDefaultItem(name + "_smooth_slab", 
				() -> new SlabBlock(material));
		smooth_wall = BlockInit.registerBlockWithDefaultItem(name + "_smooth_wall", 
				() -> new WallBlock(material));
		MATERIALS.add(this);
	}
	
	public static Iterable<SandstoneMaterial> getMaterials() {
		return Iterables.unmodifiableIterable(MATERIALS);
	}
}
