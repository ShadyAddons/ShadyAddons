package cheaters.get.banned.gui.config.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class Scrollbar extends GuiButton {

    public Scrollbar(int y, int viewport, int contentHeight, int scrollOffset, int x, boolean hovered) {
        super(0, x, y, "");
        yPosition += Math.round((scrollOffset/(float)contentHeight)*viewport);
        width = 5;
        height = contentHeight > viewport ? Math.round((viewport/(float)contentHeight)*viewport) : 0;
        this.hovered = hovered;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        hovered = mousePressed(mc, mouseX, mouseY);
        drawRect(xPosition, yPosition, xPosition+width, yPosition+height, hovered ? ConfigInput.white.getRGB() : ConfigInput.transparent.getRGB());
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
    }

}
