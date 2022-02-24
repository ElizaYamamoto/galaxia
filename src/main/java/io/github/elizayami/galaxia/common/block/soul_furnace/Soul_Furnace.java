package io.github.elizayami.galaxia.common.block.soul_furnace;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.common.tileentity.TileEntitySoulFurnace;

public class Soul_Furnace extends ContainerBlock
{
	public Soul_Furnace()
	{
		super(Block.Properties.create(Material.ROCK).setLightLevel(Soul_Furnace::getLightValue));
		BlockState getDefaultState = this.stateContainer.getBaseState().with(NUMBER_BURNING, 0).
				getBlockState().with(FACING, Direction.NORTH).
				getBlockState().with(IS_BURNING, false);

		this.setDefaultState(getDefaultState);
	}

	   public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	final static int MAX_BURNING = 2;
	public static final IntegerProperty NUMBER_BURNING = IntegerProperty.create("number_burning", 0, MAX_BURNING);
	
	public static final BooleanProperty IS_BURNING = BooleanProperty.create("is_burning");

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(NUMBER_BURNING);
		builder.add(IS_BURNING);
		builder.add(FACING);
	}

	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_)
	{
		p_206840_1_.add(FACING);
	}

	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_)
	{
		return this.getDefaultState().with(FACING, p_196258_1_.getPlacementHorizontalFacing().getOpposite());
	}

	private static final int ALL_SIDES_LIGHT_VALUE = 12;
	private static final int ONE_SIDE_LIGHT_VALUE = 8;

	public static int getLightValue(BlockState state)
	{
		int lightValue = 0;
		Integer burningSidesCount = state.get(NUMBER_BURNING);

		if (burningSidesCount == 0)
		{
			lightValue = 0;
		} else
		{
			lightValue = ONE_SIDE_LIGHT_VALUE
					+ (ALL_SIDES_LIGHT_VALUE - ONE_SIDE_LIGHT_VALUE) * burningSidesCount / (MAX_BURNING - 1);
		}
		lightValue = MathHelper.clamp(lightValue, 0, ALL_SIDES_LIGHT_VALUE);
		return lightValue;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return createNewTileEntity(world);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new TileEntitySoulFurnace();
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand hand, BlockRayTraceResult rayTraceResult)
	{
		if (worldIn.isRemote)
			return ActionResultType.SUCCESS;
		INamedContainerProvider namedContainerProvider = this.getContainer(state, worldIn, pos);
		if (namedContainerProvider != null)
		{
			if (!(player instanceof ServerPlayerEntity))
				return ActionResultType.FAIL;
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
			NetworkHooks.openGui(serverPlayerEntity, namedContainerProvider, (packetBuffer) ->
			{
			});
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileentity = world.getTileEntity(blockPos);
			if (tileentity instanceof TileEntitySoulFurnace)
			{
				TileEntitySoulFurnace tileEntityFurnace = (TileEntitySoulFurnace) tileentity;
				tileEntityFurnace.dropAllContents(world, blockPos);
			}

			super.onReplaced(state, world, blockPos, newState, isMoving);
		}
	}

	@Override
	public boolean hasComparatorInputOverride(BlockState state)
	{
		return false;
	}

	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos)
	{
		return 0;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

}
