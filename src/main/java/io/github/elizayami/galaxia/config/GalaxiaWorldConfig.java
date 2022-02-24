package io.github.elizayami.galaxia.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import io.github.elizayami.galaxia.Galaxia;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GalaxiaWorldConfig
{
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;

	public static final ForgeConfigSpec.IntValue BIOME_SIZE_NETHER;
	public static final ForgeConfigSpec.IntValue BIOME_SIZE_END;
	public static final ForgeConfigSpec.IntValue MAIN_ISLAND_BIOME_SIZE;
	public static final ForgeConfigSpec.IntValue VOID_BIOME_SIZE;

	public static final ForgeConfigSpec.ConfigValue<String> BLACKLIST_NETHER;
	public static final ForgeConfigSpec.BooleanValue CONTROL_NETHER;
	public static final ForgeConfigSpec.BooleanValue CONTROL_END;
	public static final ForgeConfigSpec.BooleanValue IS_BLACKLIST_NETHER;

	static
	{

		COMMON_BUILDER.push("The_End");
		CONTROL_END = COMMON_BUILDER.comment("\nDoes Galaxia control The End?").define("ControlEnd", true);

		COMMON_BUILDER.push("Void");
		VOID_BIOME_SIZE = COMMON_BUILDER
				.comment("\nVoid Biome(where small end islands generate in vanilla) size.\nDefault: 3")
				.defineInRange("VoidBiomeSize", 2, 0, 10);
		COMMON_BUILDER.pop();

		BIOME_SIZE_END = COMMON_BUILDER.comment("\nEnd Biome Size\nDefault: 3").defineInRange("EndBiomeSize", 3, 0, 10);
		MAIN_ISLAND_BIOME_SIZE = COMMON_BUILDER.comment("\nEnd Biome Size.\nDefault: 3")
				.defineInRange("IslandBiomeSize", 3, 0, 10);
		COMMON_BUILDER.pop();

		COMMON_BUILDER.push("The_Nether");
		CONTROL_NETHER = COMMON_BUILDER.comment("\nDoes Galaxia control The Nether?").define("ControlNether", true);
		IS_BLACKLIST_NETHER = COMMON_BUILDER.comment(
				"\nIs the list of biomes a blacklist or whitelist?\nWhen this list is a blacklist, the values in the list will not be in world generation.\nWhen this list is a whitelist, Adding a biome several times gives it more weight in generation and it must contain at least 1 value.\nWhen set to true, datapack biomes will work automatically! When set to false, you will need to manually add datapack entries.\nDefault: true")
				.define("isBlacklistNether", true);
		BLACKLIST_NETHER = COMMON_BUILDER.comment(
				"\nThis works as a whitelist or blacklist depending on the \"isBlacklistNether\" value.\nNO SPACES AFTER COMMAS!\nDefault: \"\"")
				.define("BlacklistNether", "");
		BIOME_SIZE_NETHER = COMMON_BUILDER.comment("\nNether Biome Size\nDefault: 3").defineInRange("NetherBiomeSize",
				3, 0, 10);
		COMMON_BUILDER.pop();
		COMMON_CONFIG = COMMON_BUILDER.build();
	}

	public static void loadConfig(ForgeConfigSpec config, Path path)
	{
		Galaxia.LOGGER.info("Loading config: " + path);
		CommentedFileConfig file = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE)
				.build();
		file.load();
		config.setConfig(file);
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent)
	{
	}
}
