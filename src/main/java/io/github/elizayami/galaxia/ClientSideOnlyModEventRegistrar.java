package io.github.elizayami.galaxia;

import net.minecraftforge.eventbus.api.IEventBus;
import io.github.elizayami.galaxia.core.init.ClientInit;

public class ClientSideOnlyModEventRegistrar
{
	private final IEventBus eventBus;

	public ClientSideOnlyModEventRegistrar(IEventBus eventBus)
	{
		this.eventBus = eventBus;
	}

	public void registerClientOnlyEvents()
	{
		eventBus.register(ClientInit.class);
	}
}
