package cheaters.get.banned.config;

import net.minecraft.util.MathHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Setting {

    public SettingType type;
    public String name;
    public boolean hidden = false;
    public String parent = null;
    public Field field;
    public String credit;

    // Only SettingType.INTEGER
    public int step;
    public String prefix;
    public String suffix;
    public int min;
    public int max;

    // Only SettingType.BOOLEAN
    public ArrayList<Setting> children = new ArrayList<>();
    public String boundTo = null;
    public BooleanType booleanType;

    // Create SettingType.BOOLEAN
    public Setting(String name, boolean hidden, String parent, String boundTo, BooleanType booleanType, Field field, String credit) {
        this.type = SettingType.BOOLEAN;
        this.name = name;
        this.hidden = hidden;
        this.parent = parent;
        this.boundTo = boundTo;
        this.booleanType = booleanType;
        this.field = field;
        this.credit = credit;
    }

    // Create SettingType.INTEGER
    public Setting(String name, boolean hidden, String parent, int step, String prefix, String suffix, int min, int max, Field field, String credit) {
        this.type = SettingType.INTEGER;
        this.name = name;
        this.hidden = hidden;
        this.parent = parent;
        this.step = step;
        this.prefix = prefix;
        this.suffix = suffix;
        this.min = min;
        this.max = max;
        this.field = field;
        this.credit = credit;
    }

    public boolean enabled() {
        try {
            if(type == SettingType.BOOLEAN) return (boolean) field.get(boolean.class);
        } catch(Exception ignored) {}
        return false;
    }

    public Object getValue() {
        try {
            return field.get(Object.class);
        } catch(Exception ignored) {}
        return null;
    }

    public void set(Object value) {
        try {
            field.set(value.getClass(), value);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void update(Object newValue, boolean preventRecursion) {
        try {
            for(Setting child : children) {
                if(child.type == SettingType.BOOLEAN) child.update(false, false);
            }

            if(!preventRecursion && boundTo != null) {
                Setting boundSetting = ConfigLogic.getSetting(boundTo);
                if(boundSetting != null) {
                    boundSetting.update(false, true);
                }
            }

            if(type == SettingType.INTEGER) newValue = MathHelper.clamp_int((int) newValue, 0, max);
            set(newValue);
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

    public enum BooleanType {
        SWITCH, CHECKBOX;
    }

    public enum SettingType {
        BOOLEAN, INTEGER
    }

}
