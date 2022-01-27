package io.github.elizayami.galaxia.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.data.client.ModBlockStates;
import io.github.elizayami.galaxia.common.data.client.ModItemModels;

@Mod.EventBusSubscriber(modid = Galaxia.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModData 
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator dataGenerator = event.getGenerator();
		
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		
		if (event.includeClient())
		{
			dataGenerator.addProvider(new ModItemModels(dataGenerator, existingFileHelper));
			
			dataGenerator.addProvider(new ModBlockStates(dataGenerator, existingFileHelper));
		}
		if (event.includeServer())
		{
			ModBlockTagsProvider blockTags = new ModBlockTagsProvider(dataGenerator, existingFileHelper);
			dataGenerator.addProvider(blockTags);
			dataGenerator.addProvider(new ModItemTagsProvider(dataGenerator, blockTags, existingFileHelper));
			
			dataGenerator.addProvider(new ModRecipes(dataGenerator));
			
			dataGenerator.addProvider(new ModLootTableProvider(dataGenerator));
		}
	}
}
