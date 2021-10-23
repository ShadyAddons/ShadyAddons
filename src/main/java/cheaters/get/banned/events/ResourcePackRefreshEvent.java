package cheaters.get.banned.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ResourcePackRefreshEvent extends Event {

    @Cancelable
    public static class Pre extends ResourcePackRefreshEvent {
    }

    public static class Post extends ResourcePackRefreshEvent {
    }

}
