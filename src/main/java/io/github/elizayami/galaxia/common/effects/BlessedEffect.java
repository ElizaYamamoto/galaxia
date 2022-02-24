package io.github.elizayami.galaxia.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.server.ServerWorld;

public class BlessedEffect extends Effect
{
	public BlessedEffect()
	{
		super(EffectType.BENEFICIAL, 0x97C1C5);
	}

	public boolean isReady(int tick, int level)
	{
		return tick > 0 && tick % 20 == 0;
	}

	public static final DamageSource Smitten(LivingEntity lastAttacker)
	{
		if (lastAttacker != null)
		{
			return new EntityDamageSource("smitten", lastAttacker).setDamageBypassesArmor().setMagicDamage();
		}
		else
		{
			return new DamageSource("smitten").setDamageBypassesArmor().setMagicDamage();
		}
	}

	@Override
	public void performEffect(LivingEntity target, int level)
	{
		DamageSource source = Smitten(target.getLastAttackedEntity());

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
			((ServerWorld) target.world).spawnParticle(ParticleTypes.DAMAGE_INDICATOR, target.getPosX(),
					target.getPosYHeight(0.5), target.getPosZ(), 1, 0.1, 0, 0.1, 0.2);
		}
	}
}
