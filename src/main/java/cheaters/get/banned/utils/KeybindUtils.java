package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import net.minecraft.client.settings.KeyBinding;

import java.util.HashMap;

public class KeybindUtils {

    public static HashMap<String, KeyBinding> keyBindings = new HashMap<>();

    public static void register(String name, int key) {
        keyBindings.put(name, new KeyBinding(name, key, Shady.MOD_NAME));
    }

    public static boolean isPressed(String name) {
        return get(name).isPressed();
    }

    public static KeyBinding get(String name) {
        return keyBindings.get(name);
    }

    public static void rightClick() {
        if(!ReflectionUtils.invoke(Shady.mc, "func_147121_ag")) {
            ReflectionUtils.invoke(Shady.mc, "rightClickMouse");
        }
    }

    public static void leftClick() {
        if(!ReflectionUtils.invoke(Shady.mc, "func_147116_af")) {
            ReflectionUtils.invoke(Shady.mc, "clickMouse");
        }
    }

    public static void middleClick() {
        if(!ReflectionUtils.invoke(Shady.mc, "func_147112_ai")) {
            ReflectionUtils.invoke(Shady.mc, "middleClickMouse");
        }
    }

}
