package cheaters.get.banned.gui.config.settings;

import cheaters.get.banned.gui.config.Property;

import java.lang.reflect.Field;

public class SelectSetting extends Setting {

    public String[] options;

    public SelectSetting(Property annotation, Field field) {
        super(annotation, field);
        this.options = annotation.options();
    }

    @Override
    public boolean set(Object value) {
        if(((Number)value).intValue() > options.length-1) return super.set(0);
        if(((Number)value).intValue() < 0) return super.set(options.length-1);
        return super.set(value);
    }

}
