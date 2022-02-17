package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.Utils;

public class DisplayMessageAction extends Action {

    private String message;

    public DisplayMessageAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        message = data.keyAsString("message");
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        Utils.sendMessage(message);
    }

}
