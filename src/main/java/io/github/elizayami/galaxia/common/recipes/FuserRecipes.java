package io.github.elizayami.galaxia.common.recipes;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.collect.Iterables;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public final class FuserRecipes
{
	private FuserRecipes()
	{
	}

	public static Iterable<FuserRecipe> getRecipes(World world)
	{
		Collection<IRecipe<IInventory>> unfilteredRecipes = world.getRecipeManager().getRecipes(FuserRecipe.TYPE)
				.values();
		return Iterables.filter(unfilteredRecipes, FuserRecipe.class);
	}

	@Nullable
	public static FuserRecipe findRecipe(World world, ItemStack pick, ItemStack axe, ItemStack shovel, ItemStack hoe,
			ItemStack output)
	{
		for (final FuserRecipe recipe : getRecipes(world))
		{
			final boolean match = recipe.getPick().test(pick) && recipe.getAxe().test(axe)
					&& recipe.getShovel().test(shovel) && recipe.getHoe().test(hoe);
			if (match)
			{
				return recipe;
			}
		}

		return null;
	}
}
