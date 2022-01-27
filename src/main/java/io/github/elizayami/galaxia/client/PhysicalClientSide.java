package io.github.elizayami.galaxia.client;

import io.github.elizayami.galaxia.client.renderer.GalaxiaChestTileEntityRenderer;
import io.github.elizayami.galaxia.client.renderer.GalaxiaSignTileEntityRenderer;
import io.github.elizayami.galaxia.common.abstracts.materials.MetalMaterial;
import io.github.elizayami.galaxia.common.abstracts.materials.WoodenMaterial;
import io.github.elizayami.galaxia.core.init.BlockInit;
import io.github.elizayami.galaxia.core.init.WoodTileEntityInit;
import io.github.elizayami.galaxia.usefultools.IPhysicalSide;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PhysicalClientSide implements IPhysicalSide
{
	@Override
	public void setup(IEventBus modEventBus, IEventBus forgeEventBus) 
	{
		modEventBus.addListener(this::clientSetup);
	}
	
	private void clientSetup(FMLClientSetupEvent event)
	{
		registerRenderers();
		registerGUIs();
		setRenderLayers();
	}
	
	private void registerRenderers()
	{
		ClientRegistry.bindTileEntityRenderer(WoodTileEntityInit.CHEST.get(), GalaxiaChestTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(WoodTileEntityInit.SIGN.get(), GalaxiaSignTileEntityRenderer::new);
	}
	
	private void registerGUIs()
	{	
		
	}
	
	private void setRenderLayers()
	{
		// WOODEN MATERIALS
		
		setWoodenMaterialRenderLayers(BlockInit.SHADOWSPIKE);
		setWoodenMaterialRenderLayers(BlockInit.GROUNDSTALK);
		setWoodenMaterialRenderLayers(BlockInit.SEAWOOD);
		setWoodenMaterialRenderLayers(BlockInit.SCORCHWOOD);
		setWoodenMaterialRenderLayers(BlockInit.GHOSTWOOD);
		setWoodenMaterialRenderLayers(BlockInit.GROVEWOOD);
		
		// METAL MATERIALS

		setMetalMaterialRenderLayers(BlockInit.METEOR);
		setMetalMaterialRenderLayers(BlockInit.COMETSTEEL);
	}
	
	private void setWoodenMaterialRenderLayers(WoodenMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.door.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.trapdoor.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.ladder.get(), RenderType.getCutout());
	}
	
	private void setMetalMaterialRenderLayers(MetalMaterial material)
	{
		RenderTypeLookup.setRenderLayer(material.door.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(material.trapdoor.get(), RenderType.getCutout());
	}
}
