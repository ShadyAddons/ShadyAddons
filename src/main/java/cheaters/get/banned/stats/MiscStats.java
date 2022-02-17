package cheaters.get.banned.stats;

import cheaters.get.banned.events.ShutdownEvent;
import com.google.gson.Gson;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

public class MiscStats {

    public enum Metric {
        BLOCKS_GHOSTED,
        CRYSTALS_REACHED,
        CONNECT_FOUR_WINS,
        CONNECT_FOUR_LOSSES,
        CHESTS_CLOSED,
        ITEMS_SOLD,
        ITEMS_SALVAGED,
        MATH_PROBLEMS_SOLVED,
        ITEMS_MACROED,
        WARDROBES_OPENED,
        COMMAND_PALETTE_OPENS
    }

    private static HashMap<Metric, Integer> data = new HashMap<>();
    public static int minutesSinceLastSend = 0;

    private static String toJson() {
        return new Gson().toJson(data);
    }

    public static void add(Metric metric) {
        Integer oldValue = data.get(metric);
        data.put(metric, (oldValue == null ? 0 : oldValue) + 1);
    }

    public static void send() {
        Analytics.collect("stats", toJson());
        data.clear();
    }

    @SubscribeEvent
    public void onShutdown(ShutdownEvent event) {
        send();
    }

}
