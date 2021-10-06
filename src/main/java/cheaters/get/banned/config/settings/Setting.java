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

    public int getIndent(int startingIndent) {
        return getIndent(startingIndent, this);
    }

    public int getIndent(int startingIndent, Setting setting) {
        if(setting.parent != null) {
            startingIndent += 10;
            return setting.getIndent(startingIndent, setting.parent);
        }
        return startingIndent;
    }

    /*public Object get() {
        try {
            return field.get(Object.class);
        } catch(Exception ignored) {}
        return null;
    }*/

    // Fail at no cost
    public <T> T get(Class<T> type) {
        try {
            return type.cast(field.get(Object.class));
        } catch(Exception ignored) {
            ignored.printStackTrace();
        }
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
