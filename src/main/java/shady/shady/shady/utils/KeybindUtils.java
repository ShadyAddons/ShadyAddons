package shady.shady.shady.utils;

import net.minecraft.client.settings.KeyBinding;
import shady.shady.shady.Shady;

import java.util.HashMap;

public class KeybindUtils {

    public static HashMap<String, KeyBinding> keyBindings = new HashMap<>();

    public static void register(String name, int key) {
        keyBindings.put(name, new KeyBinding(name, key, Shady.MODNAME));
    }

    public static KeyBinding get(String name) {
        return keyBindings.get(name);
    }

}
