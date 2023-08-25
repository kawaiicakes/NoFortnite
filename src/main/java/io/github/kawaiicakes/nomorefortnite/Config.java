package io.github.kawaiicakes.nomorefortnite;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    public static final Gson BUILDER = (new GsonBuilder()).setPrettyPrinting().create();

    public static final Path file = FMLPaths.GAMEDIR.get().toAbsolutePath().resolve("config").resolve("nomorefortnite.json");

    public static ConfigEntries loadConfig() {
        try {
            if (Files.notExists(file)) {
                ConfigEntries defaultConfig = new ConfigEntries();
                String defaultJson = BUILDER.toJson(defaultConfig);
                Files.writeString(file, defaultJson);
            }

            return BUILDER.fromJson(Files.readString(file), ConfigEntries.class);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static class ConfigEntries {
        public boolean INHIBIT_ATTACKER = true;
        public boolean INHIBIT_TARGET = true;

        public int INHIBIT_TIMER_ATTACKER = 60;
        public int INHIBIT_TIMER_TARGET = 60;

        public boolean NOTIFY_ATTACKER = true;
        public boolean NOTIFY_TARGET = true;
    }
}
