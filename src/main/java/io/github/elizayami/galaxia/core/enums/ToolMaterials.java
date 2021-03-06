package io.github.elizayami.galaxia.core.enums;

import java.util.function.Supplier;

import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ToolMaterials implements IItemTier
{
	METEOR(2, 2000, 3, 0, 1, () -> Ingredient.fromItems(BlockInit.METEOR.ingot.get())),
	
	SILVER(2, 150, 6.0F, 1.5F, 18, () -> Ingredient.fromItems(BlockInit.SILVER.ingot.get())),

	BOLTRINE(3, 1000, 8.0F, 3.5F, 12, () -> Ingredient.fromItems(BlockInit.METEOR.ingot.get())),

	COMETSTEEL(5, 3000, 11, 5f, 10, () -> Ingredient.fromItems(BlockInit.COMETSTEEL.ingot.get())),

	GALAXIUM(6, 5000, 11, 6, 22, () -> Ingredient.fromItems(BlockInit.COMETSTEEL.ingot.get()));

	private final int harvestLevel, maxUses, enchantability;
	private final float efficiency, attackDamage;
	private final LazyValue<Ingredient> repairMaterial;

	private ToolMaterials(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn,
			int enchantabilityIn, Supplier<Ingredient> repairMaterialIn)
	{
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<Ingredient>(repairMaterialIn);
	}

	@Override
	public int getMaxUses()
	{
		return this.maxUses;
	}

	@Override
	public float getEfficiency()
	{
		return this.efficiency;
	}

	@Override
	public float getAttackDamage()
	{
		return this.attackDamage;
	}

	@Override
	public int getHarvestLevel()
	{
		return this.harvestLevel;
	}

	@Override
	public int getEnchantability()
	{
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairMaterial()
	{
		return this.repairMaterial.getValue();
	}
}
