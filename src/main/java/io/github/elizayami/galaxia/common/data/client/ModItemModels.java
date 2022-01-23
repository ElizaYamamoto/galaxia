package io.github.elizayami.galaxia.common.data.client;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.materials.GemMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.SandstoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider
{
	public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) 
	{
		super(generator, Galaxia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() 
	{
        getBuilder("comet_trail_dust").parent(new ModelFile.UncheckedModelFile("item/generated")).
        texture("layer0", modLoc("item/comet_trail_dust"));
        
        // WOODEN MATERIALS
		
		registerWoodenMaterialItemModels(BlockInit.SHADOWSPIKE);
		registerWoodenMaterialItemModels(BlockInit.GROUNDSTALK);
		registerWoodenMaterialItemModels(BlockInit.SEAWOOD);
		registerWoodenMaterialItemModels(BlockInit.SCORCHWOOD);
		registerWoodenMaterialItemModels(BlockInit.GHOSTWOOD);
		registerWoodenMaterialItemModels(BlockInit.GROVEWOOD);
		
		// STONE MATERIALS
		
		registerStoneMaterialItemModels(BlockInit.DRAGONSTONE);

		// SANDSTONE MATERIALS
		
		registerSandstoneMaterialItemModels(BlockInit.SOULSANDSTONE);
		registerSandstoneMaterialItemModels(BlockInit.IMPACTSANDSTONE);
		
		// METAL MATERIALS
		registerMetalMaterialItemModels(BlockInit.METEOR);
		registerMetalMaterialItemModels(BlockInit.COMETSTEEL);
		
		// Gem Materials
		registerGemMaterialItemModels(BlockInit.GALAXIUM);
	}
	
	private ItemModelBuilder simpleItem(Item item) {
		String name = item.getRegistryName().getPath();
		return singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(ITEM_FOLDER + "/" + name));
	}
	
	private void registerWoodenMaterialItemModels(WoodenMaterial material)
	{
        getBuilder(material.name + "_door").parent(new ModelFile.UncheckedModelFile("item/generated")).
        		texture("layer0", modLoc("item/" + material.name + "_door"));

        fenceInventory(material.name + "_fence", modLoc("block/" + material.name + "_planks"));
        
        buttonInventory(material.name, modLoc("block/" + material.name + "_planks"));
        
        singleTexture(material.name + "_ladder", mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc("block/" + material.name + "_ladder"));
        
		getBuilder(material.name + "_chest").parent(new ModelFile.UncheckedModelFile(ITEM_FOLDER + "/chest")).texture("particle",
				modLoc("block/" + material.name + "_planks"));
		
        singleTexture(material.name + "_sign", mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc("item/" + material.name + "_sign"));
	}
	
	private void registerStoneMaterialItemModels(StoneMaterial material)
	{
		wallInventory(material.name + "_wall", modLoc("block/" + material.name));
		
		wallInventory(material.name + "_bricks_wall", modLoc("block/" + material.name + "_bricks"));
		
		buttonInventory(material.name, modLoc("block/" + material.name));
	}
	
	private void registerSandstoneMaterialItemModels(SandstoneMaterial material)
	{
		wallInventory(material.name + "_wall", modLoc("block/" + material.name));
		
		wallInventory(material.name + "_smooth_wall", modLoc("block/" + material.name + "_smooth"));
	}
	
	
	private void registerMetalMaterialItemModels(MetalMaterial material)
	{
        getBuilder(material.name + "_door").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_door"));
        
        getBuilder(material.name + "_nugget").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_nugget"));
        
        getBuilder(material.name + "_ingot").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_ingot"));

        // Tools and armor
        getBuilder(material.name + "_shovel").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_shovel"));
        
        getBuilder(material.name + "_sword").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_sword"));
        
        getBuilder(material.name + "_pickaxe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_pickaxe"));
        
        getBuilder(material.name + "_axe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_axe"));
        
        getBuilder(material.name + "_hoe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_hoe"));

        getBuilder(material.name + "_helmet").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_helmet"));
        
        getBuilder(material.name + "_chestplate").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_chestplate"));
        
        getBuilder(material.name + "_leggings").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_leggings"));
       
        getBuilder(material.name + "_boots").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_boots"));
	}
	

	private void registerGemMaterialItemModels(GemMaterial material)
	{
        // Tools and armor
        getBuilder(material.name + "_shovel").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_shovel"));
        
        getBuilder(material.name + "_sword").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_sword"));
        
        getBuilder(material.name + "_pickaxe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_pickaxe"));
        
        getBuilder(material.name + "_axe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_axe"));
        
        getBuilder(material.name + "_hoe").parent(new ModelFile.UncheckedModelFile("item/handheld")).
                texture("layer0", modLoc("item/" + material.name + "_hoe"));

        getBuilder(material.name + "_helmet").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_helmet"));
        
        getBuilder(material.name + "_chestplate").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_chestplate"));
        
        getBuilder(material.name + "_leggings").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_leggings"));
       
        getBuilder(material.name + "_boots").parent(new ModelFile.UncheckedModelFile("item/generated")).
                texture("layer0", modLoc("item/" + material.name + "_boots"));
	}

	private void buttonInventory(String material, ResourceLocation texture)
	{
        singleTexture(material + "_button", mcLoc(BLOCK_FOLDER + "/button_inventory"), texture);
	}
}
