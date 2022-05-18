package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.Utils;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeaveServerAction extends Action {

    public LeaveServerAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        Shady.mc.theWorld.sendQuittingDisconnectingPacket();
    }
}
