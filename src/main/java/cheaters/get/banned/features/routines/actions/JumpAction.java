package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;

public class JumpAction extends Action {
    public JumpAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() {
        if (!Shady.mc.thePlayer.isAirBorne) Shady.mc.thePlayer.jump();
    }

}
