package cheaters.get.banned.configuration.components;

import cheaters.get.banned.configuration.Setting;
import net.minecraft.client.Minecraft;

public class SwitchButton extends ConfigButton {

    public SwitchButton(Setting setting, int x, int y) {
        super(setting, x, y);
        super.width = 25;
        super.height = 10;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        int x = xPosition;
        int y = yPosition;

        // ←↑ →↓
        drawRect(x, y+3, x+25, y+6, white.getRGB());
        drawRect(setting.enabled() ? x+15 : x, y, setting.enabled() ? x+25 : x+10, y+10, setting.enabled() ? green.getRGB() : red.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
            setting.update(!setting.enabled(), false);
            return true;
        }

        return false;
    }
}
