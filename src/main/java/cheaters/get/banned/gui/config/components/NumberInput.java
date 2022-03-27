package cheaters.get.banned.gui.config.components;

import cheaters.get.banned.gui.config.settings.NumberSetting;
import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.Minecraft;

public class NumberInput extends ConfigInput {

    private int minusWidth = FontUtils.getStringWidth("-");
    private int plusWidth = FontUtils.getStringWidth("+");
    private int gap = 3;
    private boolean minusHovered = false;
    private boolean plusHovered = false;

    public NumberSetting setting;

    public NumberInput(NumberSetting setting, int x, int y) {
        super(setting, x, y);
        this.setting = setting;
        height = 10;
        updateText();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        plusHovered = mouseX>=xPosition-plusWidth-gap && mouseY>=yPosition && mouseX<xPosition && mouseY<yPosition+height;
        minusHovered = mouseX>=xPosition-width && mouseY>=yPosition && mouseX<xPosition-width+minusWidth+gap && mouseY<yPosition+height;

        //                    - width +                  |---- xPosition
        //     - hitbox                        + hitbox
        // |-------------|------------------|------------|
        // minusWidth     displayStringWidth     plusWidth
        // pro tip: it helps to trace your finger along this diagram

        FontUtils.drawString((minusHovered?"§c":"§7")+"-", xPosition-width, yPosition, false);
        FontUtils.drawString(displayString, xPosition-width+minusWidth+gap, yPosition, false);
        FontUtils.drawString((plusHovered?"§a":"§7")+"+", xPosition-plusWidth, yPosition, false);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(plusHovered || minusHovered) {
            if(plusHovered) setting.set(setting.get(Integer.class)+setting.step);
            if(minusHovered) setting.set(setting.get(Integer.class)-setting.step);
            updateText();
            return true;
        }
        return false;
    }

    public void updateText() {
        displayString = (setting.prefix == null ? "" : setting.prefix) + setting.get(Integer.class) + (setting.suffix == null ? "" : setting.suffix);
        width = FontUtils.getStringWidth(displayString) + plusWidth+minusWidth + gap*2;
    }

}
