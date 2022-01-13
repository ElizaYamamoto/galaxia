package io.github.elizayami.galaxia.core.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class FuelRegistry
{
	public static final FuelRegistry instance = new FuelRegistry();

	private FuelRegistry()
	{
	};
	
	@SubscribeEvent
	public void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event)
	{
		ItemStack fuel = event.getItemStack();
		int BURN_TIME_SECONDS = 1200;
		final int TICKS_PER_SECOND = 20;
		if (fuel.getItem() == ItemInit.SOAL.get())
		{
			BURN_TIME_SECONDS = 1200;
			event.setBurnTime(BURN_TIME_SECONDS * TICKS_PER_SECOND);
		}
		if (fuel.getItem() == ItemInit.SOAL.get())
		{
			BURN_TIME_SECONDS = 10800;
			event.setBurnTime(BURN_TIME_SECONDS * TICKS_PER_SECOND);
		}
	}
}
