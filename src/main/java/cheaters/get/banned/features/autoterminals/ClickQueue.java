package cheaters.get.banned.features.autoterminals;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.Iterator;

public class ClickQueue implements Iterable<SlotClick> {

    private ArrayList<SlotClick> clicks = new ArrayList<>();

    /**
     * Adds a click action to the queue
     */
    public void add(SlotClick.ClickType type, Slot slot) {
        clicks.add(new SlotClick(type, slot));
    }

    /**
     * Adds a right-click action to the queue
     */
    public void rightClick(Slot slot) {
        add(SlotClick.ClickType.RIGHT_CLICK, slot);
    }

    /**
     * Adds a left-click action to the queue
     */
    public void leftClick(Slot slot) {
        add(SlotClick.ClickType.LEFT_CLICK, slot);
    }

    /**
     * Adds a middle-click action to the queue
     */
    public void middleClick(Slot slot) {
        add(SlotClick.ClickType.MIDDLE_CLICK, slot);
    }

    /**
     * Removes all click actions from the queue
     */
    public void clear() {
        clicks.clear();
    }

    @Override
    public Iterator<SlotClick> iterator() {
        return clicks.iterator();
    }

    /**
     * Clicks the frist item in the queue and returns the new
     * number of window clicks. The value is irrelevant if half-trip
     * mode is enabled.
     */
    public int clickFirst(int windowClicks) {
        SlotClick click = clicks.get(0);
        click.click(Shady.mc.thePlayer.openContainer.windowId + windowClicks);

        if(Config.terminalHalfTrip) {
            windowClicks++;
            clicks.remove(0);
        }

        return windowClicks;
    }

    public int size() {
        return clicks.size();
    }

    public void debug() {
        for(SlotClick click : clicks) {
            System.out.println(click.toString());
        }
    }

}
