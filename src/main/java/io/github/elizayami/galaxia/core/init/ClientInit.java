package io.github.elizayami.galaxia.core.init;

import io.github.elizayami.galaxia.client.renderer.StarRenderer;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.ContainerScreenDFF;
import io.github.elizayami.galaxia.common.block.soul_furnace.ContainerScreenSF;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientInit 
{
	@SubscribeEvent
	public static void onClientSetupEvent(FMLClientSetupEvent event) 
	{
		ScreenManager.registerFactory(FurnaceInit.containerTypeDFF, ContainerScreenDFF::new);
		ScreenManager.registerFactory(FurnaceInit.containerTypeSF, ContainerScreenSF::new);

        ClientRegistry.bindTileEntityRenderer(BlockInit.galaxium_star_tile, o -> new StarRenderer(o, BlockInit.GALAXIUM_STAR, true));
	}

}
