package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.Utils;
import org.apache.commons.lang3.SystemUtils;

public class DisplayNotificationAction extends Action {

    private String message;

    public DisplayNotificationAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        message = data.keyAsString("message");
        message = message.replace("\"", "");
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) {
            try {
                // https://apple.stackexchange.com/a/115373
                Runtime.getRuntime().exec("osascript -e 'display notification \"" + message + "\" with title \"ShadyAddons\"'");
            } catch(Exception ignored) {}
        } else {
            Utils.sendModMessage("§e" + message);
            Utils.sendMessage("§oNotifications are only supported on macOS for now");
        }
    }

}
