package cheaters.get.banned.features.routines;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import scala.Int;

public class RoutineElementData {

    private JsonObject json;

    public RoutineElementData(JsonObject json) {
        this.json = json;
    }

    public String keyAsString(String key) throws RoutineRuntimeException {
        String value = keyAsString(key, null);
        if (value == null) throw new RoutineRuntimeException("Value for '" + key + "' is invalid");
        return value;
    }

    public String keyAsString(String key, String defaultValue) {
        JsonElement element = json.get(key);
        if (element == null) return defaultValue;
        return element.getAsString();
    }

    public Integer keyAsInt(String key) throws RoutineRuntimeException {
        Integer value = keyAsInt(key, null);
        if (value == null) throw new RoutineRuntimeException("Value for '" + key + "' is invalid");
        return value;
    }

    public Integer keyAsInt(String key, Integer defaultValue) {
        JsonElement element = json.get(key);
        if(element == null) return defaultValue;
        return element.getAsInt();
    }

    public Boolean keyAsBool(String key) throws RoutineRuntimeException {
        Boolean value = keyAsBool(key, null);
        if(value == null) throw new RoutineRuntimeException("Value for '" + key + "' is invalid");
        return value;
    }

    public Boolean keyAsBool(String key, Boolean defaultValue) {
        JsonElement element = json.get(key);
        if(element == null) return defaultValue;
        return element.getAsBoolean();
    }
}
