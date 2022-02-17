package cheaters.get.banned.gui.routines;

import cheaters.get.banned.features.include.routines.Routine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class RoutineComponent extends GuiButton {

    private Routine routine;

    public RoutineComponent(Routine routine, int x, int y) {
        super(0, x, y, 300, 9, routine.name);
        this.routine = routine;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);
    }

}
