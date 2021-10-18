package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.Property;

import java.lang.reflect.Field;

public class BooleanSetting extends ParentSetting {

    public BooleanSetting(Property annotation, Field field) {
        super(annotation, field);
    }

    @Override
    public boolean set(Object value) {
        try {
            for(Setting child : children) {
                if(child instanceof BooleanSetting) child.set(false);
                if(child instanceof SelectSetting) child.set(0);
            }

            return super.set(value);
        } catch(Exception exception) {
            System.out.println("Failed to set "+name+" to "+value);
            exception.printStackTrace();
        }
        return false;
    }

}
