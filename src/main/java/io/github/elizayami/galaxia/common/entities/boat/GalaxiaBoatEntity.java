package io.github.elizayami.galaxia.common.entities.boat;

import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.EntityInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class GalaxiaBoatEntity extends BoatEntity
{
	private static final DataParameter<Integer> Galaxia_BOAT_TYPE = EntityDataManager.createKey(GalaxiaBoatEntity.class,
			DataSerializers.VARINT);

	public GalaxiaBoatEntity(World worldIn, double x, double y, double z)
	{
		this(EntityInit.BOAT, worldIn);
		this.setPosition(x, y, z);
		this.setMotion(Vector3d.ZERO);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	public GalaxiaBoatEntity(EntityType<? extends BoatEntity> boatEntityType, World worldType)
	{
		super(boatEntityType, worldType);
	}

	public GalaxiaBoatEntity(FMLPlayMessages.SpawnEntity packet, World world)
	{
		super(EntityInit.BOAT, world);
	}

	@Override
	public Item getItemBoat()
	{
		switch (this.getGalaxiaBoatType())
		{
			default:
				return ItemInit.SHADOWSPIKE_BOAT.get();
			case GROUNDSTALK:
				return ItemInit.SHADOWSPIKE_BOAT.get();
			case SEAWOOD:
				return ItemInit.SHADOWSPIKE_BOAT.get();
			case SCORCHWOOD:
				return ItemInit.SHADOWSPIKE_BOAT.get();
			case GHOSTWOOD:
				return ItemInit.SHADOWSPIKE_BOAT.get();
			case GROVEWOOD:
				return ItemInit.SHADOWSPIKE_BOAT.get();
		}
	}

	public Block getPlanks()
	{
		switch (this.getGalaxiaBoatType())
		{
			default:
				return BlockInit.SHADOWSPIKE.planks.get();
			case GROUNDSTALK:
				return BlockInit.SHADOWSPIKE.planks.get();
			case SEAWOOD:
				return BlockInit.SHADOWSPIKE.planks.get();
			case SCORCHWOOD:
				return BlockInit.SHADOWSPIKE.planks.get();
			case GHOSTWOOD:
				return BlockInit.SHADOWSPIKE.planks.get();
			case GROVEWOOD:
				return BlockInit.SHADOWSPIKE.planks.get();
		}
	}

	public GalaxiaType getGalaxiaBoatType()
	{
		return GalaxiaType.byId(this.dataManager.get(Galaxia_BOAT_TYPE));
	}

	public void setGalaxiaBoatType(GalaxiaType boatGalaxiaType)
	{
		this.dataManager.set(Galaxia_BOAT_TYPE, boatGalaxiaType.ordinal());
	}

	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(Galaxia_BOAT_TYPE, GalaxiaType.SHADOWSPIKE.ordinal());
	}

	@Override
	protected void writeAdditional(CompoundNBT compound)
	{
		compound.putString("GalaxiaType", this.getGalaxiaBoatType().getName());
	}

	@Override
	protected void readAdditional(CompoundNBT compound)
	{
		if (compound.contains("GalaxiaType", 8))
		{
			this.setGalaxiaBoatType(GalaxiaType.getTypeFromString(compound.getString("GalaxiaType")));
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void performHurtAnimation()
	{
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos)
	{
		this.lastYd = this.getMotion().y;
		if (!this.isPassenger())
		{
			if (onGroundIn)
			{
				if (this.fallDistance > 3.0F)
				{
					if (this.status != BoatEntity.Status.ON_LAND)
					{
						this.fallDistance = 0.0F;
						return;
					}

					this.onLivingFall(this.fallDistance, 1.0F);
					if (!this.world.isRemote && !this.removed)
					{
						this.remove();
						if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
						{
							for (int i = 0; i < 3; ++i)
							{
								this.entityDropItem(this.getBoatType().asPlank());
							}

							for (int j = 0; j < 2; ++j)
							{
								this.entityDropItem(Items.STICK);
							}
						}
					}
				}

				this.fallDistance = 0.0F;
			}
			else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && y < 0.0D)
			{
				this.fallDistance = (float) ((double) this.fallDistance - y);
			}

		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isInvulnerableTo(source))
		{
			return false;
		}
		else if (!this.world.isRemote && !this.removed)
		{
			if (source instanceof IndirectEntityDamageSource && source.getTrueSource() != null
					&& this.isPassenger(source.getTrueSource()))
			{
				return false;
			}
			else
			{
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
				this.markVelocityChanged();
				boolean flag = source.getTrueSource() instanceof PlayerEntity
						&& ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode;
				if (flag || this.getDamageTaken() > 40.0F)
				{
					if (!flag && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
					{
						this.entityDropItem(this.getItemBoat());
					}

					this.remove();
				}

				return true;
			}
		}
		else
		{
			return true;
		}
	}

	@Override
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public enum GalaxiaType
	{
		SHADOWSPIKE("shadowspike"), GROUNDSTALK("groundstalk"), SEAWOOD("seawood"), SCORCHWOOD("scorchwood"),
		GHOSTWOOD("ghostwood"), GROVEWOOD("grovewood");

		private final String name;

		GalaxiaType(String string)
		{
			this.name = string;
		}

		/**
		 * Get a boat type by it's enum ordinal
		 */
		public static GalaxiaType byId(int id)
		{
			GalaxiaType[] aGalaxiaBoatEntity$GalaxiaType = values();
			if (id < 0 || id >= aGalaxiaBoatEntity$GalaxiaType.length)
			{
				id = 0;
			}

			return aGalaxiaBoatEntity$GalaxiaType[id];
		}

		public static GalaxiaType getTypeFromString(String nameIn)
		{
			GalaxiaType[] aGalaxiaBoatEntity$GalaxiaType = values();

			for (GalaxiaType GalaxiaType : aGalaxiaBoatEntity$GalaxiaType)
			{
				if (GalaxiaType.getName().equals(nameIn))
				{
					return GalaxiaType;
				}
			}

			return aGalaxiaBoatEntity$GalaxiaType[0];
		}

		public String getName()
		{
			return this.name;
		}

		public String toString()
		{
			return this.name;
		}
	}
}
