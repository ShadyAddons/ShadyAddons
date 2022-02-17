package cheaters.get.banned.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.time.LocalDateTime;

public class MinuteEvent extends Event {

    public LocalDateTime dateTime;

    public MinuteEvent() {
        dateTime = LocalDateTime.now();
    }

}
