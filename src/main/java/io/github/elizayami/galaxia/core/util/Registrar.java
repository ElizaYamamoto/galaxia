package io.github.elizayami.galaxia.core.util;

import com.google.common.collect.Maps;

import io.github.elizayami.galaxia.core.init.RegistryHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Consumer;

public class Registrar
{
	private final Map<IForgeRegistry<?>, IRegistryHelper<?>> subHelpers = Maps.newHashMap();
	protected final String modId;

	public Registrar(String modId)
	{
		this.modId = modId;
		this.setHelper(ForgeRegistries.ITEMS, new RegistryHelper(this));
	}

	public static Registrar create(String modId, Consumer<Registrar> consumer)
	{
		Registrar helper = new Registrar(modId);
		consumer.accept(helper);
		return helper;
	}

	public String getModId()
	{
		return this.modId;
	}

	public ResourceLocation prefix(String name)
	{
		return new ResourceLocation(this.modId, name);
	}

	public <K extends IForgeRegistryEntry<K>> void setHelper(IForgeRegistry<K> registry,
			IRegistryHelper<K> subHelper)
	{
		this.subHelpers.put(registry, subHelper);
	}

	@Nonnull
	public <T extends IForgeRegistryEntry<T>, S extends IRegistryHelper<T>> S getHelper(IForgeRegistry<T> registry)
	{
		S subHelper = (S) this.subHelpers.get(registry);
		if (subHelper == null)
		{
			throw new NullPointerException(
					"Missing Boat Register Helper");
		}
		return subHelper;
	}

	@Nonnull
	public <T extends AbstractRegistryHelper<Item>> T getItemHelper()
	{
		return this.getHelper(ForgeRegistries.ITEMS);
	}

	public void register(IEventBus eventBus)
	{
		this.subHelpers.values().forEach(helper ->
		{
			helper.register(eventBus);
		});
	}
}