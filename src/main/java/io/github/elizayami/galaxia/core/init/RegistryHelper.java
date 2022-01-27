package io.github.elizayami.galaxia.core.init;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.abstracts.items.ModBoatItem;
import io.github.elizayami.galaxia.core.util.AbstractRegistryHelper;
import io.github.elizayami.galaxia.core.util.Registrar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHelper extends AbstractRegistryHelper<Item>
{

	public RegistryHelper(Registrar parent, DeferredRegister<Item> deferredRegister)
	{
		super(parent, deferredRegister);
	}

	public RegistryHelper(Registrar registrar)
	{
		super(registrar, DeferredRegister.create(ForgeRegistries.ITEMS, Galaxia.MOD_ID));
	}

	public RegistryObject<Item> createBoatItem(String wood, RegistryObject<Block> block)
	{
		String type = "galaxia:" + wood;
		RegistryObject<Item> boat = this.deferredRegister.register(wood + "_boat",
				() -> new ModBoatItem(type, new Item.Properties().group(Galaxia.galaxiaGroup).maxStackSize(1)));
		BoatInit.registerBoat(type, boat, block);
		return boat;
	}
}
