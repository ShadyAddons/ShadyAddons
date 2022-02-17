package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.Utils;

public class UseCommandAction extends Action {

    private String command;

    public UseCommandAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        command = data.keyAsString("command");
    }

    @Override
    public void doAction() {
        Utils.executeCommand("/" + command);
    }

}
