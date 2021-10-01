package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.properties.Property;

import java.lang.reflect.Field;

public abstract class Setting {

    public String name;
    public ParentSetting parent = null;
    public String credit;
    public Field field;
    public Property annotation;

    public Setting(Property annotation, Field field) {
        this.annotation = annotation;
        name = annotation.name();
        if(!annotation.credit().equals("")) credit = annotation.credit();
        this.field = field;
    }

    // Fail at no cost
    public Object get() {
        try {
            return field.get(Object.class);
        } catch(Exception ignored) {}
        return null;
    }

    public boolean set(Object value) {
        try {
            field.set(value.getClass(), value);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

}
