package io.github.elizayami.galaxia.common.abstracts.items;

import java.util.function.Function;

import net.minecraft.item.IItemTier;
import net.minecraftforge.common.ToolType;

public class BackhawItem extends LargeToolItem
{
	public BackhawItem(float attackDamageIn, float attackSpeedIn, IItemTier tier,  Function<Properties, Properties> properties)
	{
		super(attackDamageIn, 
				attackSpeedIn, 
				tier, 
				properties.apply(new Properties().defaultMaxDamage((int) (tier.getMaxUses() * 1.5))
						.addToolType(ToolType.PICKAXE, tier.getMaxUses())
						.addToolType(ToolType.SHOVEL, tier.getMaxUses())
						.addToolType(ToolType.AXE, tier.getMaxUses())
						.addToolType(ToolType.HOE, tier.getMaxUses())));
	}
}
