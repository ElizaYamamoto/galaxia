package io.github.elizayami.galaxia.core.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.blocks.RotatingBlock;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.NetherrackMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.GemMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.SandstoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.VanillaMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.common.block.galaxium_star.Galaxium_Star;
import io.github.elizayami.galaxia.common.block.galaxium_star.Galaxium_Star2;
import io.github.elizayami.galaxia.core.enums.ArmorMaterials;
import io.github.elizayami.galaxia.core.enums.ToolMaterials;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class BlockInit
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Galaxia.MOD_ID);

	@ObjectHolder("galaxia:galaxium_star")
	public static Block GALAXIUM_STAR;

	@ObjectHolder("galaxia:galaxium_star2")
	public static Block GALAXIUM_STAR2;

	@ObjectHolder("galaxia:galaxium_star_tile")
	public static TileEntityType<RotatingBlock> galaxium_star_tile;

	@ObjectHolder("galaxia:galaxium_star_tile2")
	public static TileEntityType<RotatingBlock> galaxium_star_tile2;

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void onBlockRegistry(final RegistryEvent.Register<Block> Star)
		{
			Star.getRegistry().register(new Galaxium_Star("galaxium_star", Galaxium_Star.Shape,
					() -> new RotatingBlock(galaxium_star_tile)));

			Star.getRegistry().register(new Galaxium_Star("galaxium_star2", Galaxium_Star2.Shape,
					() -> new RotatingBlock(galaxium_star_tile2)));
		}

		@SubscribeEvent
		public static void onTileRegistry(final RegistryEvent.Register<TileEntityType<?>> Star)
		{
			Star.getRegistry()
					.register(TileEntityType.Builder.create(() -> new RotatingBlock(galaxium_star_tile), GALAXIUM_STAR)
							.build(null).setRegistryName("galaxium_star_tile"));

			Star.getRegistry().register(
					TileEntityType.Builder.create(() -> new RotatingBlock(galaxium_star_tile2), GALAXIUM_STAR2)
							.build(null).setRegistryName("galaxium_star_tile2"));
		}
	}

	public static final RegistryObject<FallingBlock> IMPACT_SAND = BLOCKS.register("impact_sand",
			() -> new FallingBlock(AbstractBlock.Properties.from(Blocks.SAND)));

	// ores

	public static final RegistryObject<Block> SOAL_ORE = BLOCKS.register("soal_ore",
			() -> new Block(AbstractBlock.Properties.from(Blocks.SOUL_SAND)));

	// storage

	public static final RegistryObject<Block> SOAL_BLOCK = BLOCKS.register("soal_block",
			() -> new Block(AbstractBlock.Properties.from(Blocks.COAL_BLOCK).setLightLevel(s -> 15)
					.setNeedsPostProcessing((bs, br, bp) -> true).setEmmisiveRendering((bs, br, bp) -> true)));

	// Materials
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
	
	private static List<NetherrackMaterial> netherrackMaterials;

	public static List<NetherrackMaterial> getNetherrackMaterials()
	{
		return ImmutableList.copyOf(netherrackMaterials);
	}

	private static List<SandstoneMaterial> sandstoneMaterials;

	public static List<SandstoneMaterial> getSandstoneMaterials()
	{
		return ImmutableList.copyOf(sandstoneMaterials);
	}

	private static List<MetalMaterial> metalMaterials;

	public static List<MetalMaterial> getMetalMaterials()
	{
		return ImmutableList.copyOf(metalMaterials);
	}

	private static List<GemMaterial> gemMaterials;

	public static List<GemMaterial> getGemMaterial()
	{
		return ImmutableList.copyOf(gemMaterials);
	}

	private static List<VanillaMaterial> vanillaMaterials;

	public static List<VanillaMaterial> getVanillaMaterial()
	{
		return ImmutableList.copyOf(vanillaMaterials);
	}

	// VANILLA

	public static final VanillaMaterial WOOD = createVanillaMaterial("wooden", ItemTier.WOOD);
	public static final VanillaMaterial STONE = createVanillaMaterial("stone", ItemTier.STONE);
	public static final VanillaMaterial IRON = createVanillaMaterial("iron", ItemTier.IRON);
	public static final VanillaMaterial GOLD = createVanillaMaterial("gold", ItemTier.GOLD);
	public static final VanillaMaterial DIAMOND = createVanillaMaterial("diamond", ItemTier.DIAMOND);
	public static final VanillaMaterial NETHERITE = createVanillaMaterial("netherite", ItemTier.NETHERITE);

	// WOOD

	public static final WoodenMaterial SHADOWSPIKE = createWoodenMaterial("shadowspike", MaterialColor.OBSIDIAN,
			MaterialColor.GRAY_TERRACOTTA);
	public static final WoodenMaterial GROUNDSTALK = createWoodenMaterial("groundstalk", MaterialColor.YELLOW,
			MaterialColor.YELLOW_TERRACOTTA);
	public static final WoodenMaterial SEAWOOD = createWoodenMaterial("seawood", MaterialColor.LIGHT_BLUE,
			MaterialColor.CYAN);
	public static final WoodenMaterial SCORCHWOOD = createWoodenMaterial("scorchwood", MaterialColor.ADOBE,
			MaterialColor.OBSIDIAN);
	public static final WoodenMaterial GHOSTWOOD = createWoodenMaterial("ghostwood", MaterialColor.STONE,
			MaterialColor.SNOW);
	public static final WoodenMaterial GROVEWOOD = createWoodenMaterial("grovewood", MaterialColor.GRASS,
			MaterialColor.GREEN);

	// STONE

	public static final StoneMaterial DRAGONSTONE = createStoneMaterial("dragonstone", MaterialColor.BLACK_TERRACOTTA);

	// NETHERRACK
	
	public static final NetherrackMaterial GALVIROCK = createNetherrackMaterial("galvirock", MaterialColor.YELLOW_TERRACOTTA, new Item.Properties().group(Galaxia.galaxiaGroup));
	public static final NetherrackMaterial WITHERRACK = createNetherrackMaterial("witherrack", MaterialColor.BLACK, new Item.Properties().group(Galaxia.galaxiaGroup));
	
	// SANDSTONE

	public static final SandstoneMaterial SOULSANDSTONE = createSandstoneMaterial("soul_sandstone",
			MaterialColor.BROWN);

	public static final SandstoneMaterial IMPACTSANDSTONE = createSandstoneMaterial("impact_sandstone",
			MaterialColor.GRAY);

	// METAL

	public static final MetalMaterial METEOR = createMetalMaterial("meteor", true,
			AbstractBlock.Properties.create(Material.IRON, MaterialColor.GRAY).setRequiresTool()
					.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL),
			new Item.Properties().group(Galaxia.galaxiaGroup), ToolMaterials.METEOR, ArmorMaterials.METEOR);

	public static final MetalMaterial COMETSTEEL = createMetalMaterial("cometsteel", true,
			AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE_TERRACOTTA).setRequiresTool()
					.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL),
			new Item.Properties().group(Galaxia.galaxiaGroup), ToolMaterials.COMETSTEEL, ArmorMaterials.COMETSTEEL);

	// GEM
	
	public static final GemMaterial BOLTRINE = createGemMaterial("boltrine", true,
			AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).setRequiresTool()
					.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.STONE),
			new Item.Properties().group(Galaxia.galaxiaGroup), ToolMaterials.BOLTRINE, ArmorMaterials.BOLTRINE);

	public static final GemMaterial GALAXIUM = createGemMaterial("galaxium", true,
			AbstractBlock.Properties.create(Material.IRON, MaterialColor.PURPLE).setRequiresTool()
					.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.GILDED_BLACKSTONE),
			new Item.Properties().group(Galaxia.galaxiaGroup), ToolMaterials.GALAXIUM, ArmorMaterials.GALAXIUM);

	// helpers
	public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<? extends T> blockSupplier)
	{
		return BLOCKS.register(name, blockSupplier);
	}

	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name,
			Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		ItemInit.ITEMS.register(name,
				() -> new BlockItem(block.get(), new Item.Properties().group(Galaxia.galaxiaGroup)));
		return block;
	}

	public static <T extends Block> RegistryObject<T> registerBlockWithNoItem(String name,
			Supplier<? extends T> blockSupplier)
	{
		RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
		return block;
	}

	public static WoodenMaterial createWoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor)
	{
		if (woodenMaterials == null)
			woodenMaterials = new ArrayList<>();

		WoodenMaterial material = new WoodenMaterial(name, woodColor, planksColor);
		woodenMaterials.add(material);
		return material;
	}

	public static StoneMaterial createStoneMaterial(String name, MaterialColor color)
	{
		if (stoneMaterials == null)
			stoneMaterials = new ArrayList<>();

		StoneMaterial material = new StoneMaterial(name, color);
		stoneMaterials.add(material);
		return material;
	}

	public static NetherrackMaterial createNetherrackMaterial(String name, MaterialColor color, Item.Properties itemSettings)
	{
		if (netherrackMaterials == null)
			netherrackMaterials = new ArrayList<>();

		NetherrackMaterial material = new NetherrackMaterial(name, color, itemSettings);
		netherrackMaterials.add(material);
		return material;
	}

	public static SandstoneMaterial createSandstoneMaterial(String name, MaterialColor color)
	{
		if (sandstoneMaterials == null)
			sandstoneMaterials = new ArrayList<>();

		SandstoneMaterial material = new SandstoneMaterial(name, color);
		sandstoneMaterials.add(material);
		return material;
	}

	public static MetalMaterial createMetalMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings,
			Item.Properties itemSettings, IItemTier itemTier, IArmorMaterial armor)
	{
		if (metalMaterials == null)
			metalMaterials = new ArrayList<>();

		MetalMaterial material = new MetalMaterial(name, hasOre, blockSettings, itemSettings, itemTier, armor);
		metalMaterials.add(material);
		return material;
	}

	public static GemMaterial createGemMaterial(String name, boolean hasOre, AbstractBlock.Properties blockSettings,
			Item.Properties itemSettings, IItemTier itemTier, IArmorMaterial armor)
	{
		if (gemMaterials == null)
			gemMaterials = new ArrayList<>();

		GemMaterial material = new GemMaterial(name, hasOre, blockSettings, itemSettings, itemTier, armor);
		gemMaterials.add(material);
		return material;
	}

	public static VanillaMaterial createVanillaMaterial(String name, IItemTier itemTier)
	{
		if (vanillaMaterials == null)
			vanillaMaterials = new ArrayList<>();

		VanillaMaterial material = new VanillaMaterial(name, itemTier);
		vanillaMaterials.add(material);
		return material;
	}
}
