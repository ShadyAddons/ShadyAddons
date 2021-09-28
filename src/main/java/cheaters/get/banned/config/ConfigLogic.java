package cheaters.get.banned.config;

import cheaters.get.banned.Shady;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigLogic {
    
    private static String fileName = "config/ShadyAddons.cfg";

    public static ArrayList<Setting> collect(Class<Config> instance) {
        Field[] fields = instance.getDeclaredFields();
        ArrayList<Setting> settings = new ArrayList<>();

        for(Field field : fields) {
            Boolean booleanAnnotation = field.getAnnotation(Boolean.class);
            if(booleanAnnotation != null) {
                settings.add(new Setting(
                        booleanAnnotation.value(),
                        booleanAnnotation.hidden(),
                        booleanAnnotation.parent().equals("") ? null : booleanAnnotation.parent(),
                        booleanAnnotation.boundTo().equals("") ? null : booleanAnnotation.boundTo(),
                        booleanAnnotation.booleanType(),
                        field,
                        booleanAnnotation.credit().equals("") ? null : booleanAnnotation.credit()
                ));
                continue;
            }

            Number numberAnnotation = field.getAnnotation(Number.class);
            if(numberAnnotation != null) {
                settings.add(new Setting(
                        numberAnnotation.value(),
                        numberAnnotation.hidden(),
                        numberAnnotation.parent().equals("") ? null : numberAnnotation.parent(),
                        numberAnnotation.step(),
                        numberAnnotation.prefix().equals("") ? null : numberAnnotation.prefix(),
                        numberAnnotation.suffix().equals("") ? null : numberAnnotation.suffix(),
                        numberAnnotation.min(),
                        numberAnnotation.max(),
                        field,
                        numberAnnotation.credit().equals("") ? null : numberAnnotation.credit()
                ));
                continue;
            }
        }

        /*for(int i = 0; i < settings.size(); i++) {
            Setting newSetting = settings.get(i);
            if(newSetting.type != Setting.SettingType.BOOLEAN) continue;
            newSetting.children = newSetting.getChildren(settings);
            settings.set(i, newSetting);
        }*/

        for(Setting setting : settings) {
            if(setting.type != Setting.SettingType.BOOLEAN) continue;
            setting.children = setting.getChildren(settings);
        }

        return settings;
    }

    public static Setting getSetting(String name) {
        for(Setting setting : Shady.settings) {
            if(setting.name.equals(name)) return setting;
        }
        return null;
    }

    public static void save() {
        try {
            HashMap<String, Object> convertedSettings = new HashMap<>();
            for(Setting setting : Shady.settings) {
                if(setting.type == Setting.SettingType.BOOLEAN) convertedSettings.put(setting.name, setting.enabled());
                if(setting.type == Setting.SettingType.INTEGER) convertedSettings.put(setting.name, (int) setting.getValue());
            }
            String json = new Gson().toJson(convertedSettings);
            Files.write(Paths.get(fileName), json.getBytes(StandardCharsets.UTF_8));
        } catch(Exception error) {
            System.out.println("Error while saving config file");
            error.printStackTrace();
        }
    }

    public static void load() {
        try {
            File file = new File(fileName);
            if(file.exists()) {
                Reader reader = Files.newBufferedReader(Paths.get(fileName));
                Type type = new TypeToken<HashMap<String, Object>>(){}.getType();

                HashMap<String, Object> settingsToProcess = new Gson().fromJson(reader, type);

                for(Map.Entry<String, Object> settingToProcess : settingsToProcess.entrySet()) {
                    Setting settingToAdd = getSetting(settingToProcess.getKey());
                    if(settingToAdd != null) {
                        if(settingToAdd.getValue() instanceof java.lang.Number) {
                            settingToAdd.set(((java.lang.Number) settingToProcess.getValue()).intValue());
                        } else {
                            settingToAdd.set(settingToProcess.getValue());
                        }
                    }
                }
            }
        } catch(Exception error) {
            System.out.println("Error while loading config file");
            error.printStackTrace();
        }
    }

}
