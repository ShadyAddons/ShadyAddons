package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.ThreadUtils;

/**
 * {
 *     "name": "WaitAction",
 *     "ms": int
 * }
 */

public class WaitAction extends Action {

    private int ms;

    public WaitAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        ms = data.keyAsInt("ms");
    }

    @Override
    public void doAction() {
        ThreadUtils.sleep(ms);
    }

}
