package io.github.elizayami.galaxia.core.init;

import java.util.HashSet;
import java.util.Set;
import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.entities.boat.GalaxiaBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit
{
	public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITIES, Galaxia.MOD_ID);

	public static Set<EntityType<?>> entities = new HashSet<>();

	public static final EntityType<GalaxiaBoatEntity> BOAT = createEntity("boat", EntityType.Builder
			.<GalaxiaBoatEntity>create(GalaxiaBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).build(Galaxia.MOD_ID + ":boat"));

	public static final EntityType<SmallFireballEntity> SMAL_BOLT = createEntity("small_bolt", EntityType.Builder
			.<SmallFireballEntity>create(SmallFireballEntity::new, EntityClassification.MISC).size(0.3125F, 0.3125F).trackingRange(4).func_233608_b_(10).build(Galaxia.MOD_ID + ":small_bolt"));

	public static <E extends Entity, ET extends EntityType<E>> ET createEntity(String id, ET entityType)
	{
		entityType.setRegistryName(new ResourceLocation(Galaxia.MOD_ID, id));
		entities.add(entityType);
		return entityType;
	}
	
	public static void init()
	{
	}
}
