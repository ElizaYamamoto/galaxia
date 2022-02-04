package io.github.elizayami.galaxia.common.block.tool_fuser;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

import io.github.elizayami.galaxia.common.tileentity.TileEntityDragonfireFurnace;

public class ToolFuser extends ContainerBlock
{
	public ToolFuser()
	{
		super(Block.Properties.create(Material.ROCK).setLightLevel(ToolFuser::getLightValue));
		BlockState defaultBlockState = this.stateContainer.getBaseState().with(FACING, Direction.NORTH);

		this.setDefaultState(defaultBlockState);
	}

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
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

	public static int getLightValue(BlockState state)
	{
		return 8;
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
		return new TileEntityDragonfireFurnace();
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
			if (tileentity instanceof TileEntityDragonfireFurnace)
			{
				TileEntityDragonfireFurnace tileEntityFurnace = (TileEntityDragonfireFurnace) tileentity;
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
