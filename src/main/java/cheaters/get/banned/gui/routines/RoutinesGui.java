package cheaters.get.banned.gui.routines;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.Routine;
import cheaters.get.banned.features.routines.Routines;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class RoutinesGui extends GuiScreen {

    private static final int wth = 300;
    private static final int borderColor = new Color(255, 255, 255, 64).getRGB();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for(Routine value : Routines.routines.values()) {
            new RoutineComponent(value, 0, 0).drawButton(Shady.mc, mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        int yOffset = 50;
        final int xOffset = (width - wth) / 2;

        for(Routine routine : Routines.routines.values()) {
            buttonList.add(new RoutineComponent(routine, xOffset, yOffset));
            yOffset += 9;
            drawRect(xOffset, yOffset, yOffset + wth, yOffset + 1, borderColor);
            yOffset += 1;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
