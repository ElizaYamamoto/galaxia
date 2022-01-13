package io.github.elizayami.galaxia.common.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityLootTables extends EntityLootTables
{
	@Override
	protected void addTables()
	{
		
	}
	
	@Override
	protected Iterable<EntityType<?>> getKnownEntities() 
	{
		return StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false)
				.filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(Galaxia.MOD_ID))
				.collect(Collectors.toSet());
	}
}
