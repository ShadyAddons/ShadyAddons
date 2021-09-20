package cheaters.get.banned.config;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Setting {

    public String name;
    public boolean hidden = false;
    public String parent = null;
    public String boundTo = null;
    public String tooltip = null;
    public Type type;
    public Field field;
    public ArrayList<Setting> children = new ArrayList<>();

    public Setting(String name, boolean hidden, String parent, String boundTo, String tooltip, Type type, Field field) {
        this.name = name;
        this.hidden = hidden;
        this.parent = parent;
        this.boundTo = boundTo;
        this.tooltip = tooltip;
        this.type = type;
        this.field = field;
    }

    public boolean enabled() {
        try {
            return (boolean) field.get(Boolean.class);
        } catch(Exception ignored) {}
        return false;
    }

    public void update(Object newValue, boolean preventRecursion) {
        try {
            for(Setting child : children) {
                if(field.getType() == boolean.class) {
                    child.update(false, false);
                } else if(field.getType() == String.class) {
                    child.update(null, false);
                } else {
                    throw new Exception("Type mismatch when updating setting value");
                }
            }

            if(!preventRecursion && boundTo != null) {
                Setting boundSetting = ConfigLogic.getSetting(boundTo);
                if(boundSetting != null) {
                    boundSetting.update(false, true);
                }
            }

            field.set(newValue.getClass(), newValue);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<Setting> getChildren(ArrayList<Setting> settings) {
        ArrayList<Setting> children = new ArrayList<>();

        for(Setting setting : settings) {
            if(setting.parent != null && setting.parent.equals(name)) {
                children.add(setting);
            }
        }

        return children;
    }

    public enum Type {
        SWITCH, CHECKBOX, STRING;
    }

}
