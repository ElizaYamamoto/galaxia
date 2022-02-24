package io.github.elizayami.galaxia.common.abstracts.materials;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

public class StoneMaterial
{
	private static final List<StoneMaterial> MATERIALS = new ArrayList<>();

	public final String name;

	public final RegistryObject<Block> stone;

	public final RegistryObject<Block> polished;
	public final RegistryObject<Block> tiles;
	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	public final RegistryObject<Block> wall;
	public final RegistryObject<Block> button;
	public final RegistryObject<Block> pressure_plate;

	public final RegistryObject<Block> bricks;
	public final RegistryObject<Block> brick_stairs;
	public final RegistryObject<Block> brick_slab;
	public final RegistryObject<Block> brick_wall;

	public StoneMaterial(String name, MaterialColor color)
	{
		this.name = name;

		AbstractBlock.Properties material = AbstractBlock.Properties.create(Material.ROCK, color).setRequiresTool().hardnessAndResistance(3.0F, 9.0F);

		stone = BlockInit.registerBlockWithDefaultItem(name, () -> new Block(material));
		polished = BlockInit.registerBlockWithDefaultItem(name + "_polished", () -> new Block(material));
		tiles = BlockInit.registerBlockWithDefaultItem(name + "_tile", () -> new Block(material));
		stairs = BlockInit.registerBlockWithDefaultItem(name + "_stairs", () -> new StairsBlock(() -> stone.get().getDefaultState(), material));
		slab = BlockInit.registerBlockWithDefaultItem(name + "_slab", () -> new SlabBlock(material));
		wall = BlockInit.registerBlockWithDefaultItem(name + "_wall", () -> new WallBlock(material));
		button = BlockInit.registerBlockWithDefaultItem(name + "_button", () -> new StoneButtonBlock(material));
		pressure_plate = BlockInit.registerBlockWithDefaultItem(name + "_pressure_plate", () -> new PressurePlateBlock(Sensitivity.MOBS, material));
		bricks = BlockInit.registerBlockWithDefaultItem(name + "_bricks", () -> new Block(material));
		brick_stairs = BlockInit.registerBlockWithDefaultItem(name + "_bricks_stairs", () -> new StairsBlock(() -> bricks.get().getDefaultState(), material));
		brick_slab = BlockInit.registerBlockWithDefaultItem(name + "_bricks_slab", () -> new SlabBlock(material));
		brick_wall = BlockInit.registerBlockWithDefaultItem(name + "_bricks_wall", () -> new WallBlock(material));
		MATERIALS.add(this);
	}

	public static Iterable<StoneMaterial> getMaterials()
	{
		return Iterables.unmodifiableIterable(MATERIALS);
	}
}
