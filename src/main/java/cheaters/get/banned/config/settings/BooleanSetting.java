package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.properties.Property;

import java.lang.reflect.Field;

public class BooleanSetting extends ParentSetting {

    public BooleanSetting boundTo;

    public BooleanSetting(Property annotation, Field field) {
        super(annotation, field);
    }

    public boolean set(boolean value) {
        try {
            for(Setting child : children) {
                if(child instanceof BooleanSetting) child.set(false);
            }

            if(boundTo != null && (boolean)boundTo.get() != (boolean)get()) {
                boundTo.set(!(boolean)get());
            }

            return super.set(value);
        } catch(Exception exception) {
            System.out.println("Failed to set "+name+" to "+value);
            exception.printStackTrace();
        }
        return false;
    }

}
