package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.utils.NetworkUtils;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

/**
 * {
 *     "name": "SwapPreviousHotbarAction"
 * }
 */

public class SwapPreviousHotbarAction extends Action {

    public static int slot = -1;

    public SwapPreviousHotbarAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() {
        if(slot > -1 && slot < 9) {
            Shady.mc.thePlayer.inventory.currentItem = slot;
            NetworkUtils.sendPacket(new C09PacketHeldItemChange(slot));
        }
    }

}
