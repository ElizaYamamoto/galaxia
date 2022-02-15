package io.github.elizayami.galaxia.common.entities;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.Galaxia;

import java.util.EnumSet;
import java.util.Random;

public class ArcEntity extends MonsterEntity implements IAnimatable
{
	private int exampleTimer;
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	private static final DataParameter<Byte> FAST = EntityDataManager.createKey(ArcEntity.class, DataSerializers.BYTE);

	public ArcEntity(EntityType<ArcEntity> entityType, World worldIn)
	{
		super(entityType, worldIn);

	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		if (event.isMoving())
		{
			event.getController().setAnimation(new AnimationBuilder().addAnimation("arc idle", true));
			return PlayState.CONTINUE;
		}
		else
			event.getController().setAnimation(new AnimationBuilder().addAnimation("a attack", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();
		this.goalSelector.addGoal(4, new ArcEntity.FireballAttackGoal(this));
	      this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
	      this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
	      this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	      this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
	      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	public static boolean canArcSpawn(EntityType<ArcEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn)
	{
		return worldIn.getLightSubtracted(pos, 0) > 8;
	}

	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	@Override
	public void registerControllers(AnimationData animationData)
	{
		animationData
				.addAnimationController(new AnimationController<ArcEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player)
	{
		return 10;
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}

	@Nullable
	public AgeableEntity createChild(ServerWorld serverWorld, AgeableEntity ageableEntity)
	{
		return null;
	}

	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(FAST, (byte) 0);
	}

	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_arc_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_arc_HURT;
	}

	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_arc_DEATH;
	}

	public float getBrightness()
	{
		return 1.0F;
	}

	public void livingTick()
	{
		if (!this.onGround && this.getMotion().y < 0.0D)
		{
			this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
		}

		if (this.world.isRemote)
		{
			if (this.rand.nextInt(24) == 0 && !this.isSilent())
			{
				this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D,
						SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(),
						this.rand.nextFloat() * 0.7F + 0.3F, false);
			}

			for (int i = 0; i < 2; ++i)
			{
	            Galaxia.PROXY.spawnParticle("spark", this.getPosXRandom(0.5D), this.getPosYRandom() + 0.5D, this.getPosZRandom(0.5D), 0, 0, 0);
			}
		}

		super.livingTick();
	}

	public boolean isWaterSensitive()
	{
		return true;
	}

	protected void updateAITasks()
	{
		--this.heightOffsetUpdateTime;
		if (this.heightOffsetUpdateTime <= 0)
		{
			this.heightOffsetUpdateTime = 100;
			this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
		}

		LivingEntity livingentity = this.getAttackTarget();
		if (livingentity != null && livingentity.getPosYEye() > this.getPosYEye() + (double) this.heightOffset
				&& this.canAttack(livingentity))
		{
			Vector3d vector3d = this.getMotion();
			this.setMotion(this.getMotion().add(0.0D, ((double) 0.3F - vector3d.y) * (double) 0.3F, 0.0D));
			this.isAirBorne = true;
		}

		super.updateAITasks();
	}

	public boolean onLivingFall(float distance, float damageMultiplier)
	{
		return false;
	}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire effect
	 * on rendering.
	 */
	public boolean isBurning()
	{
		return this.isCharged();
	}

	private boolean isCharged()
	{
		return (this.dataManager.get(FAST) & 1) != 0;
	}

	private void setOnFire(boolean onFire)
	{
		byte b0 = this.dataManager.get(FAST);
		if (onFire)
		{
			b0 = (byte) (b0 | 1);
		}
		else
		{
			b0 = (byte) (b0 & -2);
		}

		this.dataManager.set(FAST, b0);
	}

	static class FireballAttackGoal extends Goal
	{
		private final ArcEntity arc;
		private int attackStep;
		private int attackTime;
		private int firedRecentlyTimer;

		public FireballAttackGoal(ArcEntity arcIn)
		{
			this.arc = arcIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean shouldExecute()
		{
			LivingEntity livingentity = this.arc.getAttackTarget();
			return livingentity != null && livingentity.isAlive() && this.arc.canAttack(livingentity);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			this.attackStep = 0;
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		public void resetTask()
		{
			this.arc.setOnFire(false);
			this.firedRecentlyTimer = 0;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick()
		{
			--this.attackTime;
			LivingEntity livingentity = this.arc.getAttackTarget();
			if (livingentity != null)
			{
				boolean flag = this.arc.getEntitySenses().canSee(livingentity);
				if (flag)
				{
					this.firedRecentlyTimer = 0;
				}
				else
				{
					++this.firedRecentlyTimer;
				}

				double d0 = this.arc.getDistanceSq(livingentity);
				if (d0 < 4.0D)
				{
					if (!flag)
					{
						return;
					}

					if (this.attackTime <= 0)
					{
						this.attackTime = 20;
						this.arc.attackEntityAsMob(livingentity);
					}

					this.arc.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(),
							livingentity.getPosZ(), 1.0D);
				}
				else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag)
				{
					double d1 = livingentity.getPosX() - this.arc.getPosX();
					double d2 = livingentity.getPosYHeight(0.5D) - this.arc.getPosYHeight(0.5D);
					double d3 = livingentity.getPosZ() - this.arc.getPosZ();
					if (this.attackTime <= 0)
					{
						++this.attackStep;
						if (this.attackStep == 1)
						{
							this.attackTime = 60;
							this.arc.setOnFire(true);
						}
						else if (this.attackStep <= 4)
						{
							this.attackTime = 6;
						}
						else
						{
							this.attackTime = 100;
							this.attackStep = 0;
							this.arc.setOnFire(false);
						}

						if (this.attackStep > 1)
						{
							float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
							if (!this.arc.isSilent())
							{
								this.arc.world.playEvent((PlayerEntity) null, 1018, this.arc.getPosition(), 0);
							}

							for (int i = 0; i < 1; ++i)
							{
								SmallFireballEntity smallfireballentity = new SmallFireballEntity(this.arc.world,
										this.arc, d1 + this.arc.getRNG().nextGaussian() * (double) f, d2,
										d3 + this.arc.getRNG().nextGaussian() * (double) f);
								smallfireballentity.setPosition(smallfireballentity.getPosX(),
										this.arc.getPosYHeight(0.5D) + 0.5D, smallfireballentity.getPosZ());
								this.arc.world.addEntity(smallfireballentity);
							}
						}
					}

					this.arc.getLookController().setLookPositionWithEntity(livingentity, 10.0F, 10.0F);
				}
				else if (this.firedRecentlyTimer < 5)
				{
					this.arc.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(),
							livingentity.getPosZ(), 1.0D);
				}

				super.tick();
			}
		}

		private double getFollowDistance()
		{
			return this.arc.getAttributeValue(Attributes.FOLLOW_RANGE);
		}
	}
}
