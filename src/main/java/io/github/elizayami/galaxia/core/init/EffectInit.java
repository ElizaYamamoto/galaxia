package io.github.elizayami.galaxia.core.init;

import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.effects.BlessedEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit 
{
	public static final DeferredRegister<Effect> EFFECTS = 
			DeferredRegister.create(ForgeRegistries.POTIONS, Galaxia.MOD_ID);

	public static final RegistryObject<BlessedEffect> BLESSED = EFFECTS.register("blessed",
			() -> new BlessedEffect());
	
	public static final RegistryObject<BlessedEffect> OVERCHARGED = EFFECTS.register("overcharged",
			() -> new BlessedEffect());
}
