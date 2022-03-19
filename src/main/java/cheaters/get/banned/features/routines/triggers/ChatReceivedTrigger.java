package cheaters.get.banned.features.routines.triggers;

import cheaters.get.banned.events.PacketEvent;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChatReceivedTrigger extends Trigger {

    public String messageContains;

    public ChatReceivedTrigger(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        messageContains = data.keyAsString("message_contains");
    }

    @Override
    public boolean shouldTrigger(Event event) {
        if(event instanceof PacketEvent.ReceiveEvent) {
            S02PacketChat packet = (S02PacketChat) ((PacketEvent.ReceiveEvent) event).packet;
            String messageContent = packet.getChatComponent().getUnformattedText();
            messageContent = StringUtils.stripControlCodes(messageContent);
            return messageContent.contains(messageContains);
        }

        return false;
    }

}
