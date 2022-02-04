package io.github.elizayami.galaxia.common.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import io.github.elizayami.galaxia.Galaxia;

public class FuserRecipe implements IRecipe<IInventory>
{
	public static final ResourceLocation TYPE_ID = Galaxia.createID("fuser");

	public static final IRecipeType<FuserRecipe> TYPE = IRecipeType.register(TYPE_ID.toString());

	private final ResourceLocation id;
	private final String group;
	private final Ingredient pick;
	private final Ingredient axe;
	private final Ingredient shovel;
	private final Ingredient hoe;
	private final ItemStack output;

	public FuserRecipe(ResourceLocation id, 
			String group, 
			Ingredient pick, 
			Ingredient axe,
			Ingredient shovel, 
			Ingredient hoe, 
			ItemStack output)
	{
		this.id = id;
		this.group = group;
		this.pick = pick;
		this.axe = axe;
		this.shovel = shovel;
		this.hoe = hoe;
		this.output = output;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn)
	{
		return false;
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv)
	{
		return this.output.copy();
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return true;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return output;
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return FuserRecipeSerializer.INSTANCE;
	}

	@Override
	public IRecipeType<?> getType()
	{
		return TYPE;
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.pick);
		nonnulllist.add(this.axe);
		nonnulllist.add(this.shovel);
		nonnulllist.add(this.hoe);
		return nonnulllist;
	}
	public Ingredient getPick()
	{
		return pick;
	}

	public Ingredient getAxe()
	{
		return axe;
	}

	public Ingredient getShovel()
	{
		return shovel;
	}

	public Ingredient getHoe()
	{
		return hoe;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	@Override
	public String getGroup()
	{
		return group;
	}
}
