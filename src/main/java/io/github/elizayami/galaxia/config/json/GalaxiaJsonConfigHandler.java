package io.github.elizayami.galaxia.config.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.elizayami.galaxia.Galaxia;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaEndSubBiome;
import io.github.elizayami.galaxia.common.world.biome.GalaxiaSubBiome;
import io.github.elizayami.galaxia.config.json.biomedata.BiomeDataListHolder;
import io.github.elizayami.galaxia.config.json.biomedata.BiomeDataListHolderSerializer;
import io.github.elizayami.galaxia.config.json.endbiomedata.EndBiomeDataListHolder;
import io.github.elizayami.galaxia.config.json.endbiomedata.EndBiomeDataListHolderSerializer;
import io.github.elizayami.galaxia.config.json.endbiomedata.sub.EndSubBiomeDataListHolder;
import io.github.elizayami.galaxia.config.json.endbiomedata.sub.EndSubBiomeDataListHolderSerializer;
import io.github.elizayami.galaxia.config.json.subbiomedata.SubBiomeDataListHolder;
import io.github.elizayami.galaxia.config.json.subbiomedata.SubBiomeDataListHolderSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class GalaxiaJsonConfigHandler {

    public static void handleEndBiomeJsonConfigs(Path path, Registry<Biome> biomeRegistry) {
        File dir = new File(path.toString());
        if (!dir.exists())
            dir.mkdirs();

        try {
            createReadMe(path.resolve("README.txt"));
        } catch (Exception e) {
            Galaxia.LOGGER.info("config/byg README.txt failed to load!");
        }

        try {
            handleGalaxiaEndBiomesJSONConfig(path.resolve(Galaxia.MOD_ID + "-end-biomes.json"), biomeRegistry);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("byg-end-biomes.json failed to load. To quickly fix this error, delete this file and let it reset.");
        }

        try {
            handleGalaxiaEndSubBiomesJSONConfig(path.resolve(Galaxia.MOD_ID + "-end-sub-biomes.json"));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("byg-end-sub-biomes.json failed to load. To quickly fix this error, delete this file and let it reset.");
        }

        EndBiomeDataListHolder.fillBiomeLists();
        EndSubBiomeDataListHolder.fillBiomeLists();
    }


    //TODO: Handle per world load and allow datapack values.
    public static void handleOverWorldConfig(Path path) {
        File dir = new File(path.toString());
        if (!dir.exists())
            dir.mkdirs();

        try {
            handleGalaxiaSubBiomesJSONConfig(path.resolve(Galaxia.MOD_ID + "-sub-biomes.json"));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("byg-sub-biomes.json failed to load. To quickly fix this error, delete this file and let it reset.");
        }

        try {
            handleGalaxiaBiomesJSONConfig(path.resolve(Galaxia.MOD_ID + "-biomes.json"));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("byg-biomes.json failed to load. To quickly fix this error, delete this file and let it reset.");
        }

        BiomeDataListHolder.fillBiomeLists();
        SubBiomeDataListHolder.fillBiomeLists();
    }


    public static void handleGalaxiaEndSubBiomesJSONConfig(Path path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EndSubBiomeDataListHolder.class, new EndSubBiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        final File CONFIG_FILE = new File(String.valueOf(path));

        if (!CONFIG_FILE.exists()) {
            EndSubBiomeDataListHolder.createDefaults();
            createGalaxiaEndSubBiomesJson(path);
        }
        try (Reader reader = new FileReader(path.toString())) {
            EndSubBiomeDataListHolder biomeDataListHolder = gson.fromJson(reader, EndSubBiomeDataListHolder.class);
            if (biomeDataListHolder != null) {
                GalaxiaEndSubBiome.endSubBiomeData = biomeDataListHolder.getEndSubBiomeData();
                GalaxiaEndSubBiome.voidSubBiomeData = biomeDataListHolder.getVoidSubBiomeData();
            } else
                Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be read");

        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be read");
        }
    }

    public static void createGalaxiaEndSubBiomesJson(Path path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EndSubBiomeDataListHolder.class, new EndSubBiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        String jsonString = gson.toJson(new EndSubBiomeDataListHolder(GalaxiaEndSubBiome.endSubBiomeData, GalaxiaEndSubBiome.voidSubBiomeData));

        try {
            Files.write(path, jsonString.getBytes());
        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be created");
        }
    }


    public static void handleGalaxiaEndBiomesJSONConfig(Path path, Registry<Biome> biomeRegistry) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EndBiomeDataListHolder.class, new EndBiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        final File CONFIG_FILE = new File(String.valueOf(path));

        if (!CONFIG_FILE.exists()) {
            EndBiomeDataListHolder.createDefaults(biomeRegistry);
            createGalaxiaEndBiomesJson(path);
        }
        try (Reader reader = new FileReader(path.toString())) {
            EndBiomeDataListHolder biomeDataListHolder = gson.fromJson(reader, EndBiomeDataListHolder.class);
            if (biomeDataListHolder != null) {
                GalaxiaEndBiome.endBiomeData = biomeDataListHolder.getEndBiomeData();
                GalaxiaEndBiome.voidBiomeData = biomeDataListHolder.getVoidBiomeData();
            } else
                Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be read");

        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be read");
        }
    }

    public static void createGalaxiaEndBiomesJson(Path path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EndBiomeDataListHolder.class, new EndBiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        String jsonString = gson.toJson(new EndBiomeDataListHolder(GalaxiaEndBiome.endBiomeData, GalaxiaEndBiome.voidBiomeData));

        try {
            Files.write(path, jsonString.getBytes());
        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be created");
        }
    }

    public static void handleGalaxiaBiomesJSONConfig(Path path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BiomeDataListHolder.class, new BiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        final File CONFIG_FILE = new File(String.valueOf(path));

        if (!CONFIG_FILE.exists()) {
            BiomeDataListHolder.createDefaults();
            createGalaxiaBiomesJson(path);
        }
        try (Reader reader = new FileReader(path.toString())) {
            BiomeDataListHolder biomeDataListHolder = gson.fromJson(reader, BiomeDataListHolder.class);
            if (biomeDataListHolder != null)
                GalaxiaBiome.biomeData = biomeDataListHolder.getBiomeData();
            else
                Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be read");

        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be read");
        }
    }

    public static void createGalaxiaBiomesJson(Path path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BiomeDataListHolder.class, new BiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        String jsonString = gson.toJson(new BiomeDataListHolder(GalaxiaBiome.biomeData));

        try {
            Files.write(path, jsonString.getBytes());
        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-biomes.json could not be created");
        }
    }


    public static void handleGalaxiaSubBiomesJSONConfig(Path path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SubBiomeDataListHolder.class, new SubBiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        final File CONFIG_FILE = new File(String.valueOf(path));

        if (!CONFIG_FILE.exists()) {
            SubBiomeDataListHolder.createDefaults();
            createGalaxiaSubBiomesJson(path);
        }
        try (Reader reader = new FileReader(path.toString())) {
            SubBiomeDataListHolder biomeDataListHolder = gson.fromJson(reader, SubBiomeDataListHolder.class);
            if (biomeDataListHolder != null)
                GalaxiaSubBiome.subBiomeData = biomeDataListHolder.getSubBiomeData();
            else
                Galaxia.LOGGER.error(Galaxia.MOD_ID + "-sub-biomes.json could not be read");

        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-sub-biomes.json could not be read");
        }
    }

    public static void createGalaxiaSubBiomesJson(Path path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SubBiomeDataListHolder.class, new SubBiomeDataListHolderSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();

        String jsonString = gson.toJson(new SubBiomeDataListHolder(GalaxiaSubBiome.subBiomeData));

        try {
            Files.write(path, jsonString.getBytes());
        } catch (IOException e) {
            Galaxia.LOGGER.error(Galaxia.MOD_ID + "-sub-biomes.json could not be created");
        }
    }


    public static void createReadMe(Path path) {
        final File README_FILE = new File(String.valueOf(path));
        String text = "If you need help understanding what the byg-biomes or byg-sub-biomes configs allow you to do, please watch this video: https://youtu.be/iq0q09O7ZYo\n\nIf you need help with datapacking, please watch this: https://youtu.be/TF_p8OeB-hc";
        if (!README_FILE.exists()) {
            try {
                Files.write(path, text.getBytes());
            } catch (IOException e) {
                Galaxia.LOGGER.error(Galaxia.MOD_ID + "'s README.txt could not be created");
            }
        }
    }
}