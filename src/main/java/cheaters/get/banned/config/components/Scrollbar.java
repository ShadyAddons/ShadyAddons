package cheaters.get.banned.config.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class Scrollbar extends GuiButton {

    public Scrollbar(int viewport, int contentHeight, int scrollOffset, int x, boolean scrolling) {
        super(0, x, 100+scrollOffset, "");
        width = 5;
        height = contentHeight > viewport ? Math.round((viewport/(float)contentHeight)*viewport) : 0;
        hovered = scrolling;
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
