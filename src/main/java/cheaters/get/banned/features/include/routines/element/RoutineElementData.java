package cheaters.get.banned.features.include.routines.element;

import cheaters.get.banned.features.include.routines.RoutineException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RoutineElementData {

    private JsonObject json;

    public RoutineElementData(JsonObject json) {
        this.json = json;
    }

    public String keyAsString(String key) throws RoutineException {
        JsonElement element = json.get(key);
        if(element == null) throw new RoutineException("Value for '" + key + "' is invalid");
        return element.getAsString();
    }

    public int keyAsInt(String key) throws RoutineException {
        JsonElement element = json.get(key);
        if(element == null) throw new RoutineException("Value for '" + key + "' is invalid");
        return element.getAsInt();
    }

}
