package cheaters.get.banned.config.components;

import cheaters.get.banned.config.settings.*;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public abstract class ConfigInput extends GuiButton {

    public static Color white = new Color(255, 255, 255);
    public static Color green = new Color(85, 255, 85);
    public static Color red = new Color(255, 85, 85);
    public static Color transparent = new Color(255, 255, 255, 64);

    public Setting setting;

    public ConfigInput(Setting setting, int x, int y) {
        super(0, x, y, "");
        this.setting = setting;
    }

    public static ConfigInput buttonFromSetting(Setting setting, int x, int y) {
        switch(setting.annotation.type()) {
            case BOOLEAN:
                return new SwitchInput((BooleanSetting) setting, x, y);

            case CHECKBOX:
                return new CheckboxInput((BooleanSetting) setting, x, y);

            case FOLDER:
                return new FolderComponent((FolderSetting) setting, x, y);

            case NUMBER:
                return new NumberInput((NumberSetting) setting, x, y);

            case SELECT:
                return new SelectInput((SelectSetting) setting, x, y);

            default:
                return null;
        }
    }

}
