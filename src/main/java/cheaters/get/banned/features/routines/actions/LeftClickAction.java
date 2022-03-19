package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.utils.KeybindUtils;

public class LeftClickAction extends Action {

    public LeftClickAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() {
        KeybindUtils.leftClick();
    }

}
