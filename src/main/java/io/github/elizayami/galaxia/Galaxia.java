package io.github.elizayami.galaxia;

import org.apache.logging.log4j.Logger;

import io.github.elizayami.galaxia.client.PhysicalClientSide;
import io.github.elizayami.galaxia.common.PhysicalServerSide;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import io.github.elizayami.galaxia.core.init.TileEntityInit;
import io.github.elizayami.galaxia.usefultools.IPhysicalSide;

import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Mod("galaxia")
public class Galaxia 
{
	public static final String MOD_ID = "galaxia";
	
    public static final Logger LOGGER = LogManager.getLogger();
    
	public static final IPhysicalSide SIDE = 
			DistExecutor.safeRunForDist(() -> PhysicalClientSide::new, () -> PhysicalServerSide::new);

	public static final ItemGroup galaxiaGroup = new GalaxiaGroup("galaxiatab");

    public Galaxia() 
    {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    	
    	SIDE.setup(bus, forgeBus);
    	
		bus.addListener(this::setup);

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
	    bus.register(TileEntityInit.class);
		
	    final ClientSideOnlyModEventRegistrar clientSideOnlyModEventRegistrar = new ClientSideOnlyModEventRegistrar(bus);
	    DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientSideOnlyModEventRegistrar::registerClientOnlyEvents);
		
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
	}

	private void setup(final FMLCommonSetupEvent event) {

	}
	
	public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) 
    {
        entry.setRegistryName(new ResourceLocation(Galaxia.MOD_ID, registryKey));
        registry.register(entry);
        return entry;
    }


	public static boolean isClient() 
	{
		return FMLLoader.getDist() == Dist.CLIENT;
	}

	public static ResourceLocation makeID(String path) 
	{
		return new ResourceLocation(MOD_ID, path);
	}

	public static class GalaxiaGroup extends ItemGroup 
	{

		public GalaxiaGroup(String label) 
		{
			super(label);
		}

		@Override
		public ItemStack createIcon() 
		{
			return new ItemStack(ItemInit.STARDUST.get());
		}
	}
}
