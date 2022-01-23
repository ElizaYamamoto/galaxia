package io.github.elizayami.galaxia.common.abstracts.items;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.HashSet;
import java.util.Set;

public class LargeToolItem extends ToolItem
{
	private static final Set<Material> WOODS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD);
	
	private static final Set<Material> STONES = Sets.newHashSet(Material.IRON, Material.ANVIL, Material.ROCK);
	
	private static final Set<Material> PLANTS = Sets.newHashSet(Material.ORGANIC, Material.SPONGE, Material.LEAVES);
	
	private static final Set<Material> SOILS = Sets.newHashSet(Material.CLAY, Material.EARTH, Material.SAND);

	private Set<Material> EFFECTIVE;
	
	public LargeToolItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Item.Properties apply)
	{
		super(attackDamageIn, attackSpeedIn, tier, new HashSet<>(), apply);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		super.fillItemGroup(group, items);
	}

	@Override
	public boolean canHarvestBlock(BlockState blockIn)
	{
		int i = this.getTier().getHarvestLevel();
		if (blockIn.getHarvestTool() == ToolType.PICKAXE)
		{
			return i >= blockIn.getHarvestLevel();
		}
		
		Material material = blockIn.getMaterial();
		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL
				|| blockIn.isIn(Blocks.SNOW) || blockIn.isIn(Blocks.SNOW_BLOCK);
	}
	
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		if (stack.getToolTypes().contains(ToolType.AXE))
		{
			EFFECTIVE.addAll(WOODS);
		}
		if (stack.getToolTypes().contains(ToolType.PICKAXE))
		{
			EFFECTIVE.addAll(STONES);
		}
		if (stack.getToolTypes().contains(ToolType.HOE))
		{
			EFFECTIVE.addAll(PLANTS);
		}
		if (stack.getToolTypes().contains(ToolType.SHOVEL))
		{
			EFFECTIVE.addAll(WOODS);
		}
		Material material = state.getMaterial();
		return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK && !EFFECTIVE.contains(material)
				? super.getDestroySpeed(stack, state)
				: this.efficiency;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		PlayerEntity player = context.getPlayer();
		ItemStack stack = context.getItem();

		BlockState state = world.getBlockState(pos);

		BlockState strippable = state.getToolModifiedState(world, pos, player, stack, ToolType.AXE);

		BlockState tillable = state.getToolModifiedState(world, pos, player, stack, ToolType.HOE);

		BlockState pathable = state.getToolModifiedState(world, pos, player, stack, ToolType.SHOVEL);

		boolean success = false;

		if (strippable != null && stack.getToolTypes().contains(ToolType.AXE))
		{
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.setBlockState(pos, strippable, 11);
			success = true;
		}
		else if (context.getFace() != Direction.DOWN && world.getBlockState(pos.up()).isAir(world, pos.up()))
		{
			if (tillable != null)
			{
				if (stack.getToolTypes().contains(ToolType.HOE))
				{
					world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!world.isRemote)
					{
						world.setBlockState(pos, tillable, 11);
						success = true;
					}
				}
			}
			else if (stack.getToolTypes().contains(ToolType.SHOVEL))
			{
				if (pathable != null)
				{
					world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

					if (!world.isRemote())
					{
						world.setBlockState(pos, pathable, 11);
						success = true;
					}
				}
			}
			if (success)
			{
				if (player != null)
				{
					stack.damageItem(1, player, entity ->
					{
						entity.sendBreakAnimation(context.getHand());
					});
				}
				return ActionResultType.func_233537_a_(world.isRemote);
			}
		}

		return ActionResultType.PASS;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, BlockState state, BlockPos pos,
			LivingEntity entity)
	{
		boolean retval = super.onBlockDestroyed(itemstack, world, state, pos, entity);

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (world instanceof World)
		{
			double sx = -1;
			double sy = -1;
			double sz = -1;
			if (entity.rotationPitch > 40 || entity.rotationPitch < -40)
			{
				for (int loopsX = 0; loopsX < 3; loopsX++)
				{
					sz = -1;
					for (int loopsZ = 0; loopsZ < 3; loopsZ++)
					{
						if (canHarvestBlock(world.getBlockState(new BlockPos(x + sx, y, z + sz))))
						{
							Block.spawnDrops(world.getBlockState(new BlockPos(x + sx, y, z + sz)), world,
									new BlockPos(x + sx, y, z + sz));
							world.destroyBlock(new BlockPos(x + sx, y, z + sz), false);
						}
						sz++;
					}
					sx++;
				}
			}
			else if (entity.getHorizontalFacing() == Direction.NORTH || entity.getHorizontalFacing() == Direction.SOUTH)
			{
				for (int loopsX = 0; loopsX < 3; loopsX++)
				{
					sy = -1;
					for (int loopsY = 0; loopsY < 3; loopsY++)
					{
						if (canHarvestBlock(world.getBlockState(new BlockPos(x + sx, y + sy, z))))
						{
							Block.spawnDrops(world.getBlockState(new BlockPos(x + sx, y + sy, z)), world,
									new BlockPos(x + sx, y + sy, z));
							world.destroyBlock(new BlockPos(x + sx, y + sy, z), false);
						}
						sy++;
					}
					sx++;
				}
			}
			else if (entity.getHorizontalFacing() == Direction.WEST || entity.getHorizontalFacing() == Direction.EAST)
			{
				for (int loopsZ = 0; loopsZ < 3; loopsZ++)
				{
					sy = -1;
					for (int loopsY = 0; loopsY < 3; loopsY++)
					{
						if (canHarvestBlock(world.getBlockState(new BlockPos(x, y + sy, z + sz))))
						{
							Block.spawnDrops(world.getBlockState(new BlockPos(x, y + sy, z + sz)), world,
									new BlockPos(x, y + sy, z + sz));
							world.destroyBlock(new BlockPos(x, y + sy, z + sz), false);
						}
						sy++;
					}
					sz++;
				}
			}
		}
		return retval;
	}
}