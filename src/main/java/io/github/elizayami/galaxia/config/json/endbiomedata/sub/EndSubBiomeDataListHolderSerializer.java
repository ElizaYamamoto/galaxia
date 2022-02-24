package io.github.elizayami.galaxia.config.json.endbiomedata.sub;

import com.google.gson.*;

import io.github.elizayami.galaxia.Galaxia;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraftforge.common.BiomeDictionary;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class EndSubBiomeDataListHolderSerializer
		implements JsonSerializer<EndSubBiomeDataListHolder>, JsonDeserializer<EndSubBiomeDataListHolder>
{

	@Override
	public JsonElement serialize(EndSubBiomeDataListHolder Src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject biomes = new JsonObject();
		JsonObject endBiomeObject = new JsonObject();
		JsonObject voidBiomeObject = new JsonObject();

		for (EndSubBiomeData endSubBiomeData : Src.getEndSubBiomeData())
		{
			JsonObject object = new JsonObject();

			StringBuilder dictionaryString = new StringBuilder();

			for (BiomeDictionary.Type type : endSubBiomeData.getDictionaryTypes())
			{
				if (!dictionaryString.toString().isEmpty())
					dictionaryString.append(",");
				dictionaryString.append(type.toString());
			}

			object.addProperty("dictionary", dictionaryString.toString().toUpperCase());
			ResourceLocation edgeKey = endSubBiomeData.getEdgeBiome();
			if (edgeKey != null)
				object.addProperty("edge", edgeKey.toString());
			else
				object.addProperty("edge", "");

			// This should never be null.
			ResourceLocation location = endSubBiomeData.getBiome();
			if (location != null)
			{
				if (dictionaryString.toString().contains("VOID"))
					voidBiomeObject.add(location.toString(), object);
				else
					endBiomeObject.add(location.toString(), object);
			}
			else
				Galaxia.LOGGER.error("The Biome key was null! This should NEVER happen.");

		}
		biomes.add("biomes", endBiomeObject);
		biomes.add("void-biomes", voidBiomeObject);

		return biomes;
	}

	@Override
	public EndSubBiomeDataListHolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException
	{
		Galaxia.LOGGER.info("Reading json");

		JsonObject jsonObject = json.getAsJsonObject();
		List<EndSubBiomeData> endSubBiomeData = new ArrayList<>();
		List<EndSubBiomeData> voidSubBiomeData = new ArrayList<>();

		Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.get("biomes").getAsJsonObject().entrySet();
		Set<Map.Entry<String, JsonElement>> voidEntrySet = jsonObject.get("void-biomes").getAsJsonObject().entrySet();

		extractElements(endSubBiomeData, entrySet);
		extractElements(endSubBiomeData, voidEntrySet);
		return new EndSubBiomeDataListHolder(endSubBiomeData, voidSubBiomeData);
	}

	private void extractElements(List<EndSubBiomeData> endSubBiomeData, Set<Map.Entry<String, JsonElement>> entrySet)
	{
		for (Map.Entry<String, JsonElement> elementEntry : entrySet)
		{
			WeightedList<ResourceLocation> weightedList = new WeightedList<>();

			String biomeName = elementEntry.getKey();
			JsonElement element = elementEntry.getValue();

			JsonObject elementObject = element.getAsJsonObject();

			String edge = elementObject.get("edge").getAsString();

			String dictionary = elementObject.get("dictionary").getAsString();

			List<BiomeDictionary.Type> types = Arrays
					.stream(dictionary.trim().replace(" ", "").toUpperCase().split(","))
					.map(this::warnIfTagIsNotDefault).map(BiomeDictionary.Type::getType).collect(Collectors.toList());

			BiomeDictionary.Type[] typesArray = new BiomeDictionary.Type[types.size()];
			typesArray = types.toArray(typesArray);

			if (types.size() == 0)
			{
				types.add(BiomeDictionary.Type.END);
				Galaxia.LOGGER.warn("No dictionary entries were read...defaulting to: \"END\"");
			}

			ResourceLocation biomeKey = new ResourceLocation(biomeName);
			if (Galaxia.EARLY_BIOME_REGISTRY_ACCESS.keySet().contains(biomeKey))
			{
				endSubBiomeData.add(new EndSubBiomeData(biomeKey, typesArray, new ResourceLocation(edge)));
			}
			else
				Galaxia.LOGGER.error("The biome key: \"" + biomeName
						+ "\" was not found in the dynamic registry, skipping entry...");
		}
	}

	public static List<BiomeDictionary.Type> defaultTypesList = new ArrayList<>();

	static
	{
		defaultTypesList.add(BiomeDictionary.Type.HOT);
		defaultTypesList.add(BiomeDictionary.Type.COLD);
		defaultTypesList.add(BiomeDictionary.Type.SPARSE);
		defaultTypesList.add(BiomeDictionary.Type.DENSE);
		defaultTypesList.add(BiomeDictionary.Type.WET);
		defaultTypesList.add(BiomeDictionary.Type.DRY);
		defaultTypesList.add(BiomeDictionary.Type.SAVANNA);
		defaultTypesList.add(BiomeDictionary.Type.CONIFEROUS);
		defaultTypesList.add(BiomeDictionary.Type.JUNGLE);
		defaultTypesList.add(BiomeDictionary.Type.SPOOKY);
		defaultTypesList.add(BiomeDictionary.Type.DEAD);
		defaultTypesList.add(BiomeDictionary.Type.LUSH);
		defaultTypesList.add(BiomeDictionary.Type.MUSHROOM);
		defaultTypesList.add(BiomeDictionary.Type.MAGICAL);
		defaultTypesList.add(BiomeDictionary.Type.RARE);
		defaultTypesList.add(BiomeDictionary.Type.PLATEAU);
		defaultTypesList.add(BiomeDictionary.Type.MODIFIED);
		defaultTypesList.add(BiomeDictionary.Type.OCEAN);
		defaultTypesList.add(BiomeDictionary.Type.RIVER);
		defaultTypesList.add(BiomeDictionary.Type.WATER);
		defaultTypesList.add(BiomeDictionary.Type.MESA);
		defaultTypesList.add(BiomeDictionary.Type.FOREST);
		defaultTypesList.add(BiomeDictionary.Type.PLAINS);
		defaultTypesList.add(BiomeDictionary.Type.MOUNTAIN);
		defaultTypesList.add(BiomeDictionary.Type.HILLS);
		defaultTypesList.add(BiomeDictionary.Type.SWAMP);
		defaultTypesList.add(BiomeDictionary.Type.SANDY);
		defaultTypesList.add(BiomeDictionary.Type.SNOWY);
		defaultTypesList.add(BiomeDictionary.Type.WASTELAND);
		defaultTypesList.add(BiomeDictionary.Type.VOID);
		defaultTypesList.add(BiomeDictionary.Type.OVERWORLD);
		defaultTypesList.add(BiomeDictionary.Type.BEACH);
		defaultTypesList.add(BiomeDictionary.Type.NETHER);
		defaultTypesList.add(BiomeDictionary.Type.END);
	}

	public static List<BiomeDictionary.Type> stopSpamLoggerSpam = new ArrayList<>();

	public String warnIfTagIsNotDefault(String string)
	{
		BiomeDictionary.Type type = BiomeDictionary.Type.getType(string);
		if (!defaultTypesList.contains(type) && !stopSpamLoggerSpam.contains(type))
		{
			Galaxia.LOGGER.warn(type.toString()
					+ " is not a default dictionary value.\nIgnore this msg if using modded biome dictionary values.");
			stopSpamLoggerSpam.add(type);
		}
		return string;
	}
}
