package cheaters.get.banned.features.autoterminals;

import net.minecraft.inventory.Slot;

import java.util.List;

public abstract class SolverBase {

    public abstract ClickQueue getClicks(List<Slot> inventory, String chestName);

    private static void clickSlot(int index, int button, int mode) {
        // Shady.mc.playerController.windowClick(window)
    }

    public static void leftClickSlot(int index) {

    }

    public static void middleClickSlot(int index) {

    }

}
