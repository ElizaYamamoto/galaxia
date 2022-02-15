package io.github.elizayami.galaxia;

import org.apache.logging.log4j.Logger;

import com.ibm.icu.impl.Pair;

import io.github.elizayami.galaxia.client.ClientProxy;
import io.github.elizayami.galaxia.client.PhysicalClientSide;
import io.github.elizayami.galaxia.common.CommonProxy;
import io.github.elizayami.galaxia.common.PhysicalServerSide;
import io.github.elizayami.galaxia.common.biome.GalaxiaBiomes;
import io.github.elizayami.galaxia.common.biome.GalaxiaSurfaceBuilders;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.BoatInit;
import io.github.elizayami.galaxia.core.init.TileEntityInit;
import io.github.elizayami.galaxia.core.init.ItemInit;
import io.github.elizayami.galaxia.core.init.WoodTileEntityInit;
import io.github.elizayami.galaxia.core.init.enchantments.EnchantmentInit;
import io.github.elizayami.galaxia.usefultools.IPhysicalSide;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import software.bernie.geckolib3.GeckoLib;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@Mod("galaxia")
public class Galaxia
{
	public static final String MOD_ID = "galaxia";

	public static final Logger LOGGER = LogManager.getLogger();

	public static final IPhysicalSide SIDE = DistExecutor.safeRunForDist(() -> PhysicalClientSide::new,
			() -> PhysicalServerSide::new);

	public static ResourceLocation createID(String id)
	{
		return new ResourceLocation(MOD_ID, id);
	}

	public static Map<PlayerEntity, Pair<Direction, BlockPos>> clickedBlockFaces = new HashMap<PlayerEntity, Pair<Direction, BlockPos>>();

	public Galaxia()
	{
		GeckoLib.initialize();

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		SIDE.setup(bus, forgeBus);

		bus.addListener(this::setup);

		bus.register(BoatInit.class);

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EnchantmentInit.ENCHANTMENTS.register(bus);

		WoodTileEntityInit.TILE_ENTITY_TYPES.register(bus);

		bus.register(TileEntityInit.class);

		PROXY.init();

		final ClientSideOnlyModEventRegistrar clientSideOnlyModEventRegistrar = new ClientSideOnlyModEventRegistrar(bus);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientSideOnlyModEventRegistrar::registerClientOnlyEvents);

		GalaxiaBiomes.initialise(bus);
		GalaxiaSurfaceBuilders.initialise(bus);

		forgeBus.register(this);
		forgeBus.addListener(EventPriority.HIGH, OreGeneration::generateOres);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			PROXY.setup();
		});
	}

	public static boolean isClient()
	{
		return FMLLoader.getDist() == Dist.CLIENT;
	}

	public static ResourceLocation makeID(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

	public static final SimpleChannel NETWORK_WRAPPER;

	private static final String PROTOCOL_VERSION = Integer.toString(1);

	public static CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	private static int packetsRegistered = 0;
	static
	{
		NetworkRegistry.ChannelBuilder channel = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation("galaxia", "main_channel"));
		String version = PROTOCOL_VERSION;
		version.getClass();
		channel = channel.clientAcceptedVersions(version::equals);
		version = PROTOCOL_VERSION;
		version.getClass();
		NETWORK_WRAPPER = channel.serverAcceptedVersions(version::equals).networkProtocolVersion(() ->
		{
			return PROTOCOL_VERSION;
		}).simpleChannel();
	}

	public void postInit()
	{
	}

	private void setupClient(FMLClientSetupEvent event)
	{
		PROXY.setupClient();
	}

	public static final ItemGroup galaxiaGroup = new GalaxiaGroup("galaxiatab");

	public static class GalaxiaGroup extends ItemGroup
	{

		public GalaxiaGroup(String label)
		{
			super(label);
		}

		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ItemInit.GALAXIUM_STAR.get());
		}
	}
}
