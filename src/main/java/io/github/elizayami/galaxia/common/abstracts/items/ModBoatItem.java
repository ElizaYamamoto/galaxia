package io.github.elizayami.galaxia.common.abstracts.items;

import io.github.elizayami.galaxia.common.abstracts.entity.ModBoatEntity;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class ModBoatItem extends Item 
{
	private static final Predicate<Entity> COLLISION_PREDICATE = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
	private final String type;

	public ModBoatItem(String type, Item.Properties properties) {
		super(properties);
		this.type = type;
		DispenserBlock.registerDispenseBehavior(this, new DispenserBoatBehavior(type));
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	      ItemStack itemstack = playerIn.getHeldItem(handIn);
	      RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
	      if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
	         return ActionResult.resultPass(itemstack);
	      } else {
	         Vector3d vector3d = playerIn.getLook(1.0F);
	         double d0 = 5.0D;
	         List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vector3d.scale(5.0D)).grow(1.0D), COLLISION_PREDICATE);
	         if (!list.isEmpty()) {
	            Vector3d vector3d1 = playerIn.getEyePosition(1.0F);

	            for(Entity entity : list) {
	               AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow((double)entity.getCollisionBorderSize());
	               if (axisalignedbb.contains(vector3d1)) {
	                  return ActionResult.resultPass(itemstack);
	               }
	            }
	         }

	         if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
	            ModBoatEntity boatentity = new ModBoatEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
	            boatentity.setBoat(this.type);
	            boatentity.rotationYaw = playerIn.rotationYaw;
	            if (!worldIn.hasNoCollisions(boatentity, boatentity.getBoundingBox().grow(-0.1D))) {
	               return ActionResult.resultFail(itemstack);
	            } else {
	               if (!worldIn.isRemote) {
	                  worldIn.addEntity(boatentity);
	                  if (!playerIn.abilities.isCreativeMode) {
	                     itemstack.shrink(1);
	                  }
	               }

	               playerIn.addStat(Stats.ITEM_USED.get(this));
	               return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	            }
	         } 
	         else 
	         {
	            return ActionResult.resultPass(itemstack);
	         }
	      }
		}

	static class DispenserBoatBehavior extends DefaultDispenseItemBehavior {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
		private final String type;

		public DispenserBoatBehavior(String type) {
			this.type = type;
		}

		@SuppressWarnings("deprecation")
		public ItemStack execute(IBlockSource iBlockSource, ItemStack stack) {
			Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
			World world = iBlockSource.getWorld();
			double x = iBlockSource.getX() + (double) ((float) direction.getXOffset() * 1.125f);
			double y = iBlockSource.getY() + (double) ((float) direction.getYOffset() * 1.125f);
			double z = iBlockSource.getZ() + (double) ((float) direction.getZOffset() * 1.125f);
			BlockPos pos = iBlockSource.getBlockPos().offset(direction);
			double adjustY;
			if (world.getFluidState(pos).isTagged(FluidTags.WATER)) {
				adjustY = 1d;
			} else {
				if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.down()).isTagged(FluidTags.WATER)) {
					return this.defaultDispenseItemBehavior.dispense(iBlockSource, stack);
				}
				adjustY = 0d;
			}
			ModBoatEntity boat = new ModBoatEntity(world, x, y + adjustY, z);
			boat.setBoat(this.type);
			boat.rotationYaw = direction.getHorizontalAngle();
			world.addEntity(boat);
			stack.shrink(1);
			return stack;
		}


		private void playSound(IBlockSource iBlockSource, SoundEvent soundEvent) {
			Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
			double x = iBlockSource.getX() + (double) ((float) direction.getXOffset() * 1.125f);
			double y = iBlockSource.getY() + (double) ((float) direction.getYOffset() * 1.125f);
			double z = iBlockSource.getZ() + (double) ((float) direction.getZOffset() * 1.125f);
			iBlockSource.getWorld().playSound((PlayerEntity) null, x, y, z, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 0.5F,
					iBlockSource.getWorld().rand.nextFloat() * 0.1F + 0.9F);
		}
	}
}