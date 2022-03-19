package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.Utils;

/**
 * {
 *     "name": "SendChatAction",
 *     "message": string
 * }
 */

public class SendChatAction extends Action {

    private String message;

    public SendChatAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        message = data.keyAsString("message");
    }

    @Override
    public void doAction() {
        Utils.sendMessageAsPlayer(message);
    }

}
