package cheaters.get.banned.features.routines;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RoutineElementData {

    private JsonObject json;

    public RoutineElementData(JsonObject json) {
        this.json = json;
    }

    public String keyAsString(String key) throws RoutineRuntimeException {
        String value = keyAsString_noError(key);
        if(value == null) throw new RoutineRuntimeException("Value for '" + key + "' is invalid");
        return value;
    }

    public String keyAsString_noError(String key) {
        JsonElement element = json.get(key);
        if(element == null) return null;
        return element.getAsString();
    }

    public Integer keyAsInt(String key) throws RoutineRuntimeException {
        Integer value = keyAsInt_noError(key);
        if(value == null) throw new RoutineRuntimeException("Value for '" + key + "' is invalid");
        return value;
    }

    public Integer keyAsInt_noError(String key) {
        JsonElement element = json.get(key);
        if(element == null) return null;
        return element.getAsInt();
    }

    public Boolean keyAsBool_noError(String key) {
        JsonElement element = json.get(key);
        if(element == null) return null;
        return element.getAsBoolean();
    }

    public Boolean keyAsBool(String key) throws RoutineRuntimeException {
        Boolean value = keyAsBool_noError(key);
        if(value == null) throw new RoutineRuntimeException("Value for '" + key + "' is invalid");
        return value;
    }

}
