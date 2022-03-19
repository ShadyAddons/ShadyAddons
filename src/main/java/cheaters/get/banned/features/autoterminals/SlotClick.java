package cheaters.get.banned.features.autoterminals;

import cheaters.get.banned.Shady;
import net.minecraft.inventory.Slot;

public class SlotClick {

    // Documentation: https://wiki.vg/index.php?title=Protocol&oldid=7368#Click_Window
    public enum ClickType {
        RIGHT_CLICK(0, 1),
        LEFT_CLICK(0, 0),
        MIDDLE_CLICK(3, 2);

        public final int mode;
        public final int button;
        ClickType(int mode, int button) {
            this.mode = mode;
            this.button = button;
        }


        @Override
        public String toString() {
            return "ClickType." + name() + "(" + mode + ", " + button + ")";
        }
    }

    private ClickType type;
    private Slot slot;

    public SlotClick(ClickType type, Slot slot) {
        this.type = type;
        this.slot = slot;
    }

    public void click(int windowId) {
        Shady.mc.playerController.windowClick(windowId, slot.slotNumber, type.button, type.mode, Shady.mc.thePlayer);
    }

    @Override
    public String toString() {
        return "Slot#" + slot.slotNumber + ", " + type.toString();
    }

}
