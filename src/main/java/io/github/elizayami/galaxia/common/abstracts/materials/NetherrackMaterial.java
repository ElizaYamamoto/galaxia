package io.github.elizayami.galaxia.common.abstracts.materials;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class NetherrackMaterial
{
	private static final List<NetherrackMaterial> MATERIALS = new ArrayList<>();

	public final String name;

	public final RegistryObject<Block> stone;

	public final RegistryObject<Block> bricks;
	public final RegistryObject<Block> cracked_bricks;
	public final RegistryObject<Block> chiseled;

	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	public final RegistryObject<Block> fence;
	public final RegistryObject<Block> gate;

	public final RegistryObject<Item> brick;

	public NetherrackMaterial(String name, MaterialColor color, Item.Properties itemSettings)
	{
		this.name = name;

		AbstractBlock.Properties material = AbstractBlock.Properties.create(Material.ROCK, color).setRequiresTool()
				.hardnessAndResistance(3.0F, 9.0F);

		stone = BlockInit.registerBlockWithDefaultItem(name, () -> new Block(material));
		bricks = BlockInit.registerBlockWithDefaultItem(name + "_bricks", () -> new Block(material));
		cracked_bricks = BlockInit.registerBlockWithDefaultItem(name + "_brick_cracked", () -> new Block(material));
		chiseled = BlockInit.registerBlockWithDefaultItem(name + "_brick_chiseled", () -> new WallBlock(material));

		stairs = BlockInit.registerBlockWithDefaultItem(name + "_brick_stairs",
				() -> new StairsBlock(() -> stone.get().getDefaultState(), material));
		slab = BlockInit.registerBlockWithDefaultItem(name + "_brick_slab", () -> new SlabBlock(material));
		fence = BlockInit.registerBlockWithDefaultItem(name + "_fence", () -> new FenceBlock(material));
		gate = BlockInit.registerBlockWithDefaultItem(name + "_gate", () -> new FenceGateBlock(material));

		brick = ItemInit.registerItem(name + "_brick", () -> new Item(itemSettings));
		MATERIALS.add(this);
	}

	public static Iterable<NetherrackMaterial> getMaterials()
	{
		return Iterables.unmodifiableIterable(MATERIALS);
	}
}
