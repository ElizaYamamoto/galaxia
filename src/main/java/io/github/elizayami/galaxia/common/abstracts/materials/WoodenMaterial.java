package io.github.elizayami.galaxia.common.abstracts.materials;

import java.util.function.Supplier;

import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import io.github.elizayami.galaxia.core.init.TagInit;
import io.github.elizayami.galaxia.core.init.TileEntityInit;
import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.client.renderer.ChestItemTileEntityRenderer;
import io.github.elizayami.galaxia.common.abstracts.blocks.BarkBlockTemplate;
import io.github.elizayami.galaxia.common.abstracts.blocks.GalaxiaBarrelBlock;
import io.github.elizayami.galaxia.common.abstracts.blocks.GalaxiaCraftingTableBlock;
import io.github.elizayami.galaxia.common.abstracts.blocks.GalaxiaSignBlock;
import io.github.elizayami.galaxia.common.abstracts.blocks.StripableLogBlockTemplate;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fml.RegistryObject;

// TO DO? Make all wooden blocks flammable so they can take and spread fire
public class WoodenMaterial
{
	public final String name;

	public final RegistryObject<Block> log;
	public final RegistryObject<Block> bark;

	public final RegistryObject<Block> log_stripped;
	public final RegistryObject<Block> bark_stripped;

	public final RegistryObject<Block> planks;

	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	public final RegistryObject<Block> fence;
	public final RegistryObject<Block> gate;
	public final RegistryObject<Block> button;
	public final RegistryObject<Block> pressurePlate;
	public final RegistryObject<Block> trapdoor;
	public final RegistryObject<Block> door;

	public final RegistryObject<Block> craftingTable;
	public final RegistryObject<Block> ladder;
	public final RegistryObject<Block> sign;

	public final RegistryObject<Block> chest;
	public final RegistryObject<Block> barrel;
	public final RegistryObject<Block> shelf;

	public final ITag.INamedTag<Block> logBlockTag;
	public final ITag.INamedTag<Item> logItemTag;

	public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor)
	{
		this.name = name;

		logBlockTag = TagInit.makeModBlockTag(name + "_logs");
		logItemTag = TagInit.makeModItemTag(name + "_logs");

		AbstractBlock.Properties materialPlanks = AbstractBlock.Properties.create(Material.WOOD, planksColor)
				.hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
		AbstractBlock.Properties materialPlanksNotSolid = AbstractBlock.Properties.create(Material.WOOD, planksColor)
				.hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid();

		log_stripped = BlockInit.registerBlockWithDefaultItem(name + "_stripped_log",
				() -> new BarkBlockTemplate(materialPlanks));
		bark_stripped = BlockInit.registerBlockWithDefaultItem(name + "_stripped_bark",
				() -> new BarkBlockTemplate(materialPlanks));

		log = BlockInit.registerBlockWithDefaultItem(name + "_log",
				() -> new StripableLogBlockTemplate(woodColor, log_stripped.get()));
		bark = BlockInit.registerBlockWithDefaultItem(name + "_bark",
				() -> new StripableLogBlockTemplate(woodColor, bark_stripped.get()));

		planks = BlockInit.registerBlockWithDefaultItem(name + "_planks", () -> new Block(materialPlanks));
		stairs = BlockInit.registerBlockWithDefaultItem(name + "_stairs",
				() -> new StairsBlock(() -> planks.get().getDefaultState(), materialPlanks));
		slab = BlockInit.registerBlockWithDefaultItem(name + "_slab", () -> new SlabBlock(materialPlanks));
		fence = BlockInit.registerBlockWithDefaultItem(name + "_fence", () -> new FenceBlock(materialPlanks));
		gate = BlockInit.registerBlockWithDefaultItem(name + "_gate", () -> new FenceGateBlock(materialPlanks));
		button = BlockInit.registerBlockWithDefaultItem(name + "_button", () -> new WoodButtonBlock(materialPlanks));
		pressurePlate = BlockInit.registerBlockWithDefaultItem(name + "_pressure_plate",
				() -> new PressurePlateBlock(Sensitivity.EVERYTHING, materialPlanks));
		trapdoor = BlockInit.registerBlockWithDefaultItem(name + "_trapdoor",
				() -> new TrapDoorBlock(materialPlanksNotSolid));
		door = BlockInit.registerBlockWithDefaultItem(name + "_door", () -> new DoorBlock(materialPlanksNotSolid));

		craftingTable = registerBlockWithBurnItem(name + "_crafting_table",
				() -> new GalaxiaCraftingTableBlock(materialPlanks), 300);
		ladder = registerBlockWithBurnItem(name + "_ladder", () -> new LadderBlock(materialPlanksNotSolid), 300);
		chest = BlockInit.registerBlock(name + "_chest",
				() -> new ChestBlock(materialPlanksNotSolid, () -> TileEntityInit.CHEST.get())
				{
					public net.minecraft.tileentity.TileEntity createTileEntity(BlockState state,
							net.minecraft.world.IBlockReader world)
					{
						return TileEntityInit.CHEST.get().create();
					};
				});
		ItemInit.ITEMS.register(name + "_chest", () -> new BlockItem(chest.get(), new Item.Properties()
				.group(Galaxia.galaxiaGroup).setISTER(() -> ChestItemTileEntityRenderer::new))
		{
			public int getBurnTime(net.minecraft.item.ItemStack itemStack)
			{
				return 300;
			};
		});

		sign = registerBlockWithBurnItem(name + "_sign",
				() -> new GalaxiaSignBlock(AbstractBlock.Properties.create(Material.WOOD, planksColor)
						.hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid().doesNotBlockMovement()),
				200);
		barrel = registerBlockWithBurnItem(name + "_barrel", () -> new GalaxiaBarrelBlock(materialPlanksNotSolid), 300);
		shelf = registerBlockWithBurnItem(name + "_bookshelf", () -> new Block(materialPlanks)
		{
			@Override
			public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos)
			{
				return 1;
			};
		}, 300);
	}

	public boolean isTreeLog(Block block)
	{
		return block == log.get() || block == bark.get();
	}

	public boolean isTreeLog(BlockState state)
	{
		return isTreeLog(state.getBlock());
	}

	private static <T extends Block> RegistryObject<T> registerBlockWithBurnItem(String name,
			Supplier<? extends T> blockSupplier, int burnTime)
	{
		RegistryObject<T> block = BlockInit.BLOCKS.register(name, blockSupplier);
		ItemInit.ITEMS.register(name,
				() -> new BlockItem(block.get(), new Item.Properties().group(Galaxia.galaxiaGroup))
				{
					public int getBurnTime(net.minecraft.item.ItemStack itemStack)
					{
						return burnTime;
					};
				});
		return block;
	}
}
