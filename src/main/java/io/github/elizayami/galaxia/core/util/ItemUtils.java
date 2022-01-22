package io.github.elizayami.galaxia.core.util;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ItemUtils
{

	public static boolean handCheck(PlayerEntity sourceentity, Item itemType)
	{
		LivingEntity player = ((LivingEntity) sourceentity);

		if (player.getHeldItemOffhand().getItem() == itemType || (player.getHeldItemMainhand().getItem() == itemType))
		{
			return true;
		}
		return false;
	}

	public static void replaceItem(PlayerEntity sourceentity, IWorld world, IItemProvider itemRemoved,
			IItemProvider itemAdded)
	{
		ItemStack _stktoremove = new ItemStack(itemRemoved);
		sourceentity.inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
				sourceentity.container.func_234641_j_());

		ItemEntity entityToSpawn = new ItemEntity((World) world, sourceentity.getPosX(), sourceentity.getPosY(),
				sourceentity.getPosZ(), new ItemStack(itemAdded));
		entityToSpawn.setPickupDelay(0);
		world.addEntity(entityToSpawn);
	}

	public static boolean addOrDropStack(PlayerEntity player, ItemStack stack)
	{
		if (!player.inventory.addItemStackToInventory(stack))
		{
			player.dropItem(stack, true);
			return false;
		}
		return true;
	}

	public static void applyEnchantment(ItemStack stack, Enchantment enchantment, int level)
	{
		Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
		enchants.put(enchantment, level);
		EnchantmentHelper.setEnchantments(enchants, stack);
	}

	public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment)
	{
		Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
		return enchants.containsKey(enchantment);
	}
}
