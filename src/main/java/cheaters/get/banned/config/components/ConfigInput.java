package cheaters.get.banned.config.components;

import cheaters.get.banned.config.settings.BooleanSetting;
import cheaters.get.banned.config.settings.NumberSetting;
import cheaters.get.banned.config.settings.SelectSetting;
import cheaters.get.banned.config.settings.Setting;
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

    public static ConfigInput createButtonForSetting(Setting setting, int x, int y) {
        if(setting instanceof BooleanSetting) {
            return new SwitchInput((BooleanSetting) setting, x, y);
        } else if(setting instanceof NumberSetting) {
            return new NumberInput((NumberSetting) setting, x, y);
        } else if(setting instanceof SelectSetting) {
            return new SelectInput((SelectSetting) setting, x, y);
        }
        return null;
    }

}
