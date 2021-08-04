package shady.shady.shady.features;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.KeybindUtils;
import shady.shady.shady.utils.Utils;

public class AbilityKeybind {

    public AbilityKeybind() {
        KeybindUtils.register("Use Normal Ability", Keyboard.KEY_X);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Config.isEnabled(Setting.NORMAL_ABILITY_KEYBIND) && KeybindUtils.get("Use Normal Ability").isPressed() && Utils.inDungeon) {
            Shady.mc.thePlayer.dropOneItem(true);
        }
    }

}
