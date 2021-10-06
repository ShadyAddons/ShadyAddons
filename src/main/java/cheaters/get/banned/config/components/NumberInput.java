package cheaters.get.banned.config.components;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.settings.NumberSetting;
import net.minecraft.client.Minecraft;

public class NumberInput extends ConfigInput {

    private int minusWidth = Shady.mc.fontRendererObj.getStringWidth("-");
    private int plusWidth = Shady.mc.fontRendererObj.getStringWidth("+");
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

        Shady.mc.fontRendererObj.drawString((minusHovered?"§c":"§7")+"-", xPosition-width, yPosition, -1);
        Shady.mc.fontRendererObj.drawString(displayString, xPosition-width+minusWidth+gap, yPosition, -1);
        Shady.mc.fontRendererObj.drawString((plusHovered?"§a":"§7")+"+", xPosition-plusWidth, yPosition, -1);
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
        width = Shady.mc.fontRendererObj.getStringWidth(displayString) + plusWidth+minusWidth + gap*2;
    }

}
