package io.github.elizayami.galaxia.common.data.client;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.NetherrackMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.SandstoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.GemMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.Dragonfire_Furnace;
import io.github.elizayami.galaxia.common.block.soul_furnace.Soul_Furnace;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.TileEntityInit;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStates extends BlockStateProvider
{
	public ModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, Galaxia.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
		simpleBlock(BlockInit.STATIC_ASH.get());
		makeBlockItemFromExistingModel(BlockInit.STATIC_ASH.get());

		simpleBlock(BlockInit.SOAL_BLOCK.get());
		makeBlockItemFromExistingModel(BlockInit.SOAL_BLOCK.get());

		simpleBlock(BlockInit.SOAL_ORE.get());
		makeBlockItemFromExistingModel(BlockInit.SOAL_ORE.get());

		tableBlock(TileEntityInit.blockTF, "tool_fuser");
		makeBlockItemFromExistingModel(TileEntityInit.blockTF);

		/*furnaceBlock(TileEntityInit.blockDFF, "dragonfire");
		makeBlockItemFromExistingModel(TileEntityInit.blockDFF);

		furnaceBlock(TileEntityInit.blockDFF, "soul");
		makeBlockItemFromExistingModel(TileEntityInit.blockSF);*/

		// BLOCKS

		// WOODEN MATERIALS

		registerWoodenMaterialBlockStates(BlockInit.SHADOWSPIKE);
		registerWoodenMaterialBlockStates(BlockInit.GROUNDSTALK);
		registerWoodenMaterialBlockStates(BlockInit.SEAWOOD);
		registerWoodenMaterialBlockStates(BlockInit.SCORCHWOOD);
		registerWoodenMaterialBlockStates(BlockInit.GHOSTWOOD);
		registerWoodenMaterialBlockStates(BlockInit.GROVEWOOD);

		// STONE MATERIALS

		registerStoneMaterialBlockStates(BlockInit.DRAGONSTONE);

		// NETHERRACK MATERIALS

		registerNetherrackMaterialBlockStates(BlockInit.STATIRACK);
		registerNetherrackMaterialBlockStates(BlockInit.WITHERRACK);

		// SANDSTONE MATERIALS

		registerSandstoneMaterialBlockStates(BlockInit.SOULSANDSTONE);
		registerSandstoneMaterialBlockStates(BlockInit.IMPACTSANDSTONE);

		// METAL MATERIALS
		registerMetalMaterialBlockStates(BlockInit.SILVER);
		registerMetalMaterialBlockStates(BlockInit.METEOR);
		registerMetalMaterialBlockStates(BlockInit.COMETSTEEL);

		// GEM MATERIALS
		registerGemMaterialBlockStates(BlockInit.GALAXIUM);
		registerGemMaterialBlockStates(BlockInit.BOLTRINE);
	}

	private void registerWoodenMaterialBlockStates(WoodenMaterial material)
	{
		ResourceLocation planksTexture = modLoc("block/" + material.name + "_planks");

		simpleBlock(material.planks.get());
		makeBlockItemFromExistingModel(material.planks.get());

		simpleBlock(material.panel.get());
		makeBlockItemFromExistingModel(material.panel.get());

		logBlock((RotatedPillarBlock) material.log.get());
		makeBlockItemFromExistingModel(material.log.get());

		logBlock((RotatedPillarBlock) material.log_stripped.get());
		makeBlockItemFromExistingModel(material.log_stripped.get());

		String log = "block/" + material.name + "_log";
		String stripped = "block/" + material.name + "_log_stripped";

		barkBlock(material.bark.get(), material.name, false);
		makeBlockItemFromExistingModel(material.bark.get(), log);

		barkBlock(material.bark_stripped.get(), material.name, false);
		makeBlockItemFromExistingModel(material.bark_stripped.get(), stripped);

		stairsBlock((StairsBlock) material.stairs.get(), planksTexture);
		makeBlockItemFromExistingModel(material.stairs.get());

		slabBlock((SlabBlock) material.slab.get(), material.planks.get().getRegistryName(), planksTexture);
		makeBlockItemFromExistingModel(material.slab.get());

		// BlockItem handled in item model provider
		fenceBlock((FenceBlock) material.fence.get(), planksTexture);

		fenceGateBlock((FenceGateBlock) material.gate.get(), planksTexture);
		makeBlockItemFromExistingModel(material.gate.get());

		// BlockItem handled in item model provider
		doorBlock((DoorBlock) material.door.get(), modLoc("block/" + material.name + "_door_bottom"),
				modLoc("block/" + material.name + "_door_top"));

		trapdoorBlock((TrapDoorBlock) material.trapdoor.get(), modLoc("block/" + material.name + "_trapdoor"), true);
		makeBlockItemFromExistingModel(material.trapdoor.get(), "block/" + material.name + "_trapdoor_bottom");

		// BlockItem handled in item model provider
		buttonBlock((WoodButtonBlock) material.button.get(), material.name, planksTexture);

		pressurePlateBlock((PressurePlateBlock) material.pressurePlate.get(), material.name, planksTexture);
		makeBlockItemFromExistingModel(material.pressurePlate.get());

		craftingTableBlock((CraftingTableBlock) material.craftingTable.get(), material.name);
		makeBlockItemFromExistingModel(material.craftingTable.get());
		// BlockItem handled in item model provider
		ladderBlock((LadderBlock) material.ladder.get(), material.name);
		// BlockItem handled in item model provider
		chestBlock(material.chest.get(), material.name);
		// BlockItem handled in item model provider
		signBlock(material.sign.get(), material.name);
		barrelBlock(material.barrel.get(), material.name);
		makeBlockItemFromExistingModel(material.barrel.get());
		shelfBlock(material.shelf.get(), material.name);
		makeBlockItemFromExistingModel(material.shelf.get());
	}

	private void registerStoneMaterialBlockStates(StoneMaterial material)
	{
		ResourceLocation stoneTexture = modLoc("block/" + material.name);

		simpleBlock(material.stone.get());
		makeBlockItemFromExistingModel(material.stone.get());

		simpleBlock(material.polished.get());
		makeBlockItemFromExistingModel(material.polished.get());

		simpleBlock(material.tiles.get());
		makeBlockItemFromExistingModel(material.tiles.get());

		stairsBlock((StairsBlock) material.stairs.get(), stoneTexture);
		makeBlockItemFromExistingModel(material.stairs.get());

		slabBlock((SlabBlock) material.slab.get(), material.stone.get().getRegistryName(), stoneTexture);
		makeBlockItemFromExistingModel(material.slab.get());

		// BlockItem handled in item model provider
		wallBlock((WallBlock) material.wall.get(), stoneTexture);

		simpleBlock(material.bricks.get());
		makeBlockItemFromExistingModel(material.bricks.get());

		stairsBlock((StairsBlock) material.brick_stairs.get(), modLoc("block/" + material.name + "_bricks"));
		makeBlockItemFromExistingModel(material.brick_stairs.get());

		slabBlock((SlabBlock) material.brick_slab.get(), material.bricks.get().getRegistryName(),
				modLoc("block/" + material.name + "_bricks"));
		makeBlockItemFromExistingModel(material.brick_slab.get());

		// BlockItem handled in item model provider
		wallBlock((WallBlock) material.brick_wall.get(), modLoc("block/" + material.name + "_bricks"));

		// BlockItem handled in item model provider
		buttonBlock((StoneButtonBlock) material.button.get(), material.name, stoneTexture);

		pressurePlateBlock((PressurePlateBlock) material.pressure_plate.get(), material.name, stoneTexture);
		makeBlockItemFromExistingModel(material.pressure_plate.get());
	}

	private void registerNetherrackMaterialBlockStates(NetherrackMaterial material)
	{
		ResourceLocation brickTexture = modLoc("block/" + material.name + "_bricks");

		simpleBlock(material.stone.get());
		makeBlockItemFromExistingModel(material.stone.get());

		simpleBlock(material.bricks.get());
		makeBlockItemFromExistingModel(material.bricks.get());

		simpleBlock(material.cracked_bricks.get());
		makeBlockItemFromExistingModel(material.cracked_bricks.get());

		simpleBlock(material.chiseled.get());
		makeBlockItemFromExistingModel(material.chiseled.get());

		stairsBlock((StairsBlock) material.stairs.get(), brickTexture);
		makeBlockItemFromExistingModel(material.stairs.get());

		slabBlock((SlabBlock) material.slab.get(), material.stone.get().getRegistryName(), brickTexture);
		makeBlockItemFromExistingModel(material.slab.get());

		// BlockItem handled in item model provider
		fenceBlock((FenceBlock) material.fence.get(), brickTexture);

		fenceGateBlock((FenceGateBlock) material.gate.get(), brickTexture);
		makeBlockItemFromExistingModel(material.gate.get());

		simpleBlock(material.gold.get());
		makeBlockItemFromExistingModel(material.gold.get());

		simpleBlock(material.quartz.get());
		makeBlockItemFromExistingModel(material.quartz.get());

		if (material.silver != null)
		{
			simpleBlock(material.silver.get());
			makeBlockItemFromExistingModel(material.silver.get());
		}
	}

	private void registerSandstoneMaterialBlockStates(SandstoneMaterial material)
	{
		String stoneTexture = "block/" + material.name + "_sandstone";
		
		if (material.sand != null)
		{
			simpleBlock(material.sand.get());
			makeBlockItemFromExistingModel(material.sand.get());
		}
		
		String name = material.name + "_sandstone";

		sandstoneBlock(material.stone.get(), material.name, "");
		makeBlockItemFromExistingModel(material.stone.get(), name);

		sandstoneBlock(material.chiseled.get(), material.name, "_chiseled");
		makeBlockItemFromExistingModel(material.chiseled.get(), name);

		sandstoneBlock(material.smooth.get(), material.name, "_smooth");
		makeBlockItemFromExistingModel(material.smooth.get(), name);

		stairsBlock((StairsBlock) material.stairs.get(), modLoc(stoneTexture), modLoc(stoneTexture + "_bottom"),
				modLoc(stoneTexture + "_top"));
		makeBlockItemFromExistingModel(material.stairs.get());

		slabBlock((SlabBlock) material.slab.get(), material.stone.get().getRegistryName(), modLoc(stoneTexture),
				modLoc(stoneTexture + "_bottom"), modLoc(stoneTexture + "_top"));
		makeBlockItemFromExistingModel(material.slab.get());

		wallBlock((WallBlock) material.wall.get(), modLoc(stoneTexture));

		stairsBlock((StairsBlock) material.smooth_stairs.get(), modLoc(stoneTexture + "_smooth"),
				modLoc(stoneTexture + "_bottom"), modLoc(stoneTexture + "_top"));
		makeBlockItemFromExistingModel(material.smooth_stairs.get());

		slabBlock((SlabBlock) material.smooth_slab.get(), material.smooth.get().getRegistryName(),
				modLoc(stoneTexture + "_smooth"), modLoc(stoneTexture + "_bottom"),
				modLoc(stoneTexture + "_top"));
		makeBlockItemFromExistingModel(material.smooth_slab.get());

		wallBlock((WallBlock) material.smooth_wall.get(), modLoc(stoneTexture + "_smooth"));
	}

	private void registerMetalMaterialBlockStates(MetalMaterial material)
	{
		if (material.hasOre)
		{
			simpleBlock(material.ore.get());
			makeBlockItemFromExistingModel(material.ore.get());
		}

		simpleBlock(material.block.get());
		makeBlockItemFromExistingModel(material.block.get());

		simpleBlock(material.tile.get());
		makeBlockItemFromExistingModel(material.tile.get());

		stairsBlock((StairsBlock) material.stairs.get(), modLoc("block/" + material.name + "_tile"));
		makeBlockItemFromExistingModel(material.stairs.get());

		slabBlock((SlabBlock) material.slab.get(), material.tile.get().getRegistryName(),
				modLoc("block/" + material.name + "_tile"));
		makeBlockItemFromExistingModel(material.slab.get());

		// BlockItem handled in item model provider
		doorBlock((DoorBlock) material.door.get(), modLoc("block/" + material.name + "_door_bottom"),
				modLoc("block/" + material.name + "_door_top"));

		trapdoorBlock((TrapDoorBlock) material.trapdoor.get(), modLoc("block/" + material.name + "_trapdoor"), true);
		makeBlockItemFromExistingModel(material.trapdoor.get(), "block/" + material.name + "_trapdoor_bottom");

	}

	private void registerGemMaterialBlockStates(GemMaterial material)
	{
		if (material.hasOre)
		{
			simpleBlock(material.ore.get());
			makeBlockItemFromExistingModel(material.ore.get());
		}

		simpleBlock(material.block.get());
		makeBlockItemFromExistingModel(material.block.get());

		simpleBlock(material.tile.get());
		makeBlockItemFromExistingModel(material.tile.get());

		stairsBlock((StairsBlock) material.stairs.get(), modLoc("block/" + material.name + "_tile"));
		makeBlockItemFromExistingModel(material.stairs.get());

		slabBlock((SlabBlock) material.slab.get(), material.tile.get().getRegistryName(),
				modLoc("block/" + material.name + "_tile"));
		makeBlockItemFromExistingModel(material.slab.get());

	}

	private void makeBlockItemFromExistingModel(Block block)
	{
		final ModelFile model = models().getExistingFile(block.getRegistryName());
		simpleBlockItem(block, model);
	}

	private void makeBlockItemFromExistingModel(Block block, String name)
	{
		final ModelFile model = models().getExistingFile(modLoc(name));
		simpleBlockItem(block, model);
	}

	private void furnaceBlock(Block block, String material)
	{
		ModelFile furnace = models().withExistingParent(material + "_furnace", mcLoc("block/orientable_with_bottom"))
				.texture("top", modLoc("block/" + material + "_furnace_top"))
				.texture("front", modLoc("block/" + material + "_furnace_front"))
				.texture("side", modLoc("block/" + material + "_furnace_side"))
				.texture("bottom", modLoc("block/" + material + "_furnace_top"));

		ModelFile furnaceOn = models().withExistingParent(material + "_furnace_on", modLoc("block/furnace_glow"))
				.texture("top", modLoc("block/" + material + "_furnace_top"))
				.texture("front", modLoc("block/" + material + "_furnace_front_on"))
				.texture("side", modLoc("block/" + material + "_furnace_side"))
				.texture("glow", modLoc("block/" + material + "_furnace_glow"));

		getVariantBuilder(block).forAllStates(state ->
		{
			boolean isLit = material == "soul" ? state.get(Soul_Furnace.IS_BURNING) : state.get(Dragonfire_Furnace.IS_BURNING);

			Direction dir = state.get(FurnaceBlock.FACING);
			int x = 0;
			int y = 0;
			switch (dir)
			{
			case EAST:
				y = 90;
				break;
			case NORTH:
				break;
			case SOUTH:
				y = 180;
				break;
			case WEST:
				y = 270;
				break;
			default:
				break;
			}

			return ConfiguredModel.builder().modelFile(isLit ? furnaceOn : furnace).rotationX(x).rotationY(y).build();
		});
	}

	private void barkBlock(Block block, String material, Boolean stripped)
	{
		ResourceLocation Side = modLoc("block/" + material + "_log" + (stripped ? "_stripped" : ""));

		ModelFile model = models().cube(material + "_bark", Side, Side, Side, Side, Side, Side).texture("particle",
				Side);
		simpleBlock(block, model);
	}

	private void buttonBlock(AbstractButtonBlock block, String material, ResourceLocation texture)
	{
		ModelFile button = models().singleTexture(material + "_button", mcLoc("block/button"), texture);
		ModelFile buttonPressed = models().singleTexture(material + "_button_pressed", mcLoc("block/button_pressed"),
				texture);
		int angleOffset = 180;
		getVariantBuilder(block).forAllStates(state ->
		{
			boolean powered = state.get(WoodButtonBlock.POWERED);

			return ConfiguredModel.builder().modelFile(powered == true ? buttonPressed : button)
					.rotationX(state.get(BlockStateProperties.FACE).ordinal() * 90)
					.rotationY((((int) state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle()
							+ angleOffset) + (state.get(BlockStateProperties.FACE) == AttachFace.CEILING ? 180 : 0))
							% 360)
					.build();
		});
	}

	private void pressurePlateBlock(PressurePlateBlock block, String material, ResourceLocation texture)
	{
		ModelFile plate = models().singleTexture(material + "_pressure_plate", mcLoc("block/pressure_plate_up"),
				texture);
		ModelFile plateDown = models().singleTexture(material + "_pressure_plate_down",
				mcLoc("block/pressure_plate_down"), texture);
		getVariantBuilder(block).forAllStates(state ->
		{
			boolean powered = state.get(PressurePlateBlock.POWERED);

			return ConfiguredModel.builder().modelFile(powered == true ? plateDown : plate).build();
		});
	}

	private void composterBlock(ComposterBlock block, String material)
	{
		ModelFile composter = models().withExistingParent(material + "_composter", mcLoc("composter"))
				.texture("particle", modLoc("block/" + material + "_composter_side"))
				.texture("top", modLoc("block/" + material + "_composter_top"))
				.texture("bottom", modLoc("block/" + material + "_composter_bottom"))
				.texture("side", modLoc("block/" + material + "_composter_side"))
				.texture("inside", modLoc("block/" + material + "_composter_bottom"));
		ModelFile composterReady = models()
				.withExistingParent(material + "_composter_contents_ready", mcLoc("composter_contents_ready"))
				.texture("particle", mcLoc("block/composter_compost"))
				.texture("inside", mcLoc("block/composter_ready"));
		ModelFile[] contents = new ModelFile[7];
		for (int i = 0; i < contents.length; i++)
			contents[i] = models()
					.withExistingParent(material + "_composter_contents" + (i + 1),
							mcLoc("composter_contents" + (i + 1)))
					.texture("particle", mcLoc("block/composter_compost"))
					.texture("inside", mcLoc("block/composter_compost"));
		getMultipartBuilder(block).part().modelFile(composter).addModel().end();
		for (int i = 0; i < contents.length; i++)
			getMultipartBuilder(block).part().modelFile(contents[i]).addModel().condition(ComposterBlock.LEVEL, i + 1)
					.end();
		getMultipartBuilder(block).part().modelFile(composterReady).addModel().condition(ComposterBlock.LEVEL, 8).end();
	}

	private void sandstoneBlock(Block block, String material, String addon)
	{
		ResourceLocation Side = modLoc("block/" + material + "_sandstone" + addon);

		ModelFile model = models().cube(material + addon, modLoc("block/" + material + "_sandstone_bottom"),
				modLoc("block/" + material + "_sandstone_top"), Side, Side, Side, Side).texture("particle", Side);
		simpleBlock(block, model);
	}

	private void craftingTableBlock(CraftingTableBlock block, String material)
	{
		ModelFile model = models()
				.cube(material + "_crafting_table", modLoc("block/" + material + "_planks"),
						modLoc("block/" + material + "_crafting_table_top"),
						modLoc("block/" + material + "_crafting_table_front"),
						modLoc("block/" + material + "_crafting_table_side"),
						modLoc("block/" + material + "_crafting_table_side"),
						modLoc("block/" + material + "_crafting_table_front"))
				.texture("particle", modLoc("block/" + material + "_crafting_table_top"));
		simpleBlock(block, model);
	}

	private void tableBlock(Block block, String name)
	{
		ModelFile model = models()
				.cube(name, modLoc("block/" + name + "_bottom"), modLoc("block/" + name + "_top"),
						modLoc("block/" + name + "_front"), modLoc("block/" + name + "_side"),
						modLoc("block/" + name + "_side"), modLoc("block/" + name + "_front"))
				.texture("particle", modLoc("block/" + name + "_top"));
		simpleBlock(block, model);
	}

	private void ladderBlock(LadderBlock block, String material)
	{
		ModelFile ladder = models().withExistingParent(material + "_ladder", mcLoc("block/ladder")).texture("texture",
				modLoc("block/" + material + "_ladder"));
		horizontalBlock(block, ladder);
	}

	private void chestBlock(Block block, String material)
	{
		ModelFile texture = models().getBuilder(material + "_chest").texture("particle",
				modLoc("block/" + material + "_planks"));
		simpleBlock(block, texture);
	}

	private void signBlock(Block block, String material)
	{
		ModelFile texture = models().getBuilder(material + "_sign").texture("particle",
				modLoc("block/" + material + "_planks"));
		simpleBlock(block, texture);
	}

	private void barrelBlock(Block block, String material)
	{
		ModelFile open = models().withExistingParent(material + "_barrel_open", mcLoc("block/cube_bottom_top"))
				.texture("top", modLoc("block/" + material + "_barrel_top_open"))
				.texture("bottom", modLoc("block/" + material + "_barrel_bottom"))
				.texture("side", modLoc("block/" + material + "_barrel_side"));
		ModelFile closed = models().withExistingParent(material + "_barrel", mcLoc("block/cube_bottom_top"))
				.texture("top", modLoc("block/" + material + "_barrel_top"))
				.texture("bottom", modLoc("block/" + material + "_barrel_bottom"))
				.texture("side", modLoc("block/" + material + "_barrel_side"));
		getVariantBuilder(block).forAllStates(state ->
		{
			boolean opened = state.get(BarrelBlock.PROPERTY_OPEN);
			Direction dir = state.get(BarrelBlock.PROPERTY_FACING);
			int x = 0;
			int y = 0;
			switch (dir)
			{
			case DOWN:
				x = 180;
				break;
			case EAST:
				x = 90;
				y = 90;
				break;
			case NORTH:
				x = 90;
				break;
			case SOUTH:
				x = 90;
				y = 180;
				break;
			case UP:
				break;
			case WEST:
				x = 90;
				y = 270;
				break;
			}

			return ConfiguredModel.builder().modelFile(opened ? open : closed).rotationX(x).rotationY(y).build();
		});
	}

	private void shelfBlock(Block block, String material)
	{
		ModelFile texture = models().cubeColumn(material + "_bookshelf", modLoc("block/" + material + "_bookshelf"),
				modLoc("block/" + material + "_planks"));
		simpleBlock(block, texture);
	}

	private void chainBlock(Block chain_block)
	{
		ModelFile chain = models().withExistingParent(chain_block.getRegistryName().getPath(), mcLoc("block/chain"))
				.texture("particle", modLoc("block/" + chain_block.getRegistryName().getPath()))
				.texture("all", modLoc("block/" + chain_block.getRegistryName().getPath()));
		axisBlock((RotatedPillarBlock) chain_block, chain, chain);
	}

	private void barsBlock(Block barsBlock)
	{
		ModelFile post = models()
				.withExistingParent(barsBlock.getRegistryName().getPath() + "_post", modLoc("metal_bars_post"))
				.texture("top", modLoc("block/" + barsBlock.getRegistryName().getPath() + "_top"));
		ModelFile side = models()
				.withExistingParent(barsBlock.getRegistryName().getPath() + "_side", modLoc("metal_bars_side"))
				.texture("top", modLoc("block/" + barsBlock.getRegistryName().getPath() + "_top"))
				.texture("side", modLoc("block/" + barsBlock.getRegistryName().getPath()));

		MultiPartBlockStateBuilder builder = getMultipartBuilder(barsBlock).part().modelFile(post).addModel()
				.condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.NORTH), false)
				.condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.SOUTH), false)
				.condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.EAST), false)
				.condition(SixWayBlock.FACING_TO_PROPERTY_MAP.get(Direction.WEST), false).end();

		fourWayMultipart(builder, side);
	}
}
