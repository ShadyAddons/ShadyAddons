package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;

public class CloseScreenAction extends Action {

    public CloseScreenAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        Shady.mc.thePlayer.closeScreen();
    }

}
