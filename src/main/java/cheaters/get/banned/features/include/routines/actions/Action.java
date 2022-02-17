package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;

public abstract class Action {

    protected RoutineElementData data;

    public Action(RoutineElementData data) {
        this.data = data;
    }

    public abstract void doAction() throws RoutineRuntimeException;

    public int getRepeat() {
        Integer times = data.keyAsInt_noError("repeat");
        if(times == null) return 1;
        return times;
    }

}
