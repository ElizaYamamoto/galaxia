package io.github.elizayami.galaxia.common.block.galaxium_star;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import java.util.function.Supplier;

/**
 * Created 7/10/2020 by SuperMartijn642
 */
public class Galaxium_Star extends Block implements IWaterLoggable {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    
    public static final VoxelShape Shape = VoxelShapes.create(5 / 16d, 5 / 16d, 5 / 16d, 11 / 16d, 11 / 16d, 11 / 16d);

    private final VoxelShape shape;
    private final Supplier<? extends TileEntity> tileProvider;

    public Galaxium_Star(String registryName, VoxelShape shape, Supplier<? extends TileEntity> tileProvider)
    {
        super(Properties.create(
        		Material.ROCK, 
        		MaterialColor.PURPLE).
				sound(SoundType.GLASS).
				harvestLevel(6).
				hardnessAndResistance(10f, 10f).
				setLightLevel(s -> 15).
				setNeedsPostProcessing((bs, br, bp) -> true).
				setEmmisiveRendering((bs, br, bp) -> true));
        
        this.setRegistryName(registryName);
        this.shape = shape;
        this.tileProvider = tileProvider;

        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return this.shape;
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return this.tileProvider.get();
    }
    
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
	
    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving){
        TileEntity tile = worldIn.getTileEntity(pos);
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving){
        TileEntity tile = worldIn.getTileEntity(pos);
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) 
    {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;
		return this.getDefaultState().with(WATERLOGGED, flag);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos,
			BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
}
