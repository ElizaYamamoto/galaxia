package io.github.elizayami.galaxia.core.init;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.blocks.GalaxiaSignBlock;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.ContainerDFF;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.Dragonfire_Furnace;
import io.github.elizayami.galaxia.common.block.soul_furnace.ContainerSF;
import io.github.elizayami.galaxia.common.block.soul_furnace.Soul_Furnace;
import io.github.elizayami.galaxia.common.tileentity.GalaxiaBarrelTileEntity;
import io.github.elizayami.galaxia.common.tileentity.GalaxiaChestTileEntity;
import io.github.elizayami.galaxia.common.tileentity.GalaxiaSignTileEntity;
import io.github.elizayami.galaxia.common.tileentity.TileEntityDragonfireFurnace;
import io.github.elizayami.galaxia.common.tileentity.TileEntitySoulFurnace;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit
{
	public static Block blockSF;
	public static Block blockDFF;
	public static BlockItem itemBlockDFF;
	public static BlockItem itemBlockSF;

	public static TileEntityType<TileEntityDragonfireFurnace> tileEntityTypeDFF;
	public static ContainerType<ContainerDFF> containerTypeDFF;

	public static TileEntityType<TileEntitySoulFurnace> tileEntityTypeSF;
	public static ContainerType<ContainerSF> containerTypeSF;
	
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
			DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Galaxia.MOD_ID);

	@SubscribeEvent
	public static void onBlocksRegistration(final RegistryEvent.Register<Block> blockRegisterEvent)
	{
		blockDFF = new Dragonfire_Furnace().setRegistryName("dragonfire_furnace");
		blockRegisterEvent.getRegistry().register(blockDFF);

		blockSF = new Soul_Furnace().setRegistryName("soul_furnace");
		blockRegisterEvent.getRegistry().register(blockSF);
	}

	@SubscribeEvent
	public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent)
	{
		final int MAXIMUM_STACK_SIZE = 64;

		Item.Properties itemSimpleProperties = new Item.Properties().maxStackSize(MAXIMUM_STACK_SIZE)
				.group(Galaxia.galaxiaGroup);
		itemBlockDFF = new BlockItem(blockDFF, itemSimpleProperties);
		itemBlockDFF.setRegistryName(blockDFF.getRegistryName());
		itemRegisterEvent.getRegistry().register(itemBlockDFF);
		itemBlockSF = new BlockItem(blockSF, itemSimpleProperties);
		itemBlockSF.setRegistryName(blockSF.getRegistryName());
		itemRegisterEvent.getRegistry().register(itemBlockSF);
	}

	@SubscribeEvent
	public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event)
	{
		tileEntityTypeDFF = TileEntityType.Builder.create(TileEntityDragonfireFurnace::new, blockDFF).build(null);
		tileEntityTypeDFF.setRegistryName("galaxia:dragonfire_furnace_registry_name");
		event.getRegistry().register(tileEntityTypeDFF);

		tileEntityTypeSF = TileEntityType.Builder.create(TileEntitySoulFurnace::new, blockSF).build(null);
		tileEntityTypeSF.setRegistryName("galaxia:soul_furnace_registry_name");
		event.getRegistry().register(tileEntityTypeSF);
	}

	@SubscribeEvent
	public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event)
	{
		containerTypeDFF = IForgeContainerType.create(ContainerDFF::createContainerClientSide);
		containerTypeDFF.setRegistryName("dragonfire_furnace_container");
		event.getRegistry().register(containerTypeDFF);

		containerTypeSF = IForgeContainerType.create(ContainerSF::createContainerClientSide);
		containerTypeSF.setRegistryName("soul_furnace_container");
		event.getRegistry().register(containerTypeSF);
	}

	public static final RegistryObject<TileEntityType<GalaxiaChestTileEntity>> CHEST = TILE_ENTITY_TYPES.register("chest",
			() -> TileEntityType.Builder.create(GalaxiaChestTileEntity::new, getChests()).build(null));

	public static final RegistryObject<TileEntityType<GalaxiaSignTileEntity>> SIGN = TILE_ENTITY_TYPES.register("sign",
			() -> TileEntityType.Builder.create(GalaxiaSignTileEntity::new, getSigns()).build(null));

	public static final RegistryObject<TileEntityType<GalaxiaBarrelTileEntity>> BARREL = TILE_ENTITY_TYPES.register(
			"barrel", () -> TileEntityType.Builder.create(GalaxiaBarrelTileEntity::new, getBarrels()).build(null));

	static Block[] getChests()
	{
		List<Block> result = Lists.newArrayList();
		ItemInit.ITEMS.getEntries().forEach((item) ->
		{
			if (item.get() instanceof BlockItem)
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof ChestBlock)
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[]
		{});
	}

	static Block[] getSigns()
	{
		List<Block> result = Lists.newArrayList();
		ItemInit.ITEMS.getEntries().forEach((item) ->
		{
			if (item.get() instanceof BlockItem)
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof GalaxiaSignBlock)
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[]
		{});
	}

	static Block[] getBarrels()
	{
		List<Block> result = Lists.newArrayList();
		ItemInit.ITEMS.getEntries().forEach((item) ->
		{
			if (item.get() instanceof BlockItem)
			{
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof BarrelBlock)
				{
					result.add(block);
				}
			}
		});
		return result.toArray(new Block[]
		{});
	}

}
