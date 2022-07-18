package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.utils.KeybindUtils;

public class SneakAction extends Action {
    boolean state;

    public SneakAction(RoutineElementData data) {
        super(data);
        state = data.keyAsBool("sneaking", !Shady.mc.thePlayer.isSneaking());
    }

    @Override
    public void doAction() {
        Shady.mc.thePlayer.setSneaking(state);
    }

}
