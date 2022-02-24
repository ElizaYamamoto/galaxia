package io.github.elizayami.galaxia.core.enums;

import java.util.function.Supplier;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum ArmorMaterials implements IArmorMaterial
{
	METEOR(Galaxia.MOD_ID + ":meteor", 20, new int[]
	{ 2, 2, 2, 2 }, 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F, () ->
	{
		return Ingredient.fromItems(BlockInit.METEOR.ingot.get());
	}, 0.1F),

	SILVER(Galaxia.MOD_ID + ":silver", 13, new int[]
	{ 2, 4, 5, 2 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () ->
	{
		return Ingredient.fromItems(BlockInit.SILVER.ingot.get());
	}, 0F),

	BOLTRINE(Galaxia.MOD_ID + ":boltrine", 28, new int[]
	{ 2, 5, 7, 2 }, 13, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F, () ->
	{
		return Ingredient.fromItems(BlockInit.COMETSTEEL.ingot.get());
	}, 0.0F),

	COMETSTEEL(Galaxia.MOD_ID + ":cometsteel", 34, new int[]
	{ 3, 6, 7, 3 }, 13, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F, () ->
	{
		return Ingredient.fromItems(BlockInit.COMETSTEEL.ingot.get());
	}, 0.0F),

	GALAXIUM(Galaxia.MOD_ID + ":galaxium", 40, new int[]
	{ 5, 8, 10, 5 }, 22, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F, () ->
	{
		return Ingredient.fromItems(ItemInit.GALAXIUM_STAR.get());
	}, 0.15F);

	private static final int[] MAX_DAMAGE_ARRAY =
	{ 11, 16, 15, 13 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float thoughness;
	private final Supplier<Ingredient> repairMaterial;
	private final float knockbackResistance;

	ArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
			SoundEvent soundEvent, float thoughness, Supplier<Ingredient> repairMaterial, float knockbackResistance)
	{
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.thoughness = thoughness;
		this.repairMaterial = repairMaterial;
		this.knockbackResistance = knockbackResistance;
	}

	@Override
	public int getDurability(EquipmentSlotType slotIn)
	{
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn)
	{
		return damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability()
	{
		return enchantability;
	}

	@Override
	public SoundEvent getSoundEvent()
	{
		return soundEvent;
	}

	@Override
	public Ingredient getRepairMaterial()
	{
		return repairMaterial.get();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public float getToughness()
	{
		return thoughness;
	}

	@Override
	public float getKnockbackResistance()
	{
		return knockbackResistance;
	}

}
