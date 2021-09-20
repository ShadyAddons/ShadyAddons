package cheaters.get.banned.config.components;

import cheaters.get.banned.config.Setting;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class ConfigButton extends GuiButton {

    public static Color white = new Color(255, 255, 255);
    public static Color green = new Color(85, 255, 85);
    public static Color red = new Color(255, 85, 85);
    public static Color transparent = new Color(255, 255, 255, 64);

    public Setting setting;

    public ConfigButton(Setting setting, int x, int y) {
        super(0, x, y, "");
        this.setting = setting;
    }

}
