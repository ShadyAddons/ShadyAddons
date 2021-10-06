package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.properties.Property;

import java.lang.reflect.Field;

public class BooleanSetting extends ParentSetting {

    public BooleanSetting boundTo;

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

            if(boundTo != null && boundTo.get(Boolean.class) != get(Boolean.class)) {
                //((Setting) boundTo).set(!get(Boolean.class));
            }

            return super.set(value);
        } catch(Exception exception) {
            System.out.println("Failed to set "+name+" to "+value);
            exception.printStackTrace();
        }
        return false;
    }

}
