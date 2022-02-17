package cheaters.get.banned.gui.config.components;

import cheaters.get.banned.gui.config.settings.ButtonSetting;
import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ButtonInput extends ConfigInput {

    public ButtonSetting setting;

    public ButtonInput(ButtonSetting setting, int x, int y) {
        super(setting, x, y);
        this.setting = setting;
        super.width = Math.round(FontUtils.getStringWidth(setting.buttonText) * 0.9f) + 6;
        super.height = Math.round(9 * 0.9f) + 2;
        super.xPosition -= width;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition+width && mouseY < yPosition+height;
        Color color = hovered ? new Color(231, 231, 231, 64) : new Color(182, 182, 182, 64);
        drawRect(xPosition, yPosition, xPosition+width, yPosition+height, color.getRGB());
        FontUtils.drawScaledCenteredString(setting.buttonText, 0.9f, xPosition+width/2, yPosition+height/2 + 1, false);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(hovered) {
            setting.run();
            return true;
        }
        return false;
    }

}
