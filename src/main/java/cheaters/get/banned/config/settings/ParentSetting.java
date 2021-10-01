package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.properties.Property;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class ParentSetting extends Setting {

    public ArrayList<Setting> children = new ArrayList<>();

    public ParentSetting(Property annotation, Field field) {
        super(annotation, field);
    }

    public ArrayList<Setting> getChildren(ArrayList<Setting> settings) {
        ArrayList<Setting> children = new ArrayList<>();

        for(Setting setting : settings) {
            if(setting.parent == this) {
                children.add(setting);
            }
        }

        return children;
    }

}
