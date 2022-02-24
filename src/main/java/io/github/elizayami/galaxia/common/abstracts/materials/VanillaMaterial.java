package io.github.elizayami.galaxia.common.abstracts.materials;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.items.BackhawItem;
import io.github.elizayami.galaxia.common.abstracts.items.BackhoeItem;
import io.github.elizayami.galaxia.common.abstracts.items.HammerItem;
import io.github.elizayami.galaxia.common.abstracts.items.PaxelItem;
import io.github.elizayami.galaxia.common.abstracts.items.SawItem;
import io.github.elizayami.galaxia.common.abstracts.items.TillerItem;
import io.github.elizayami.galaxia.core.init.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class VanillaMaterial 
{
	public final RegistryObject<Item> paxel;

	public final RegistryObject<Item> hammer;
	public final RegistryObject<Item> saw;
	public final RegistryObject<Item> backhoe;
	public final RegistryObject<Item> tiller;
	public final RegistryObject<Item> backhaw;
	
	public final String name;
	public VanillaMaterial(String name, IItemTier material) 
	{
		this.name = name;
		
		paxel = ItemInit.registerItem(name + "_paxel", () -> new PaxelItem(material,  1, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));
		
		hammer = ItemInit.registerItem(name + "_hammer", () -> new HammerItem(material, 2.5f, -2.8F, p -> p.group(Galaxia.galaxiaGroup)));
		saw = ItemInit.registerItem(name + "_saw", () -> new SawItem(material, 5, -4F, p -> p.group(Galaxia.galaxiaGroup)));
		backhoe = ItemInit.registerItem(name + "_backhoe", () -> new BackhoeItem(material, 2.5F, -3F, p -> p.group(Galaxia.galaxiaGroup)));
		tiller = ItemInit.registerItem(name + "_tiller", () -> new TillerItem(material, -2, 0F, p -> p.group(Galaxia.galaxiaGroup)));
		backhaw = ItemInit.registerItem(name + "_backhaw", () -> new BackhawItem(material, 2, -1.8F, p -> p.group(Galaxia.galaxiaGroup)));

	}
}
