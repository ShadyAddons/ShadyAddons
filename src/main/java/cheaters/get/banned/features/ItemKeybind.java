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
        KeybindUtils.register("Use Ice Spray", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Plasmaflux", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Weird Tuba", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Gyrokinetic Wand", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Pigman Sword", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Wand of Atonement", Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if(Config.iceSprayHotkey && KeybindUtils.isPressed("Use Ice Spray")) useItem("ICE_SPRAY_WAND");
        if(Config.plasmafluxHotkey && KeybindUtils.isPressed("Use Plasmaflux")) useItem("PLASMAFLUX_POWER_ORB");
        if(Config.weirdTubaHotkey && KeybindUtils.isPressed("Use Weird Tuba")) useItem("WEIRD_TUBA");
        if(Config.gyrokineticWandHotkey && KeybindUtils.isPressed("Use Gyrokinetic Wand")) useItem("GYROKINETIC_WAND");
        if(Config.pigmanSwordHotkey && KeybindUtils.isPressed("Use Pigman Sword")) useItem("PIGMAN_SWORD");
        if(Config.wandOfAtonementHotkey && KeybindUtils.isPressed("Use Wand of Atonement")) useItem("WAND_OF_ATONEMENT");
    }

    public static void useItem(String itemId) {
        for(int i = 0; i < 9; i++) {
            ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
            if(itemId.equals(Utils.getSkyBlockID(item))) {
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
