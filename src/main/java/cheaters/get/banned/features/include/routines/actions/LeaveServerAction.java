package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.routines.RoutineElementData;
import net.minecraft.client.gui.GuiMainMenu;

public class LeaveServerAction extends Action {

    public LeaveServerAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() {
        Shady.mc.theWorld.sendQuittingDisconnectingPacket();
        Shady.mc.loadWorld(null);
        Shady.mc.displayGuiScreen(new GuiMainMenu());
    }

}
