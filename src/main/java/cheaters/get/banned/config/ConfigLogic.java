package cheaters.get.banned.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.settings.*;
import cheaters.get.banned.remote.DisableFeatures;
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
import java.util.List;
import java.util.Map;

public class ConfigLogic {
    
    private static String fileName = "config/ShadyAddons.cfg";

    public static ArrayList<Setting> collect(Class<Config> instance) {
        Field[] fields = instance.getDeclaredFields();
        ArrayList<Setting> settings = new ArrayList<>();

        for(Field field : fields) {
            Property annotation = field.getAnnotation(Property.class);
            if(annotation != null) {
                switch(annotation.type()) {
                    case BOOLEAN:
                    case CHECKBOX:
                        settings.add(new BooleanSetting(annotation, field, annotation.type()));
                        break;

                    case NUMBER:
                        settings.add(new NumberSetting(annotation, field));
                        break;

                    case SELECT:
                        settings.add(new SelectSetting(annotation, field));
                        break;

                    case FOLDER:
                        settings.add(new FolderSetting(annotation, field));
                        break;
                }
            }
        }

        List<String> disabledFeatures = DisableFeatures.load();
        ArrayList<Setting> settingsToRemove = new ArrayList<>();

        // Relationships that need to be set after all settings have been collected
        for(Setting setting : settings) {
            if(disabledFeatures.contains(setting.name)) {
                settingsToRemove.add(setting);
                continue;
            }

            if(settingsToRemove.contains(setting.parent)) {
                settingsToRemove.add(setting);
                continue;
            }

            if(!setting.annotation.parent().equals("")) {
                setting.parent = (ParentSetting) ConfigLogic.getSetting(setting.annotation.parent(), settings);
                if(setting.parent != null) setting.parent.children.add(setting);
            }
        }

        settings.removeAll(settingsToRemove);

        return settings;
    }

    public static Setting getSetting(String name, ArrayList<Setting> settings) {
        for(Setting setting : settings) {
            if(setting.name.equals(name)) return setting;
        }
        return null;
    }

    public static void save() {
        try {
            HashMap<String, Object> convertedSettings = new HashMap<>();
            for(Setting setting : Shady.settings) {
                if(setting instanceof FolderSetting) continue;
                convertedSettings.put(setting.name, setting.get(Object.class));
            }
            String json = new Gson().toJson(convertedSettings);
            Files.write(Paths.get(fileName), json.getBytes(StandardCharsets.UTF_8));
        } catch(Exception error) {
            System.out.println("Error saving config file");
            error.printStackTrace();
        }
    }

    public static void load() {
        try {
            File file = new File(fileName);
            if(file.exists()) {
                Reader reader = Files.newBufferedReader(Paths.get(fileName));
                Type type = new TypeToken<HashMap<String, Object>>(){}.getType();

                HashMap<String, Object> settingsFromConfig = new Gson().fromJson(reader, type);

                for(Map.Entry<String, Object> fromConfig : settingsFromConfig.entrySet()) {
                    Setting beingUpdated = getSetting(fromConfig.getKey(), Shady.settings);
                    if(beingUpdated != null) {
                        if(beingUpdated instanceof NumberSetting || beingUpdated instanceof SelectSetting) {
                            beingUpdated.set(((Double)fromConfig.getValue()).intValue());
                        } else {
                            beingUpdated.forceSet(fromConfig.getValue());
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
