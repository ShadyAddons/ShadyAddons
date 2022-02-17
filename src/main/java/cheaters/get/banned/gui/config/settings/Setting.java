package cheaters.get.banned.gui.config.settings;

import cheaters.get.banned.gui.config.Property;
import cheaters.get.banned.events.SettingChangeEvent;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Field;

public abstract class Setting {

    public String name;
    public ParentSetting parent = null;
    public String note;
    public boolean warning;
    public boolean beta;
    public Field field;
    public Property annotation;

    public Setting(Property annotation, Field field) {
        this.annotation = annotation;
        name = annotation.name();
        warning = annotation.warning();
        beta = annotation.beta();
        if(!annotation.note().equals("")) note = annotation.note();
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

    // Fail at no cost
    public <T> T get(Class<T> type) {
        try {
            return type.cast(field.get(Object.class));
        } catch(Exception ignored) {}
        return null;
    }

    public boolean set(Object value) {
        try {
            Object oldValue = get(Object.class);
            field.set(value.getClass(), value);
            MinecraftForge.EVENT_BUS.post(new SettingChangeEvent(this, oldValue, value));
            return true;
        } catch(Exception ignored) {}
        return false;
    }

    public boolean forceSet(Object value) {
        try {
            field.set(value.getClass(), value);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

}
