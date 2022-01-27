package io.github.elizayami.galaxia.core.util;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class AbstractRegistryHelper<T extends IForgeRegistryEntry<T>> implements IRegistryHelper<T>
{
	protected final DeferredRegister<T> deferredRegister;
	protected final Registrar parent;

	public AbstractRegistryHelper(Registrar parent, DeferredRegister<T> deferredRegister) {
		this.parent = parent;
		this.deferredRegister = deferredRegister;
	}
	
	@Override
	public void register(IEventBus eventBus) {
		this.getDeferredRegister().register(eventBus);
	}
	
	@Override
	public DeferredRegister<T> getDeferredRegister() {
		return this.deferredRegister;
	}
}
