package io.github.elizayami.galaxia.mixin;

import io.github.elizayami.galaxia.common.block.StaticBlock;
import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public abstract class MixinAbstractFireBlock
{

	@Inject(at = @At("HEAD"), method = "getState", cancellable = true)
	private static void addStatic(IBlockReader reader, BlockPos pos, CallbackInfoReturnable<BlockState> cir)
	{
		BlockPos blockpos = pos.down();
		BlockState blockstate = reader.getBlockState(blockpos);
		if (StaticBlock.shouldLightStatic(blockstate.getBlock()))
		{
			cir.cancel();
			cir.setReturnValue(BlockInit.STATIC.get().getDefaultState());
		}
	}
}
