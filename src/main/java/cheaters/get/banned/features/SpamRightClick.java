package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.configuration.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.ReflectionUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class SpamRightClick {

    public SpamRightClick() {
        KeybindUtils.register("Spam Right-Click", Keyboard.KEY_Y);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Config.autoClickerBurst && KeybindUtils.get("Spam Right-Click").isPressed()) {
            for(int i = 0; i < 25; i++) {
                // DEV: ReflectionUtils.invoke(Shady.mc.getClass(), "rightClickMouse");
                ReflectionUtils.invoke(Shady.mc.getClass(), "func_147121_ag");
            }
        }
    }

}
