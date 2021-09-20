package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ItemKeybind {

    public ItemKeybind() {
        KeybindUtils.register("Use Ice Spray", Keyboard.KEY_I);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if(Config.iceSprayHotkey && Utils.inSkyBlock && KeybindUtils.get("Use Ice Spray").isPressed()) {
            if(Config.iceSprayHotkey && KeybindUtils.isPressed("Use Ice Spray")) useItem("ICE_SPRAY_WAND");
        }
    }

    public static void useItem(String itemId) {
        for(int i = 0; i < 9; i++) {
            ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
            if(Utils.getSkyBlockID(item).equals(itemId)) {
                int previousItem = Shady.mc.thePlayer.inventory.currentItem;
                Shady.mc.thePlayer.inventory.currentItem = i;
                Shady.mc.playerController.sendUseItem(Shady.mc.thePlayer, Shady.mc.theWorld, item);
                Shady.mc.thePlayer.inventory.currentItem = previousItem;
                return;
            }
        }

        Utils.sendModMessage("No &9"+itemId+"&r found in your hotbar");
    }

}
