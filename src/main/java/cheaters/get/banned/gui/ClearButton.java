package cheaters.get.banned.gui;

import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class ClearButton extends GuiButton {

    public boolean shadow = true;

    public ClearButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public ClearButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(visible) {
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition+width && mouseY < yPosition+height;
            Color color = hovered ? new Color(15, 15, 15, 64) : new Color(0, 0, 0, 64);
            drawRect(xPosition, yPosition, xPosition+width, yPosition+height, color.getRGB());
            FontUtils.drawCenteredString(displayString, xPosition+width/2, yPosition+height/2, shadow);
        }
    }

}
