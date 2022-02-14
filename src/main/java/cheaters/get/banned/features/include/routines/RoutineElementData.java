package cheaters.get.banned.features.include.routines;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RoutineElementData {

    private JsonObject json;

    public RoutineElementData(JsonObject json) {
        this.json = json;
    }

    public String keyAsString(String key, boolean error) throws RoutineException {
        JsonElement element = json.get(key);

        if(element == null) {
            if(error) throw new RoutineException("Value for '" + key + "' is invalid");
            return null;
        }

        return element.getAsString();
    }

    public Integer keyAsInt(String key, boolean error) throws RoutineException {
        JsonElement element = json.get(key);

        if(element == null) {
            if(error) throw new RoutineException("Value for '" + key + "' is invalid");
            return null;
        }

        return element.getAsInt();
    }

}
