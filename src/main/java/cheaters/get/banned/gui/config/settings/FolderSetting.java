package cheaters.get.banned.gui.config.settings;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.ConfigLogic;
import cheaters.get.banned.gui.config.Property;

import java.lang.reflect.Field;

public class FolderSetting extends ParentSetting {

    public FolderSetting(Property annotation, Field field) {
        super(annotation, field);
    }

    public boolean isChildEnabled() {
        for(Setting child : children) {
            if(child instanceof BooleanSetting && child.get(Boolean.class)) return true;
            if(child instanceof FolderSetting && ((FolderSetting) child).isChildEnabled()) return true;
        }
        return false;
    }

    public static boolean isEnabled(String name) {
        Setting setting = ConfigLogic.getSettingByName(name, Shady.settings);
        if(setting == null) return false;
        return ((FolderSetting) setting).isChildEnabled();
    }

}
