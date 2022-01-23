package io.github.elizayami.galaxia.common.data;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.materials.GemMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.SandstoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.TagInit;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.versions.forge.ForgeVersion;

public class ModBlockTagsProvider extends BlockTagsProvider
{
	public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) 
	{
		super(generatorIn, Galaxia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerTags() 
	{
		//Mod Tags

		getOrCreateBuilder(Tags.Blocks.END_STONES).add(Blocks.END_STONE);
		
		// Misc Forge tags
		getOrCreateBuilder(Tags.Blocks.ORES).add(BlockInit.SOAL_ORE.get());
		
		getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).add(BlockInit.SOAL_BLOCK.get());

		// Misc Minecraft tags
		
		// WOODEN MATERIALS
		
		registerWoodenMaterialTags(BlockInit.SHADOWSPIKE);
		registerWoodenMaterialTags(BlockInit.GROUNDSTALK);
		registerWoodenMaterialTags(BlockInit.SEAWOOD);
		registerWoodenMaterialTags(BlockInit.SCORCHWOOD);
		registerWoodenMaterialTags(BlockInit.GHOSTWOOD);
		registerWoodenMaterialTags(BlockInit.GROVEWOOD);
		
		// STONE MATERIALS
		
		registerStoneMaterialTags(BlockInit.DRAGONSTONE);
		
		// SANDSTONE MATERIALS
		
		registerSandstoneMaterialTags(BlockInit.SOULSANDSTONE);

		// METAL MATERIALS
		
		registerMetalMaterialTags(BlockInit.METEOR);
		registerMetalMaterialTags(BlockInit.COMETSTEEL);
	}
	
	private void registerWoodenMaterialTags(WoodenMaterial material)
	{
		getOrCreateBuilder(material.logBlockTag).add(material.log.get(), material.bark.get(), material.log_stripped.get(), material.bark_stripped.get());
		
		getOrCreateBuilder(BlockTags.PLANKS).add(material.planks.get());
		
		getOrCreateBuilder(BlockTags.LOGS).add(material.log.get(), material.bark.get(), material.log_stripped.get(), material.bark_stripped.get());
		
		getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(material.log.get(), material.bark.get(), material.log_stripped.get(), material.bark_stripped.get());
		
		getOrCreateBuilder(BlockTags.BUTTONS).add(material.button.get());
		getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(material.button.get());
		
		getOrCreateBuilder(BlockTags.PRESSURE_PLATES).add(material.pressurePlate.get());
		getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(material.pressurePlate.get());
		
		getOrCreateBuilder(BlockTags.DOORS).add(material.door.get());
		getOrCreateBuilder(BlockTags.WOODEN_DOORS).add(material.door.get());
		
		getOrCreateBuilder(BlockTags.FENCES).add(material.fence.get());
		getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(material.fence.get());
		
		getOrCreateBuilder(BlockTags.SLABS).add(material.slab.get());
		getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(material.slab.get());
		
		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs.get());
		getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(material.stairs.get());

		getOrCreateBuilder(BlockTags.TRAPDOORS).add(material.trapdoor.get());
		getOrCreateBuilder(BlockTags.WOODEN_TRAPDOORS).add(material.trapdoor.get());
		
		getOrCreateBuilder(BlockTags.SIGNS).add(material.sign.get());
		
		getOrCreateBuilder(BlockTags.CLIMBABLE).add(material.ladder.get());
		
		getOrCreateBuilder(BlockTags.GUARDED_BY_PIGLINS).add(material.chest.get());	
		getOrCreateBuilder(BlockTags.GUARDED_BY_PIGLINS).add(material.barrel.get());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Blocks.FENCES).add(material.fence.get());
		getOrCreateBuilder(Tags.Blocks.FENCES_WOODEN).add(material.fence.get());

		getOrCreateBuilder(Tags.Blocks.FENCE_GATES).add(material.gate.get());
		getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(material.gate.get());
		
		getOrCreateBuilder(Tags.Blocks.CHESTS).add(material.chest.get());
		getOrCreateBuilder(Tags.Blocks.CHESTS_WOODEN).add(material.chest.get());
		
		getOrCreateBuilder(Tags.Blocks.CHESTS_WOODEN).add(material.chest.get());
		
		getOrCreateBuilder(BlockTags.createOptional(frl("workbench"))).add(material.craftingTable.get());
		
		// Used by the Metal Barrels mod
		getOrCreateBuilder(TagInit.BLOCK_BARRELS).add(material.barrel.get());
	}
	
	private ResourceLocation frl(String tag) 
	{
		return new ResourceLocation(ForgeVersion.MOD_ID, tag);
	}
	
	private void registerSandstoneMaterialTags(SandstoneMaterial material)
	{
		getOrCreateBuilder(BlockTags.WALLS).add(material.wall.get());
		getOrCreateBuilder(BlockTags.WALLS).add(material.smooth_wall.get());
		
		getOrCreateBuilder(BlockTags.SLABS).add(material.slab.get());
		getOrCreateBuilder(BlockTags.SLABS).add(material.smooth_slab.get());
		
		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs.get());
		getOrCreateBuilder(BlockTags.STAIRS).add(material.smooth_stairs.get());
		
		
		// Forge Tags
		getOrCreateBuilder(Tags.Blocks.SANDSTONE).add(material.stone.get());
		getOrCreateBuilder(Tags.Blocks.SANDSTONE).add(material.smooth.get());
	}

	private void registerStoneMaterialTags(StoneMaterial material)
	{
		getOrCreateBuilder(BlockTags.STONE_BRICKS).add(material.bricks.get());
		
		getOrCreateBuilder(BlockTags.WALLS).add(material.wall.get());
		getOrCreateBuilder(BlockTags.WALLS).add(material.brick_wall.get());
		
		getOrCreateBuilder(BlockTags.SLABS).add(material.slab.get());
		getOrCreateBuilder(BlockTags.SLABS).add(material.brick_slab.get());
		
		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs.get());
		getOrCreateBuilder(BlockTags.STAIRS).add(material.brick_stairs.get());
		
		getOrCreateBuilder(BlockTags.PRESSURE_PLATES).add(material.pressure_plate.get());
		getOrCreateBuilder(BlockTags.STONE_PRESSURE_PLATES).add(material.pressure_plate.get());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Blocks.STONE).add(material.stone.get());
	}
	
	private void registerMetalMaterialTags(MetalMaterial material)
	{	
		getOrCreateBuilder(BlockTags.BEACON_BASE_BLOCKS).add(material.block.get());
		
		getOrCreateBuilder(BlockTags.DOORS).add(material.door.get());
		
		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs.get());

		getOrCreateBuilder(BlockTags.TRAPDOORS).add(material.trapdoor.get());
		
		getOrCreateBuilder(BlockTags.SLABS).add(material.slab.get());
		
		
		// Forge Tags
		if (material.hasOre)
		{
			getOrCreateBuilder(Tags.Blocks.ORES).add(material.ore.get());
		}
		
		getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).add(material.block.get());
		getOrCreateBuilder(BlockTags.BEACON_BASE_BLOCKS).add(material.block.get());
	}
	
	private void registerGemMaterialTags(GemMaterial material)
	{	
		getOrCreateBuilder(BlockTags.BEACON_BASE_BLOCKS).add(material.block.get());
		
		
		getOrCreateBuilder(BlockTags.STAIRS).add(material.stairs.get());
		
		getOrCreateBuilder(BlockTags.SLABS).add(material.slab.get());
		
		// Forge Tags
		if (material.hasOre)
		{
			getOrCreateBuilder(Tags.Blocks.ORES).add(material.ore.get());
		}
		
		getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).add(material.block.get());
		getOrCreateBuilder(BlockTags.BEACON_BASE_BLOCKS).add(material.block.get());
	}
}
