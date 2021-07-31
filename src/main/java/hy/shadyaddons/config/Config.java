package hy.shadyaddons.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.File;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public enum Setting {
        OPEN_CHESTS_THROUGH_WALLS("Open Chests Through Walls", false),
        BLOCK_CELLS_ALIGNMENT("Block Cells Alignment", false),
        CLOSE_SECRET_CHESTS("Close Secret Chests", false),
        ROYAL_PIGEON_PICKAXE_MACRO("Royal Pigeon Pickaxe Macro", false),
        CLOSE_CRYSTAL_HOLLOWS_CHESTS("Close Crystal Hollows Chests", false),
        GHOST_BLOCK_KEYBIND("Ghost Block Keybind", false),
        BLOCK_STONK_ABILITIES("Block Stonk Abilities", false),
        BOSS_CORLEONE_FINDER("Boss Corleone Finder", false);

        public String name;
        public boolean defaultValue;
        Setting(String name, boolean defaultValue) {
            this.name = name;
            this.defaultValue = defaultValue;
        }
    }

    private static HashMap<Setting, Boolean> config = new HashMap<Setting, Boolean>(){{
        for(Setting setting : Setting.values()) {
            put(setting, setting.defaultValue);
        }
    }};

    private static String fileName = "config/ShadyAddons.json";

    public static void load() {
        try {
            File file = new File(fileName);
            if(file.exists()) {
                Reader reader = Files.newBufferedReader(Paths.get(fileName));
                Type type = new TypeToken<HashMap<String, Boolean>>(){}.getType();

                HashMap<String, Boolean> settingsToProcess = new Gson().fromJson(reader, type);

                for(Map.Entry<String, Boolean> settingToProcess : settingsToProcess.entrySet()) {
                    config.put(Setting.valueOf(settingToProcess.getKey()), settingToProcess.getValue());
                }
            }
        } catch(Exception error) {
            System.out.println("Error loading config file");
            error.printStackTrace();
        }
    }

    public static void save() {
        try {
            HashMap<String, Boolean> convertedSettings = new HashMap<>();
            for(Map.Entry<Setting, Boolean> setting : config.entrySet()) {
                convertedSettings.put(setting.getKey().name(), setting.getValue());
            }
            String json = new Gson().toJson(convertedSettings);
            Files.write(Paths.get(fileName), json.getBytes(StandardCharsets.UTF_8));
        } catch(Exception error) {
            System.out.println("Error saving config file");
            error.printStackTrace();
        }
    }

    public static boolean isEnabled(Setting setting) {
        return config.get(setting);
    }

    public static void setEnabled(Setting setting, boolean enabled) {
        config.put(setting, enabled);
    }

    public static void toggleEnabled(Setting setting) {
        setEnabled(setting, !config.get(setting));
    }

}
