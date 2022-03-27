package cheaters.get.banned.gui.config.components;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.settings.SelectSetting;
import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.Minecraft;

public class SelectInput extends ConfigInput {

    private int leftWidth = FontUtils.getStringWidth("<");
    private int rightWidth = FontUtils.getStringWidth(">");
    private int gap = 3;
    private boolean leftHovered = false;
    private boolean rightHovered = false;

    public SelectSetting setting;

    public SelectInput(SelectSetting setting, int x, int y) {
        super(setting, x, y);
        this.setting = setting;
        height = 10;
        updateText();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        rightHovered = mouseX>=xPosition-rightWidth-gap && mouseY>=yPosition && mouseX<xPosition && mouseY<yPosition+height;
        leftHovered = mouseX>=xPosition-width && mouseY>=yPosition && mouseX<xPosition-width+ leftWidth +gap && mouseY<yPosition+height;

        FontUtils.drawString((leftHovered ?"§a":"§7")+"<", xPosition-width, yPosition, false);
        FontUtils.drawString(displayString, xPosition-width+leftWidth +gap, yPosition, false);
        FontUtils.drawString((rightHovered ?"§a":"§7")+">", xPosition-rightWidth, yPosition, false);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(rightHovered || leftHovered) {
            if(rightHovered) setting.set(setting.get(Integer.class)+1);
            if(leftHovered) setting.set(setting.get(Integer.class)-1);
            updateText();
            return true;
        }
        return false;
    }

    public void updateText() {
        displayString = setting.options[setting.get(Integer.class)];
        width = FontUtils.getStringWidth(displayString) + rightWidth+leftWidth + gap*2;
    }

}
