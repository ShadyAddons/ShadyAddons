package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.element.RoutineElement;
import cheaters.get.banned.features.include.routines.element.RoutineElementData;

public abstract class Action extends RoutineElement {

    protected RoutineElementData data;

    public Action(RoutineElementData data) {
        super(data);
        this.data = data;
    }

    public abstract void doAction();

}
