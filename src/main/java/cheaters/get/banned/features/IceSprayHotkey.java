package cheaters.get.banned.features;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.Setting;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;

public class IceSprayHotkey {

    public IceSprayHotkey() {
        KeybindUtils.register("Use Ice Spray", Keyboard.KEY_I);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if(Config.isEnabled(Setting.ICE_SPRAY_HOTKEY) && Utils.inSkyBlock && KeybindUtils.get("Use Ice Spray").isPressed()) {
            for(int i = 0; i < 9; i++) {
                ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
                String itemId = Utils.getSkyBlockID(item);
                if(itemId.equals("ICE_SPRAY_WAND")) {
                    int previousItem = Shady.mc.thePlayer.inventory.currentItem;
                    Shady.mc.thePlayer.inventory.currentItem = i;
                    Shady.mc.playerController.sendUseItem(Shady.mc.thePlayer, Shady.mc.theWorld, item);
                    Shady.mc.thePlayer.inventory.currentItem = previousItem;
                    return;
                }
            }
            Utils.sendModMessage("No &9Ice Spray Wand&r found in your hotbar");
        }
    }

}
