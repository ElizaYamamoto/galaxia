package io.github.elizayami.galaxia.common.data;

import java.util.function.Consumer;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.StoneMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModRecipes extends RecipeProvider
{
	public ModRecipes(DataGenerator generatorIn) 
	{
		super(generatorIn);
	}
	
	private ResourceLocation rl(String s) 
	{
		return new ResourceLocation(Galaxia.MOD_ID, s);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) 
	{
		//ShapedRecipeBuilder.shapedRecipe(Result, Number).key('X', ItemInit.Item.get()).patternLine("###").addCriterion("has_end_lily_leaf_dried", hasItem(ModItems.END_LILY_LEAF_DRIED.get())).build(consumer, rl("paper_from_end_lily_leaf_dried"));
		
		//ShapelessRecipeBuilder.shapelessRecipe(Result, Number).addIngredient(ItemInit.Item.get()).addCriterion("has_item", hasItem(ItemInit.Item.get())).build(consumer, rl("item_from_item"));
		
		// BLOCKS
		
		// ITEMS
		
	    // LANTERNS
	    
	    // PEDESTALS
	    
		// FURNACE
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.SOAL_ORE.get()), ItemInit.SOAL.get(), 0.35F, 200).addCriterion("has_soal_ore", hasItem(ItemInit.SOAL_ORE.get())).build(consumer, rl("soal_from_ore"));
		
		// ARMORS AND TOOLS
		makeToolsetRecipes(BlockInit.METEOR, consumer);
		makeToolsetRecipes(BlockInit.COMETSTEEL, consumer);
		
		// WOODEN MATERIALS
		
		// STONE MATERIALS
		
		// METAL MATERIALS
		
		// COLORED MATERIALS
	}
	
	private void makeWoodenMaterialRecipes(WoodenMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		ShapelessRecipeBuilder.shapelessRecipe(material.planks.get(), 4).addIngredient(material.logItemTag).setGroup("end_planks").addCriterion("has_logs", hasItem(material.logItemTag)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.planks.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_planks__stairs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.planks.get()).patternLine("###").setGroup("end_planks_slabs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.fence.get(), 3).key('#', Items.STICK).key('W', material.planks.get()).patternLine("W#W").patternLine("W#W").setGroup("end_planks_fences").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.gate.get()).key('#', Items.STICK).key('W', material.planks.get()).patternLine("#W#").patternLine("#W#").setGroup("end_planks_gates").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(material.button.get()).addIngredient(material.planks.get()).setGroup("end_planks_buttons").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressurePlate.get()).key('#', material.planks.get()).patternLine("##").setGroup("end_planks_plates").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.trapdoor.get(), 2).key('#', material.planks.get()).patternLine("###").patternLine("###").setGroup("end_trapdoors").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.door.get(), 3).key('#', material.planks.get()).patternLine("##").patternLine("##").patternLine("##").setGroup("end_doors").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bark.get(), 3).key('#', material.log.get()).patternLine("##").patternLine("##").addCriterion("has_log", hasItem(material.log.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.bark_stripped.get(), 3).key('#', material.log_stripped.get()).patternLine("##").patternLine("##").addCriterion("has_log_stripped", hasItem(material.log_stripped.get())).build(consumer);
	    
	    ShapedRecipeBuilder.shapedRecipe(material.composter.get(), 1).key('#', material.slab.get()).patternLine("# #").patternLine("# #").patternLine("###").setGroup("end_composters").addCriterion("has_slabs", hasItem(material.slab.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.craftingTable.get(), 1).key('#', material.planks.get()).patternLine("##").patternLine("##").setGroup("end_crafting_tables").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.ladder.get(), 3).key('#', material.planks.get()).key('I', Items.STICK).patternLine("I I").patternLine("I#I").patternLine("I I").setGroup("end_ladders").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.chest.get(), 1).key('#', material.planks.get()).patternLine("###").patternLine("# #").patternLine("###").setGroup("end_chests").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.sign.get(), 3).key('#', material.planks.get()).key('I', Items.STICK).patternLine("###").patternLine("###").patternLine(" I ").setGroup("end_signs").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.barrel.get(), 1).key('#', material.planks.get()).key('S', material.slab.get()).patternLine("#S#").patternLine("# #").patternLine("#S#").setGroup("end_barrels").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.shelf.get(), 1).key('#', material.planks.get()).key('P', Items.BOOK).patternLine("###").patternLine("PPP").patternLine("###").setGroup("end_bookshelves").addCriterion("has_planks", hasItem(material.planks.get())).build(consumer);
	}
	
	private void makeStoneMaterialRecipes(StoneMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		// Crafting
		ShapedRecipeBuilder.shapedRecipe(material.bricks.get(), 4).key('#', material.stone.get()).patternLine("##").patternLine("##").setGroup("end_bricks").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.polished.get(), 4).key('#', material.bricks.get()).patternLine("##").patternLine("##").setGroup("end_tile").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(material.tiles.get(), 4).key('#', material.polished.get()).patternLine("##").patternLine("##").setGroup("end_small_tile").addCriterion("has_" + material.polished.get().getRegistryName().getPath(), hasItem(material.polished.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.stone.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.stone.get()).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_stairs.get(), 4).key('#', material.bricks.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_stone_stairs").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_slab.get(), 6).key('#', material.bricks.get()).patternLine("###").setGroup("end_stone_slabs").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.wall.get(), 6).key('#', material.stone.get()).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.brick_wall.get(), 6).key('#', material.bricks.get()).patternLine("###").patternLine("###").setGroup("end_wall").addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(material.button.get()).addIngredient(material.stone.get()).setGroup("end_stone_buttons").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressure_plate.get()).key('#', material.stone.get()).patternLine("##").setGroup("end_stone_plates").addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer);

		// Stonecutting
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.bricks.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.polished.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_polished_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.tiles.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_tiles_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.stairs.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_stairs_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.slab.get(), 2).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_slab_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.brick_stairs.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.brick_slab.get(), 2).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.wall.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_wall_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.brick_wall.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.stone.get()), material.button.get()).addCriterion("has_" + material.stone.get().getRegistryName().getPath(), hasItem(material.stone.get())).build(consumer, rl(material.name + "_button_from_" + material.name + "_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks.get()), material.brick_stairs.get()).addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer, rl(material.name + "_bricks_stairs_from_" + material.name + "_bricks_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks.get()), material.brick_slab.get(), 2).addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer, rl(material.name + "_bricks_slab_from_" + material.name + "_bricks_stonecutting"));
		SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(material.bricks.get()), material.brick_wall.get()).addCriterion("has_" + material.bricks.get().getRegistryName().getPath(), hasItem(material.bricks.get())).build(consumer, rl(material.name + "_bricks_wall_from_" + material.name + "_bricks_stonecutting"));
	}
	
	private void makeToolsetRecipes(MetalMaterial material, Consumer<IFinishedRecipe> consumer)
	{
	    makeIngotAndBlockRecipes(material.block.get(), material.ingot.get(), consumer, material.name);
	    ShapedRecipeBuilder.shapedRecipe(material.ingot.get()).key('#', material.nugget.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material.name + "_nugget", hasItem(material.nugget.get())).build(consumer, rl(material.name + "_ingot_from_" + material.name + "_nuggets"));
	    
	    // Furnace & blast furnace
	    float exp = 0.35f;
	    int smeltTime = 200;
	    int blastTime = smeltTime / 2;
	    if (material.hasOre)
	    {
	    	CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(material.ore.get()), material.ingot.get(), exp, smeltTime).addCriterion("has_" + material.name + "_ore", hasItem(material.ore.get())).build(consumer, rl(material.name + "_ingot_from_smelting"));
	    	CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(material.ore.get()), material.ingot.get(), exp, blastTime).addCriterion("has_" + material.name + "_ore", hasItem(material.ore.get())).build(consumer, rl(material.name + "_ingot_from_blasting"));
	    }
	    Item[] nuggetables = new Item[] { material.axe.get(), material.pickaxe.get(), material.shovel.get(), material.hoe.get(),material.sword.get(), material.helmet.get(), material.chestplate.get(), material.leggings.get(), material.boots.get() };
	    CookingRecipeBuilder nuggetSmeltingRecipes = CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(nuggetables), material.nugget.get(), exp, smeltTime);
	    CookingRecipeBuilder nuggetBlastingRecipes = CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(nuggetables), material.nugget.get(), exp, blastTime);
	    for (Item nuggetable : nuggetables) {
	    	nuggetSmeltingRecipes.addCriterion("has_" + nuggetable.getRegistryName().getPath(), hasItem(nuggetable));
	    	nuggetBlastingRecipes.addCriterion("has_" + nuggetable.getRegistryName().getPath(), hasItem(nuggetable));
	    }
	    nuggetSmeltingRecipes.build(consumer, rl(material.name + "_nugget_from_smelting"));
	    nuggetBlastingRecipes.build(consumer, rl(material.name + "_nugget_from_blasting"));

		// Armor
	    makeHelmetRecipe(material.helmet.get(), material.ingot.get(), consumer, material.name);
		makeChestplateRecipe(material.chestplate.get(), material.ingot.get(), consumer, material.name);
		makeLeggingsRecipe(material.leggings.get(), material.ingot.get(), consumer, material.name);
		makeBootsRecipe(material.boots.get(), material.ingot.get(), consumer, material.name);
	}
	
	private void makeMetalMaterialRecipes(MetalMaterial material, Consumer<IFinishedRecipe> consumer)
	{
		// Base
	    makeIngotAndBlockRecipes(material.block.get(), material.ingot.get(), consumer, material.name);
	    ShapedRecipeBuilder.shapedRecipe(material.ingot.get()).key('#', material.nugget.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material.name + "_nugget", hasItem(material.nugget.get())).build(consumer, rl(material.name + "_ingot_from_" + material.name + "_nuggets"));
		
	    // Blocks
	    ShapedRecipeBuilder.shapedRecipe(material.tile.get(), 4).key('#', material.block.get()).patternLine("##").patternLine("##").setGroup("end_metal_tile").addCriterion("has_" + material.name + "_block", hasItem(material.block.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.stairs.get(), 4).key('#', material.tile.get()).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("end_metal_stairs").addCriterion("has_" + material.name + "_block", hasItem(material.block.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.slab.get(), 6).key('#', material.tile.get()).patternLine("###").setGroup("end_metal_slabs").addCriterion("has_" + material.block + "_block", hasItem(material.block.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.door.get(), 3).key('#', material.ingot.get()).patternLine("##").patternLine("##").patternLine("##").setGroup("end_metal_doors").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.trapdoor.get()).key('#', material.ingot.get()).patternLine("##").patternLine("##").setGroup("end_metal_trapdoors").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(material.pressure_plate.get()).key('#', material.ingot.get()).patternLine("##").setGroup("end_metal_plates").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer);
	    ShapedRecipeBuilder.shapedRecipe(Blocks.SMITHING_TABLE).key('I', material.ingot.get()).key('#', ItemTags.PLANKS).patternLine("II").patternLine("##").patternLine("##").addCriterion("has_" + material.name + "_ingot", hasItem(material.ingot.get())).build(consumer, rl("smithing_table_from_" + material.name + "_ingot"));
	    
	    // Furnace & blast furnace
	    float exp = 0.35f;
	    int smeltTime = 200;
	    int blastTime = smeltTime / 2;
	    if (material.hasOre)
	    {
	    	CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(material.ore.get()), material.ingot.get(), exp, smeltTime).addCriterion("has_" + material.name + "_ore", hasItem(material.ore.get())).build(consumer, rl(material.name + "_ingot_from_smelting"));
	    	CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(material.ore.get()), material.ingot.get(), exp, blastTime).addCriterion("has_" + material.name + "_ore", hasItem(material.ore.get())).build(consumer, rl(material.name + "_ingot_from_blasting"));
	    }
	    Item[] nuggetables = new Item[] { material.axe.get(), material.pickaxe.get(), material.shovel.get(), material.hoe.get(),material.sword.get(), material.helmet.get(), material.chestplate.get(), material.leggings.get(), material.boots.get() };
	    CookingRecipeBuilder nuggetSmeltingRecipes = CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(nuggetables), material.nugget.get(), exp, smeltTime);
	    CookingRecipeBuilder nuggetBlastingRecipes = CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(nuggetables), material.nugget.get(), exp, blastTime);
	    for (Item nuggetable : nuggetables) {
	    	nuggetSmeltingRecipes.addCriterion("has_" + nuggetable.getRegistryName().getPath(), hasItem(nuggetable));
	    	nuggetBlastingRecipes.addCriterion("has_" + nuggetable.getRegistryName().getPath(), hasItem(nuggetable));
	    }
	    nuggetSmeltingRecipes.build(consumer, rl(material.name + "_nugget_from_smelting"));
	    nuggetBlastingRecipes.build(consumer, rl(material.name + "_nugget_from_blasting"));

		// Armor
	    makeHelmetRecipe(material.helmet.get(), material.ingot.get(), consumer, material.name);
		makeChestplateRecipe(material.chestplate.get(), material.ingot.get(), consumer, material.name);
		makeLeggingsRecipe(material.leggings.get(), material.ingot.get(), consumer, material.name);
		makeBootsRecipe(material.boots.get(), material.ingot.get(), consumer, material.name);
	}
	
	private void cookFood(Item in, Item out, float exp, int time, Consumer<IFinishedRecipe> consumer) {
		CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(in), out, exp, time).addCriterion("has_" + in.getRegistryName().getPath(), hasItem(in)).build(consumer);
	    CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(in), out, exp, time / 2, IRecipeSerializer.SMOKING).addCriterion("has_" + in.getRegistryName().getPath(), hasItem(in)).build(consumer, new ResourceLocation(Galaxia.MOD_ID, out.getRegistryName().getPath() + "_from_smoking"));
	    CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(in), out, exp, time * 3, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_" + in.getRegistryName().getPath(), hasItem(in)).build(consumer, new ResourceLocation(Galaxia.MOD_ID, out.getRegistryName().getPath() + "_from_campfire_cooking"));
	}
	
	private void makeSmithingRecipe(Item base, Item addition, Item output, Consumer<IFinishedRecipe> consumer)
	{
		SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(base), Ingredient.fromItems(addition), output).addCriterion("has_" + addition.getRegistryName().getPath(), hasItem(addition)).build(consumer, rl(output.getRegistryName().getPath() + "_smithing"));
	}
	
	private void makeIngotAndBlockRecipes(Block block, Item ingot, Consumer<IFinishedRecipe> consumer, String material)
	{
	    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingot).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_" + material + "_ingot", hasItem(ingot)).build(consumer);
	    ShapelessRecipeBuilder.shapelessRecipe(ingot, 9).addIngredient(block).setGroup(material + "_ingot").addCriterion("has_" + material + "_block", hasItem(block)).build(consumer, rl(material + "_ingot_from_" + material + "_block"));
	}
	
	private void makeHelmetRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeChestplateRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeLeggingsRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeBootsRecipe(Item result, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(result).key('X', ingredient).patternLine("X X").patternLine("X X").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeSwordRecipe(Item sword, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(sword).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makePickaxeRecipe(Item pickaxe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(pickaxe).key('#', Items.STICK).key('X', ingredient).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeAxeRecipe(Item axe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(axe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeShovelRecipe(Item shovel, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(shovel).key('#', Items.STICK).key('X', ingredient).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
	
	private void makeHoeRecipe(Item hoe, Item ingredient, Consumer<IFinishedRecipe> consumer, String material)
	{
		ShapedRecipeBuilder.shapedRecipe(hoe).key('#', Items.STICK).key('X', ingredient).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_" + material + "_ingot", hasItem(ingredient)).build(consumer);
	}
}
