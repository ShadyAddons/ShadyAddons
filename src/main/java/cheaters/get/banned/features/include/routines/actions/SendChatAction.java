package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineException;
import cheaters.get.banned.utils.Utils;

/**
 * {
 *     "name": "SendChatAction",
 *     "message": string
 * }
 */

public class SendChatAction extends Action {

    private String message;

    public SendChatAction(RoutineElementData data) throws RoutineException {
        super(data);
        message = data.keyAsString("message", true);
    }

    @Override
    public void doAction() {
        Utils.sendMessageAsPlayer(message);
    }

}
