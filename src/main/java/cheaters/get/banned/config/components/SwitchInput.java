package cheaters.get.banned.config.components;

import cheaters.get.banned.config.settings.BooleanSetting;
import net.minecraft.client.Minecraft;

public class SwitchInput extends ConfigInput {

    public BooleanSetting setting;

    public SwitchInput(BooleanSetting setting, int x, int y) {
        super(setting, x-25, y); // subtract width so button is right-aligned
        this.setting = setting;
        super.width = 25;
        super.height = 10;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        drawRect(xPosition, yPosition+3, xPosition+25, yPosition+6, white.getRGB());
        drawRect(setting.get(Boolean.class) ? xPosition+15 : xPosition, yPosition, setting.get(Boolean.class) ? xPosition+25 : xPosition+10, yPosition+10, setting.get(Boolean.class) ? green.getRGB() : red.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            setting.set(!setting.get(Boolean.class));
            return true;
        }

        return false;
    }

}
