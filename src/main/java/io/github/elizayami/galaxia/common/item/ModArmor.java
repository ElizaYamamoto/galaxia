package io.github.elizayami.galaxia.common.item;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModArmor extends ArmorItem
{
	String type = null;
	boolean model = false;

	public ModArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, String Type,
			Boolean hasCustomModel)
	{
		super(materialIn, slot, builderIn);
		type = Type;
		model = hasCustomModel;
	}

	public String getTranslationKey()
	{
		switch (this.slot)
		{
		case HEAD:
			return "item.galaxia." + type + "_helmet";
		case CHEST:
			return "item.galaxia." + type + "_chestplate";
		case LEGS:
			return "item.galaxia." + type + "_leggings";
		case FEET:
			return "item.galaxia" + type + "_boots";
		default:
			break;
		}
		return "item.galaxia." + type + "_helmet";
	}

	@OnlyIn(Dist.CLIENT)
	@Nullable
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity LivingEntity, ItemStack itemStack,
			EquipmentSlotType armorSlot, A _default)
	{
		if (model)
		{
			return (A) Galaxia.PROXY.getArmorModel((slot == EquipmentSlotType.LEGS ? 1 : 0));
		}
		return _default;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return "galaxia:textures/models/armor/" + type
				+ (slot == EquipmentSlotType.LEGS ? "_layer_2.png" : "_layer_1.png");
	}
}
