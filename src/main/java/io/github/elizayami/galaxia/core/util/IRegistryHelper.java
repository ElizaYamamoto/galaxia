package io.github.elizayami.galaxia.core.util;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IRegistryHelper<T extends IForgeRegistryEntry<T>>
{
	DeferredRegister<T> getDeferredRegister();

	void register(IEventBus eventBus);
}