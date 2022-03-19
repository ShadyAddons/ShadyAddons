package cheaters.get.banned.gui.config.settings;

import cheaters.get.banned.gui.config.Property;

import java.lang.reflect.Field;

public class SpacerSetting extends ParentSetting implements DoNotSave {

    public SpacerSetting(Property annotation, Field field) {
        super(annotation, field);
    }

}
