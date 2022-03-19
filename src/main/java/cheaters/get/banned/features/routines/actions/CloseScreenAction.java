package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;

public class CloseScreenAction extends Action {

    public CloseScreenAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        Shady.mc.thePlayer.closeScreen();
    }

}
