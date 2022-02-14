package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;

public abstract class Action {

    protected RoutineElementData data;

    public Action(RoutineElementData data) {
        this.data = data;
    }

    public abstract void doAction();

}
