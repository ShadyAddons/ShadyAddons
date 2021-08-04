package shady.shady.shady.features;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.KeybindUtils;
import shady.shady.shady.utils.ReflectionUtils;

public class SpamRightClick {

    public SpamRightClick() {
        KeybindUtils.register("Spam Right-Click", Keyboard.KEY_Y);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Config.isEnabled(Setting.SPAM_RIGHT_CLICK) && KeybindUtils.get("Spam Right-Click").isPressed()) {
            for(int i = 0; i < 25; i++) {
                // DEV: ReflectionUtils.invoke(Shady.mc.getClass(), "rightClickMouse");
                ReflectionUtils.invoke(Shady.mc.getClass(), "func_147121_ag");
            }
        }
    }

}
