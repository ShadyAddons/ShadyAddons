package cheaters.get.banned.features.include.routines;

import cheaters.get.banned.features.include.routines.actions.Action;
import cheaters.get.banned.features.include.routines.triggers.Trigger;

public class RoutineElementFactory {

    public static Trigger createTrigger(String name, RoutineElementData data) throws RoutineException {
        try {
            return (Trigger) Class.forName("cheaters.get.banned.features.include.routines.triggers." + name).getConstructor(RoutineElementData.class).newInstance(data);
        } catch(Exception ignored) {
            throw new RoutineException("Trigger with name '" + name + "' is invalid");
        }
    }

    public static Action createAction(String name, RoutineElementData data) throws RoutineException {
        try {
            return (Action) Class.forName("cheaters.get.banned.features.include.routines.actions." + name).getConstructor(RoutineElementData.class).newInstance(data);
        } catch(Exception ignored) {
            throw new RoutineException("Action with name '" + name + "' is invalid");
        }
    }

}
