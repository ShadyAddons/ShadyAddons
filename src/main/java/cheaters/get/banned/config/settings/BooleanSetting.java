package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.Property;

import java.lang.reflect.Field;

public class BooleanSetting extends ParentSetting {

    public Property.Type type = Property.Type.BOOLEAN;

    public BooleanSetting(Property annotation, Field field, Property.Type type) {
        super(annotation, field);
        this.type = type;
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
