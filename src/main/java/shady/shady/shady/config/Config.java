package shady.shady.shady.config;

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

    private static HashMap<Setting, Boolean> config = new HashMap<Setting, Boolean>(){{
        for(Setting setting : Setting.values()) {
            put(setting, false);
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
            System.out.println("Error while loading config file");
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
            System.out.println("Error while saving config file");
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
