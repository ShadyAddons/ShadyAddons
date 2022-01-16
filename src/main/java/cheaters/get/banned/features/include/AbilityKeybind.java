package cheaters.get.banned.features.include;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class AbilityKeybind {

    public AbilityKeybind() {
        KeybindUtils.register("Use Normal Ability", Keyboard.KEY_X);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Config.normalAbilityKeybind && KeybindUtils.get("Use Normal Ability").isPressed() && Utils.inDungeon) {
            Shady.mc.thePlayer.dropOneItem(true);
        }
    }

}
