package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;

public class ClickWindowAction extends Action {

    private int slot;
    private int mouseButton;
    private int mode;
    private int windowIdOffset;

    public ClickWindowAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        slot = data.keyAsInt("slot");
        mouseButton = data.keyAsInt("mouse_button");
        mode = data.keyAsInt("mode");
        windowIdOffset = data.keyAsInt("window_id_offset");
    }

    @Override
    public void doAction() {
        Shady.mc.playerController.windowClick(
                Shady.mc.thePlayer.openContainer.windowId + windowIdOffset,
                slot,
                mouseButton,
                mode,
                Shady.mc.thePlayer
        );
    }

}