package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineException;
import cheaters.get.banned.utils.Utils;

public class UseCommandAction extends Action {

    private String command;

    public UseCommandAction(RoutineElementData data) throws RoutineException {
        super(data);
        command = data.keyAsString("command", true);
    }

    @Override
    public void doAction() {
        Utils.executeCommand("/" + command);
    }

}
