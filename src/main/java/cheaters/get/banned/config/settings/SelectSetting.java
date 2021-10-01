package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.properties.Property;

import java.lang.reflect.Field;

public class SelectSetting extends Setting {

    public String[] options;

    public SelectSetting(Property annotation, Field field) {
        super(annotation, field);
        this.options = annotation.options();
    }

    public boolean set(int value) {
        if(value > options.length-1) return super.set(0);
        if(value < 0) return super.set(options.length-1);
        return super.set(value);
    }

}
