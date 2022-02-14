package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.routines.RoutineElementData;

/**
 * {
 *     "name": "QuitMinecraftAction"
 * }
 */

public class QuitMinecraftAction extends Action {

    public QuitMinecraftAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() {
        Shady.mc.shutdown();
    }

}
