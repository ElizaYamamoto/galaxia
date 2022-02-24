package io.github.elizayami.galaxia.common.effects;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.server.ServerWorld;

public class Overcharged extends Effect
{
	public Overcharged()
	{
		super(EffectType.HARMFUL, 0x97C1C5);
	}

	public boolean isReady(int tick, int level)
	{
		return tick > 0 && tick % 20 == 0;
	}

	public static final DamageSource OverchargedDamage(LivingEntity lastAttacker)
	{
		if (lastAttacker != null)
		{
			return new EntityDamageSource("overcharged", lastAttacker).setDamageBypassesArmor().setMagicDamage();
		}
		else
		{
			return new DamageSource("overcharged").setDamageBypassesArmor().setMagicDamage();
		}
	}

	@Override
	public void performEffect(LivingEntity target, int level)
	{
		DamageSource source = OverchargedDamage(target.getLastAttackedEntity());

		// perform damage
		int hurtResistantTime = target.hurtResistantTime;

		if (target.isEntityUndead())
		{
			target.attackEntityFrom(source, (float) (6 << level));
		}
		else
		{
			if (target.getHealth() < target.getMaxHealth())
			{
				target.heal(1.0F);
			}
		}

		target.hurtResistantTime = hurtResistantTime;

		// damage particles
		if (target.world instanceof ServerWorld)
		{
			Galaxia.PROXY.spawnParticle("spark", target.getPosX(), target.getPosYHeight(0.5), target.getPosZ(), 1, 0.1, 0);
		}
	}
}
