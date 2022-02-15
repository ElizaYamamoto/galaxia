package io.github.elizayami.galaxia;

import io.github.elizayami.galaxia.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration
{

	public static final RuleTest END_STONE = new BlockMatchRuleTest(Blocks.END_STONE);

	public static void generateOres(final BiomeLoadingEvent event)
	{
		if (!(event.getCategory().equals(Biome.Category.NETHER) || event.getCategory().equals(Biome.Category.THEEND)))
		{
			generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.METEOR.ore.get().getDefaultState(), 5, 1, 30, 10);
		}
		
		if (event.getCategory().equals(Biome.Category.NETHER))
		{
			generateOre(event.getGeneration(), new BlockMatchRuleTest(BlockInit.BOLT), BlockInit.BOLTRINE.ore.get().getDefaultState(), 5, 1, 30, 10);
		}

		if (event.getCategory().equals(Biome.Category.THEEND))
		{
			generateOre(event.getGeneration(), END_STONE, BlockInit.COMETSTEEL.ore.get().getDefaultState(), 2, 1, 255, 2);
			
			generateOre(event.getGeneration(), new BlockMatchRuleTest(Blocks.AIR), BlockInit.GALAXIUM_STAR2.getDefaultState(), 1, 1, 100, 1);
		}
	}

	private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state, int veinSize, int minHeight, int maxHeight, int amount)
	{
		settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
				Feature.ORE.withConfiguration(new OreFeatureConfig(fillerType, state, veinSize))
						.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
						.square().func_242731_b(amount));
	}
}
