package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineException;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeybindTrigger extends Trigger {

    private int keyCode;

    public KeybindTrigger(RoutineElementData data) throws RoutineException {
        super(data);
        super.shouldCancelEvent = false;

        String letter = data.keyAsString("key", true).toUpperCase().substring(0, 1);
        keyCode = Keyboard.getKeyIndex(letter);
        if(keyCode == Keyboard.KEY_NONE) {
            throw new RoutineException("Invalid key character '" + letter + "' in trigger");
        }
    }

    @Override
    public boolean shouldTrigger(Event event) {
        return event instanceof InputEvent.KeyInputEvent && Keyboard.getEventKey() == keyCode && Keyboard.getEventKeyState();
    }

}