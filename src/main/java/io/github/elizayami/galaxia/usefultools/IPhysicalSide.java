package io.github.elizayami.galaxia.usefultools;

import net.minecraftforge.eventbus.api.IEventBus;

public interface IPhysicalSide 
{
	public abstract void setup(IEventBus modEventBus, IEventBus forgeEventBus);
}
