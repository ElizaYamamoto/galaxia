package io.github.elizayami.galaxia.common.world.feature.blockplacer;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.ArrayList;
import java.util.List;

public class GalaxiaBlockPlacerTypes
{

	public static List<BlockPlacerType<?>> types = new ArrayList<>();

	public static final BlockPlacerType<DoubleBlockPlacer> DOUBLE_BLOCK = createBlockPlacer("simple_block_placer",
			new BlockPlacerType<>(DoubleBlockPlacer.CODEC));

	private static <P extends BlockPlacer> BlockPlacerType<P> createBlockPlacer(String id, BlockPlacerType<P> type)
	{
		type.setRegistryName(new ResourceLocation(Galaxia.MOD_ID, id));
		types.add(type);
		return type;
	}

	public static void init()
	{
	}
}
