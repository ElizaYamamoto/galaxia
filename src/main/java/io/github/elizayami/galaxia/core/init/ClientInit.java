package io.github.elizayami.galaxia.core.init;

import io.github.elizayami.galaxia.common.block.dragonfire_furnace.ContainerScreenDFF;
import io.github.elizayami.galaxia.common.block.soul_furnace.ContainerScreenSF;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientInit 
{
	@SubscribeEvent
	public static void onClientSetupEvent(FMLClientSetupEvent event) 
	{
		ScreenManager.registerFactory(TileEntityInit.containerTypeDFF, ContainerScreenDFF::new);
		ScreenManager.registerFactory(TileEntityInit.containerTypeSF, ContainerScreenSF::new);
	}

}
