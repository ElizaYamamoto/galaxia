package io.github.elizayami.galaxia.common.recipes;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class FuserRecipeSerializer 
extends ForgeRegistryEntry<IRecipeSerializer<?>> 
implements IRecipeSerializer<FuserRecipe>
{

	public static final FuserRecipeSerializer INSTANCE = new FuserRecipeSerializer();

	static
	{
		INSTANCE.setRegistryName(FuserRecipe.TYPE_ID);
	}

	private FuserRecipeSerializer()
	{
	}

	@Override
	public FuserRecipe read(ResourceLocation recipeId, JsonObject json)
	{
		String group = JSONUtils.getString(json, "group", "");
		
		ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));

		JsonObject ingredients = JSONUtils.getJsonObject(json, "ingredients");
		
		Ingredient pick = Ingredient.deserialize(ingredients.get("pick"));
		Ingredient axe = Ingredient.deserialize(ingredients.get("axe"));
		Ingredient shovel = Ingredient.deserialize(ingredients.get("shovel"));
		Ingredient hoe = Ingredient.deserialize(ingredients.get("hoe"));

		return new FuserRecipe(recipeId, group, hoe, pick, axe, shovel, result);
	}

	@Nullable
	@Override
	public FuserRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
	{
		String group = buffer.readString(32767);
		Ingredient pick = Ingredient.read(buffer);
		Ingredient axe = Ingredient.read(buffer);
		Ingredient shovel = Ingredient.read(buffer);
		Ingredient hoe = Ingredient.read(buffer);
		
		ItemStack result = buffer.readItemStack();

		return new FuserRecipe(recipeId, group, hoe, pick, axe, shovel, result);
	}

	@Override
	public void write(PacketBuffer buffer, FuserRecipe recipe)
	{
		buffer.writeString(recipe.getGroup());
		buffer.writeItemStack(recipe.getRecipeOutput());
		recipe.getPick().write(buffer);
		recipe.getAxe().write(buffer);
		recipe.getShovel().write(buffer);
		recipe.getHoe().write(buffer);
	}

}
