package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineException;
import cheaters.get.banned.features.include.routines.element.RoutineElementData;
import cheaters.get.banned.utils.Utils;

public class OpenWebsiteAction extends Action {

    private String url;

    public OpenWebsiteAction(RoutineElementData data) throws RoutineException {
        super(data);
        url = data.keyAsString("url");
    }

    @Override
    public void doAction() {
        Utils.openUrl(url);
    }

}
