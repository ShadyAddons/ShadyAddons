package cheaters.get.banned.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.properties.Property;
import cheaters.get.banned.config.settings.*;
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
            Property annotation = field.getAnnotation(Property.class);
            if(annotation != null) {
                switch(annotation.type()) {
                    case BOOLEAN:
                        settings.add(new BooleanSetting(annotation, field));
                        break;

                    case NUMBER:
                        settings.add(new NumberSetting(annotation, field));
                        break;

                    case SELECT:
                        settings.add(new SelectSetting(annotation, field));
                        break;

                    case FOLDER:
                        // TODO: Implement folders, like boolean but they don't change contents when collapsed
                        break;
                }
            }
        }

        // Relationships that need to be set after all settings have been collected
        for(Setting setting : settings) {
            // Add parents, null if no parent
            if(!setting.annotation.parent().equals("")) {
                setting.parent = (ParentSetting)ConfigLogic.getSetting(setting.annotation.parent(), settings);
            }
        }

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
                convertedSettings.put(setting.name, setting.get(Object.class));
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
