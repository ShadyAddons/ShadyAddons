package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.NetworkUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

import java.util.function.Predicate;

/**
 * {
 *     "name": "SwapHotbarAction",
 *     "slot_number": int,
 *     "skyblock_id": string,
 *     "name_contains": string
 * }
 */

public class SwapHotbarAction extends Action {

    private Integer slot;
    private String skyblockId;
    private String nameContains;

    public SwapHotbarAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);

        slot = data.keyAsInt_noError("slot_number");
        if(slot != null) return;

        skyblockId = data.keyAsString_noError("skyblock_id");
        if(skyblockId != null) return;

        nameContains = data.keyAsString_noError("name_contains");
        if(nameContains == null) throw new RoutineRuntimeException("Invalid value(s) for SwapHotbarAction");
    }

    @Override
    public void doAction() {
        int finalSlot = -1;

        if(slot != null) finalSlot = slot;
        if(skyblockId != null) finalSlot = findHotbarSlot(itemStack -> Utils.getSkyBlockID(itemStack).equals(skyblockId));
        if(nameContains != null) finalSlot = findHotbarSlot(itemStack -> itemStack.getDisplayName().contains(nameContains));

        if(finalSlot > -1 && finalSlot < 9) {
            SwapPreviousHotbarAction.slot = Shady.mc.thePlayer.inventory.currentItem;
            NetworkUtils.sendPacket(new C09PacketHeldItemChange(finalSlot));
            Shady.mc.thePlayer.inventory.currentItem = finalSlot;
        }
    }

    /**
     * Returns a slot number that for the first hotbar item matching the predicate
     *
     * @param predicate A {@link Predicate} to test each {@link ItemStack} in the hotbar
     * @return Returns the slot, or -1 if no slot matches the predicate
     */
    public static int findHotbarSlot(Predicate<ItemStack> predicate) {
        for(int i = 0; i < 9; i++) {
            ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
            if(item == null) continue;
            if(predicate.test(item)) return i;
        }

        return -1;
    }

}
