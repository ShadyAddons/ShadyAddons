package cheaters.get.banned.config.components;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.settings.SelectSetting;
import net.minecraft.client.Minecraft;

public class SelectInput extends ConfigInput {

    private int leftWidth = Shady.mc.fontRendererObj.getStringWidth("<");
    private int rightWidth = Shady.mc.fontRendererObj.getStringWidth(">");
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

        Shady.mc.fontRendererObj.drawString((leftHovered ?"§a":"§7")+"<", xPosition-width, yPosition, -1);
        Shady.mc.fontRendererObj.drawString(displayString, xPosition-width+leftWidth +gap, yPosition, -1);
        Shady.mc.fontRendererObj.drawString((rightHovered ?"§a":"§7")+">", xPosition-rightWidth, yPosition, -1);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(rightHovered || leftHovered) {
            if(rightHovered) setting.set((int)setting.get()+1);
            if(leftHovered) setting.set((int)setting.get()-1);
            updateText();
            return true;
        }
        return false;
    }

    public void updateText() {
        displayString = setting.options[(int)setting.get()];
        width = Shady.mc.fontRendererObj.getStringWidth(displayString) + rightWidth+leftWidth + gap*2;
    }

}
