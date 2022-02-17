package cheaters.get.banned.gui.config.components;

import cheaters.get.banned.gui.config.settings.BooleanSetting;
import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.Minecraft;

public class CheckboxInput extends ConfigInput {

    public BooleanSetting setting;

    public CheckboxInput(BooleanSetting setting, int x, int y) {
        super(setting, x, y);
        this.setting = setting;
        super.width = 9;
        super.height = 9;
        super.xPosition -= 9;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

        drawRect(xPosition, yPosition, xPosition+width, yPosition+height, white.getRGB());

        if(setting.get(Boolean.class)) {
            FontUtils.drawString("§0x", xPosition+2, yPosition, false);
        } else if(hovered) {
            FontUtils.drawString("§7x", xPosition+2, yPosition, false);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(hovered) {
            setting.set(!setting.get(Boolean.class));
            return true;
        }
        return false;
    }

}
