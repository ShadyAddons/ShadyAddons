package cheaters.get.banned.features;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.ReflectionUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class SpamRightClick {

    public SpamRightClick() {
        KeybindUtils.register("Autoclicker", Keyboard.KEY_Y);
    }

    private static boolean toggled = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeybindUtils.get("Autoclicker").isPressed()) {
            if(Config.autoClickerBurst) {
                for(int i = 0; i < 25; i++) {
                    ReflectionUtils.rightClick();
                }
            } else if(Config.autoClickerToggle) {
                toggled = !toggled;
                while(toggled) {
                    ReflectionUtils.rightClick();
                }
            }
        }
    }

}
