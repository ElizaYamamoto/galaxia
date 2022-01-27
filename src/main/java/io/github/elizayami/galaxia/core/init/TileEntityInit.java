package io.github.elizayami.galaxia.core.init;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.ContainerDFF;
import io.github.elizayami.galaxia.common.block.dragonfire_furnace.Dragonfire_Furnace;
import io.github.elizayami.galaxia.common.block.soul_furnace.ContainerSF;
import io.github.elizayami.galaxia.common.block.soul_furnace.Soul_Furnace;
import io.github.elizayami.galaxia.common.block.tool_fuser.ContainerTF;
import io.github.elizayami.galaxia.common.block.tool_fuser.Tool_Fuser;
import io.github.elizayami.galaxia.common.tileentity.TileEntityDragonfireFurnace;
import io.github.elizayami.galaxia.common.tileentity.TileEntitySoulFurnace;
import io.github.elizayami.galaxia.common.tileentity.TileEntityToolFuser;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TileEntityInit
{
	public static Block blockSF;
	public static Block blockDFF;
	public static Block blockTF;

	public static BlockItem itemBlockSF;
	public static BlockItem itemBlockDFF;
	public static BlockItem itemBlockTF;
	
	public static TileEntityType<TileEntitySoulFurnace> tileEntityTypeSF;
	public static ContainerType<ContainerSF> containerTypeSF;
	
	public static TileEntityType<TileEntityDragonfireFurnace> tileEntityTypeDFF;
	public static ContainerType<ContainerDFF> containerTypeDFF;

	public static TileEntityType<TileEntityToolFuser> tileEntityTypeTF;
	public static ContainerType<ContainerTF> containerTypeTF;
	
	@SubscribeEvent
	public static void onBlocksRegistration(final RegistryEvent.Register<Block> blockRegisterEvent)
	{
		blockSF = new Soul_Furnace().setRegistryName("soul_furnace");
		blockRegisterEvent.getRegistry().register(blockSF);

		blockDFF = new Dragonfire_Furnace().setRegistryName("dragonfire_furnace");
		blockRegisterEvent.getRegistry().register(blockDFF);
		
		blockDFF = new Tool_Fuser().setRegistryName("tool_fuser");
		blockRegisterEvent.getRegistry().register(blockTF);
	}

	@SubscribeEvent
	public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent)
	{
		final int MAXIMUM_STACK_SIZE = 64;

		Item.Properties itemSimpleProperties = new Item.Properties().maxStackSize(MAXIMUM_STACK_SIZE)
				.group(Galaxia.galaxiaGroup);
		
		itemBlockSF = new BlockItem(blockSF, itemSimpleProperties);
		itemBlockSF.setRegistryName(blockSF.getRegistryName());
		itemRegisterEvent.getRegistry().register(itemBlockSF);
		
		itemBlockDFF = new BlockItem(blockDFF, itemSimpleProperties);
		itemBlockDFF.setRegistryName(blockDFF.getRegistryName());
		itemRegisterEvent.getRegistry().register(itemBlockDFF);
		
		itemBlockTF = new BlockItem(blockTF, itemSimpleProperties);
		itemBlockTF.setRegistryName(blockTF.getRegistryName());
		itemRegisterEvent.getRegistry().register(itemBlockTF);
	}

	@SubscribeEvent
	public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event)
	{
		tileEntityTypeSF = TileEntityType.Builder.create(TileEntitySoulFurnace::new, blockSF).build(null);
		tileEntityTypeSF.setRegistryName("galaxia:soul_furnace_registry_name");
		event.getRegistry().register(tileEntityTypeSF);
		
		tileEntityTypeDFF = TileEntityType.Builder.create(TileEntityDragonfireFurnace::new, blockDFF).build(null);
		tileEntityTypeDFF.setRegistryName("galaxia:dragonfire_furnace_registry_name");
		event.getRegistry().register(tileEntityTypeDFF);

		tileEntityTypeTF = TileEntityType.Builder.create(TileEntityToolFuser::new, blockTF).build(null);
		tileEntityTypeTF.setRegistryName("galaxia:too_fuser_registry_name");
		event.getRegistry().register(tileEntityTypeTF);
	}

	@SubscribeEvent
	public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event)
	{
		containerTypeSF = IForgeContainerType.create(ContainerSF::createContainerClientSide);
		containerTypeSF.setRegistryName("soul_furnace_container");
		event.getRegistry().register(containerTypeSF);
		
		containerTypeDFF = IForgeContainerType.create(ContainerDFF::createContainerClientSide);
		containerTypeDFF.setRegistryName("dragonfire_furnace_container");
		event.getRegistry().register(containerTypeDFF);

		containerTypeTF = IForgeContainerType.create(ContainerTF::createContainerClientSide);
		containerTypeTF.setRegistryName("tool_fuser_container");
		event.getRegistry().register(containerTypeTF);
	}
}
