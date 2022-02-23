package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChatReceivedTrigger extends Trigger {

    public String messageContains;
    public boolean removeFormatting;

    public ChatReceivedTrigger(RoutineElementData data) throws RoutineRuntimeException {
        super(data);

        messageContains = data.keyAsString("message_contains");
        removeFormatting = data.keyAsBool("remove_formatting");
    }

    @Override
    public boolean shouldTrigger(Event event) {
        // Will not receive canceled events by default
        if(event instanceof ClientChatReceivedEvent && ((ClientChatReceivedEvent) event).type == 1) {
            String messageContent = ((ClientChatReceivedEvent) event).message.getFormattedText();
            if(removeFormatting) messageContent = Utils.removeFormatting(messageContent);
            return messageContent.contains(messageContains);
        }

        return false;
    }

}
