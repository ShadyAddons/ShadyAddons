package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.features.include.routines.RoutineException;
import cheaters.get.banned.features.include.routines.element.RoutineElementData;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeybindTrigger extends Trigger<InputEvent.KeyInputEvent> {

    private int keyCode;

    public KeybindTrigger(RoutineElementData data) throws RoutineException {
        super(InputEvent.KeyInputEvent.class, data);
        String letter = data.keyAsString("key").toUpperCase().substring(0, 1);
        keyCode = Keyboard.getKeyIndex(letter);
        if(keyCode == Keyboard.KEY_NONE) {
            throw new RoutineException("Invalid key character '" + letter + "' in trigger");
        }
    }

    @Override
    public boolean shouldTrigger(InputEvent.KeyInputEvent event) {
        return Keyboard.getEventKey() == keyCode && Keyboard.getEventKeyState();
    }

}
