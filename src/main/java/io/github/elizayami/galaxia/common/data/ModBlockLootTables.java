package io.github.elizayami.galaxia.common.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.materials.GemMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.SandstoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.FurnaceInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.RandomValueRange;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLootTables {
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null
						&& entry.getRegistryName().getNamespace().equals(Galaxia.MOD_ID))
				.collect(Collectors.toSet());
	}

	@Override
	protected void addTables() {
		// BLOCKS
		registerDropSelfLootTable(FurnaceInit.blockSF);
		registerDropSelfLootTable(FurnaceInit.blockDFF);
		registerDropSelfLootTable(BlockInit.SOAL_BLOCK.get());
		
		registerDropSelfLootTable(BlockInit.IMPACT_SAND.get());

		// MATERIALS
		registerDropSelfLootTable(BlockInit.GALAXIUM_STAR);
		
		registerLootTable(BlockInit.GALAXIUM_STAR2, (block) -> {
			return droppingRandomly(BlockInit.GALAXIUM.gem.get(), RandomValueRange.of(1, 2));
		});
		

		// ORES
		registerLootTable(BlockInit.SOAL_ORE.get(), (block) -> {
			return droppingItemWithFortune(block, ItemInit.SOAL.get());
		});

		// STONES
		
		// WOODEN_MATERIALS

		registerWoodenMaterialLootTables(BlockInit.SHADOWSPIKE);
		registerWoodenMaterialLootTables(BlockInit.GROUNDSTALK);
		registerWoodenMaterialLootTables(BlockInit.SEAWOOD);
		registerWoodenMaterialLootTables(BlockInit.SCORCHWOOD);
		registerWoodenMaterialLootTables(BlockInit.GHOSTWOOD);
		registerWoodenMaterialLootTables(BlockInit.GROVEWOOD);

		// STONE MATERIALS
		
		registerStoneMaterialLootTables(BlockInit.DRAGONSTONE);
		
		// SANDSTONE MATERIALS
		
		registerSandstoneMaterialLootTables(BlockInit.SOULSANDSTONE);
		registerSandstoneMaterialLootTables(BlockInit.IMPACTSANDSTONE);

		// METAL MATERIALS
		
		registerMetalMaterialLootTables(BlockInit.METEOR);
		registerMetalMaterialLootTables(BlockInit.COMETSTEEL);
		
		// Gem Materials
		registerGemMaterialLootTables(BlockInit.GALAXIUM);

	}

	private void registerWoodenMaterialLootTables(WoodenMaterial material) {
		registerDropSelfLootTable(material.log.get());
		registerDropSelfLootTable(material.bark.get());
		registerDropSelfLootTable(material.log_stripped.get());
		registerDropSelfLootTable(material.bark_stripped.get());
		registerDropSelfLootTable(material.planks.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.fence.get());
		registerDropSelfLootTable(material.gate.get());
		registerDropSelfLootTable(material.button.get());
		registerDropSelfLootTable(material.pressurePlate.get());
		registerDropSelfLootTable(material.trapdoor.get());
		registerLootTable(material.door.get(), BlockLootTables::registerDoor);
		registerDropSelfLootTable(material.craftingTable.get());
		registerDropSelfLootTable(material.ladder.get());
		registerDropSelfLootTable(material.chest.get());
		registerDropSelfLootTable(material.sign.get());
		registerDropSelfLootTable(material.barrel.get());
		registerLootTable(material.shelf.get(),
				droppingWithSilkTouchOrRandomly(material.shelf.get(), Items.BOOK, ConstantRange.of(3)));
	}

	private void registerStoneMaterialLootTables(StoneMaterial material) {
		registerDropSelfLootTable(material.stone.get());
		registerDropSelfLootTable(material.polished.get());
		registerDropSelfLootTable(material.tiles.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.wall.get());
		registerDropSelfLootTable(material.button.get());
		registerDropSelfLootTable(material.pressure_plate.get());
		registerDropSelfLootTable(material.bricks.get());
		registerDropSelfLootTable(material.brick_stairs.get());
		registerLootTable(material.brick_slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.brick_wall.get());
	}

	private void registerSandstoneMaterialLootTables(SandstoneMaterial material) {
		registerDropSelfLootTable(material.stone.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.chiseled.get());
		registerDropSelfLootTable(material.wall.get());
		registerDropSelfLootTable(material.smooth.get());
		registerDropSelfLootTable(material.smooth_stairs.get());
		registerLootTable(material.smooth_slab.get(), BlockLootTables::droppingSlab);
		registerDropSelfLootTable(material.smooth_wall.get());
	}

	private void registerMetalMaterialLootTables(MetalMaterial material) {
		if (material.hasOre) {
			registerDropSelfLootTable(material.ore.get());
		}
		registerDropSelfLootTable(material.block.get());
		registerDropSelfLootTable(material.tile.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
		registerLootTable(material.door.get(), BlockLootTables::registerDoor);
		registerDropSelfLootTable(material.trapdoor.get());
	}
	
	private void registerGemMaterialLootTables(GemMaterial material) 
	{
		if (material.hasOre) 
		{
			registerDropSelfLootTable(material.ore.get());
		}
		registerDropSelfLootTable(material.block.get());
		registerDropSelfLootTable(material.tile.get());
		registerDropSelfLootTable(material.stairs.get());
		registerLootTable(material.slab.get(), BlockLootTables::droppingSlab);
	}
}
