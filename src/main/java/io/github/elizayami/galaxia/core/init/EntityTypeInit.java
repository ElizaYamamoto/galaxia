package io.github.elizayami.galaxia.core.init;

import java.util.function.BiFunction;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.entity.ModBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit
{
	public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITIES,
			Galaxia.MOD_ID);

	public static final RegistryObject<EntityType<ModBoatEntity>> BOAT = createEntity("boat", ModBoatEntity::new, ModBoatEntity::new, EntityClassification.MISC, 1.375F, 0.5625F);
	
	public static <E extends Entity> EntityType<E> createEntity(EntityType.IFactory<E> factory, BiFunction<FMLPlayMessages.SpawnEntity, World, E> clientFactory, EntityClassification entityClassification, String name, float width, float height) {
		ResourceLocation location = new ResourceLocation(Galaxia.MOD_ID, name);
		EntityType<E> entity = EntityType.Builder.create(factory, entityClassification)
				.size(width, height)
				.setTrackingRange(64)
				.setShouldReceiveVelocityUpdates(true)
				.setUpdateInterval(3)
				.setCustomClientFactory(clientFactory)
				.build(location.toString());
		return entity;
	}
	
	public static <E extends Entity> RegistryObject<EntityType<E>> createEntity(String name, EntityType.IFactory<E> factory,
			BiFunction<FMLPlayMessages.SpawnEntity, World, E> clientFactory, EntityClassification entityClassification,
			float width, float height)
	{
		return ENTITY.register(name,
				() -> createEntity(factory, clientFactory, entityClassification, name, width, height));

	}
}
