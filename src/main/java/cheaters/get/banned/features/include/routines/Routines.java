package cheaters.get.banned.features.include.routines;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.routines.triggers.Trigger;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Routines {

    public static HashMap<Trigger, Routine> routines = new HashMap<>();
    public static ArrayList<String> invalidRoutines = new ArrayList<>();
    public static ArrayList<String> routinesJson = new ArrayList<>();
    public static final File routinesDir = new File(Shady.dir, "routines");

    public static void load() {
        if(!routinesDir.exists()) routinesDir.mkdirs();

        routines.clear();
        routinesJson.clear();
        invalidRoutines.clear();

        JsonParser parser = new JsonParser();
        File[] files = routinesDir.listFiles(name -> name.isFile() && name.getName().endsWith(".json"));
        if(files == null) return;

        for(File file : files) {
            String jsonString;

            try {
                jsonString = FileUtils.readFileToString(file);
            } catch(Exception exception) {
                RoutineRuntimeException.javaException(exception);
                continue;
            }

            JsonObject json = parser.parse(jsonString).getAsJsonObject();

            if(!json.get("enabled").getAsBoolean()) continue;

            Routine routine = new Routine();
            JsonElement routineNameJson = json.get("name");
            if(routineNameJson == null) continue;
            routine.name = routineNameJson.getAsString();

            try {
                routine.allowConcurrent = json.get("allow_concurrent").getAsBoolean();
                JsonObject triggerObject = json.get("trigger").getAsJsonObject();
                routine.trigger = RoutineElementFactory.createTrigger(
                        triggerObject.get("name").getAsString(),
                        new RoutineElementData(triggerObject)
                );

                for(JsonElement action : json.get("actions").getAsJsonArray()) {
                    JsonObject actionObject = action.getAsJsonObject();
                    routine.actions.add(
                            RoutineElementFactory.createAction(
                                    actionObject.get("name").getAsString(),
                                    new RoutineElementData(actionObject)
                            )
                    );
                }

                routinesJson.add(jsonString);
                routines.put(routine.trigger, routine);
            } catch(Exception exception) {
                invalidRoutines.add(routine.name);
                exception.printStackTrace();
            }

        }
    }

}
