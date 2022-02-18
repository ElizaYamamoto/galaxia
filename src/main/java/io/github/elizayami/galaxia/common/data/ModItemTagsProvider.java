package io.github.elizayami.galaxia.common.data;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.NetherrackMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.SandstoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.TagInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.versions.forge.ForgeVersion;

public class ModItemTagsProvider extends ItemTagsProvider
{
	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) 
	{
		super(dataGenerator, blockTagProvider, Galaxia.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerTags() 
	{
		// Misc Forge tags
		
		getOrCreateBuilder(Tags.Items.ORES).add(BlockInit.SOAL_ORE.get().asItem());
		
		// WOODEN MATERIALS
		
		registerWoodenMaterialTags(BlockInit.SHADOWSPIKE);
		registerWoodenMaterialTags(BlockInit.GROUNDSTALK);
		registerWoodenMaterialTags(BlockInit.SEAWOOD);
		registerWoodenMaterialTags(BlockInit.SCORCHWOOD);
		registerWoodenMaterialTags(BlockInit.GHOSTWOOD);
		registerWoodenMaterialTags(BlockInit.GROVEWOOD);
		
		// STONE MATERIALS
		registerStoneMaterialTags(BlockInit.DRAGONSTONE);

		// NETHERRACK MATERIALS

		registerNetherrackMaterialTags(BlockInit.GALVIROCK);
		registerNetherrackMaterialTags(BlockInit.WITHERRACK);
		
		// SANDSTONE MATERIALS
		registerSandstoneMaterialTags(BlockInit.SOULSANDSTONE);
		registerSandstoneMaterialTags(BlockInit.IMPACTSANDSTONE);
		
		// METAL MATERIALS
		registerMetalMaterialTags(BlockInit.METEOR);
		registerMetalMaterialTags(BlockInit.COMETSTEEL);
	}
	
	private ResourceLocation frl(String tag) {
		return new ResourceLocation(ForgeVersion.MOD_ID, tag);
	}

	private void registerWoodenMaterialTags(WoodenMaterial material)
	{
		this.copy(material.logBlockTag, material.logItemTag);
	
		getOrCreateBuilder(ItemTags.PLANKS).add(material.planks.get().asItem());
		
		getOrCreateBuilder(ItemTags.LOGS).add(material.log.get().asItem(), material.bark.get().asItem(), material.log_stripped.get().asItem(), material.bark_stripped.get().asItem());
		
		getOrCreateBuilder(ItemTags.LOGS_THAT_BURN).add(material.log.get().asItem(), material.bark.get().asItem(), material.log_stripped.get().asItem(), material.bark_stripped.get().asItem());
		
		getOrCreateBuilder(ItemTags.BUTTONS).add(material.button.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_BUTTONS).add(material.button.get().asItem());
		
		getOrCreateBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(material.pressurePlate.get().asItem());
		
		getOrCreateBuilder(ItemTags.DOORS).add(material.door.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_DOORS).add(material.door.get().asItem());
		
		getOrCreateBuilder(ItemTags.FENCES).add(material.fence.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_FENCES).add(material.fence.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_SLABS).add(material.slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_STAIRS).add(material.stairs.get().asItem());
		
		getOrCreateBuilder(ItemTags.TRAPDOORS).add(material.trapdoor.get().asItem());
		getOrCreateBuilder(ItemTags.WOODEN_TRAPDOORS).add(material.trapdoor.get().asItem());
		
		getOrCreateBuilder(ItemTags.SIGNS).add(material.sign.get().asItem());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.FENCES).add(material.fence.get().asItem());
		getOrCreateBuilder(Tags.Items.FENCES_WOODEN).add(material.fence.get().asItem());

		getOrCreateBuilder(Tags.Items.FENCE_GATES).add(material.gate.get().asItem());
		getOrCreateBuilder(Tags.Items.FENCE_GATES_WOODEN).add(material.gate.get().asItem());
		
		getOrCreateBuilder(Tags.Items.CHESTS).add(material.chest.get().asItem());
		getOrCreateBuilder(Tags.Items.CHESTS_WOODEN).add(material.chest.get().asItem());
		
		getOrCreateBuilder(Tags.Items.BOOKSHELVES).add(material.shelf.get().asItem());
		getOrCreateBuilder(TagInit.ITEM_WORKBENCH).add(material.craftingTable.get().asItem());
		
		// Used by the Metal Barrels mod
		getOrCreateBuilder(TagInit.ITEM_BARRELS).add(material.barrel.get().asItem());
	}
	
	private void registerStoneMaterialTags(StoneMaterial material)
	{
		getOrCreateBuilder(ItemTags.STONE_BRICKS).add(material.bricks.get().asItem());
		
		getOrCreateBuilder(ItemTags.WALLS).add(material.wall.get().asItem());
		getOrCreateBuilder(ItemTags.WALLS).add(material.brick_wall.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		getOrCreateBuilder(ItemTags.SLABS).add(material.brick_slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		getOrCreateBuilder(ItemTags.STAIRS).add(material.brick_stairs.get().asItem());
		
		getOrCreateBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(material.stone.get().asItem());
		getOrCreateBuilder(ItemTags.STONE_TOOL_MATERIALS).add(material.stone.get().asItem());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.STONE).add(material.stone.get().asItem());
	}
	
	private void registerNetherrackMaterialTags(NetherrackMaterial material)
	{
		getOrCreateBuilder(ItemTags.STONE_BRICKS).add(material.bricks.get().asItem());

		getOrCreateBuilder(Tags.Items.FENCES).add(material.fence.get().asItem());

		getOrCreateBuilder(Tags.Items.FENCE_GATES).add(material.gate.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		
		getOrCreateBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(material.stone.get().asItem());
		getOrCreateBuilder(ItemTags.STONE_TOOL_MATERIALS).add(material.stone.get().asItem());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.STONE).add(material.stone.get().asItem());
		getOrCreateBuilder(Tags.Items.NETHERRACK).add(material.stone.get().asItem());
	}


	private void registerSandstoneMaterialTags(SandstoneMaterial material)
	{
		getOrCreateBuilder(ItemTags.WALLS).add(material.wall.get().asItem());
		getOrCreateBuilder(ItemTags.WALLS).add(material.smooth_wall.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		getOrCreateBuilder(ItemTags.SLABS).add(material.smooth_slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		getOrCreateBuilder(ItemTags.STAIRS).add(material.smooth_stairs.get().asItem());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.STONE).add(material.stone.get().asItem());
		getOrCreateBuilder(Tags.Items.STONE).add(material.smooth.get().asItem());
	}
	
	private void registerMetalMaterialTags(MetalMaterial material)
	{
		getOrCreateBuilder(ItemTags.WOODEN_DOORS).add(material.door.get().asItem());
		
		getOrCreateBuilder(ItemTags.SLABS).add(material.slab.get().asItem());
		
		getOrCreateBuilder(ItemTags.STAIRS).add(material.stairs.get().asItem());
		
		getOrCreateBuilder(ItemTags.WOODEN_TRAPDOORS).add(material.trapdoor.get().asItem());
		
		getOrCreateBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(material.ingot.get());
		
		// Forge Tags
		getOrCreateBuilder(Tags.Items.NUGGETS).add(material.nugget.get());
		
		getOrCreateBuilder(Tags.Items.INGOTS).add(material.ingot.get());
		
		if (material.hasOre)
		{
			getOrCreateBuilder(Tags.Items.ORES).add(material.ore.get().asItem());
		}
		
		getOrCreateBuilder(Tags.Items.STORAGE_BLOCKS).add(material.block.get().asItem());
		
	}
}
