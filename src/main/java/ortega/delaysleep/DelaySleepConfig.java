package ortega.delaysleep;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import net.fabricmc.loader.api.FabricLoader;

public final class DelaySleepConfig {
    public static final int TICKS_PER_DAY = 24000;
    public static final int VANILLA_MIN_TICK_CLEAR = 12542;
    public static final int VANILLA_MIN_TICK_RAIN = 12010;
    public static final int DEFAULT_MIN_TICK_CLEAR = 16000;
    public static final int DEFAULT_MIN_TICK_RAIN = 13000;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public int minTickClear = DEFAULT_MIN_TICK_CLEAR;
    public int minTickRain = DEFAULT_MIN_TICK_RAIN;

    public int minTickFor(boolean raining) {
        return clamp(raining ? this.minTickRain : this.minTickClear);
    }

    private static int clamp(int tick) {
        return Math.clamp(tick, 0, TICKS_PER_DAY - 1);
    }

    public static DelaySleepConfig load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(DelaySleep.MOD_ID + ".json");

        if (Files.isRegularFile(path)) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                DelaySleepConfig config = new DelaySleepConfig();

                if (json.has("minTickClear")) {
                    config.minTickClear = clamp(json.get("minTickClear").getAsInt());
                }

                if (json.has("minTickRain")) {
                    config.minTickRain = clamp(json.get("minTickRain").getAsInt());
                }

                return config;
            } catch (Exception e) {
                DelaySleep.LOGGER.error("Could not read {}, using defaults", path, e);
            }
        }

        DelaySleepConfig config = new DelaySleepConfig();
        config.save();
        return config;
    }

    public void save() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(DelaySleep.MOD_ID + ".json");

        try {
            Files.createDirectories(path.getParent());

            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            DelaySleep.LOGGER.error("Could not write {}", path, e);
        }
    }
}
