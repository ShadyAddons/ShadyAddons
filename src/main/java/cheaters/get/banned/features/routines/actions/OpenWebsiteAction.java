package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.Utils;

/**
 * {
 *     "name": "OpenWebsiteAction",
 *     "url": string
 * }
 */

public class OpenWebsiteAction extends Action {

    private String url;

    public OpenWebsiteAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        url = data.keyAsString("url");
    }

    @Override
    public void doAction() {
        Utils.openUrl(url);
    }

}
