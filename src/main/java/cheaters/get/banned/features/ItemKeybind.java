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
        KeybindUtils.register("Use Power Orb", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Weird Tuba", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Gyrokinetic Wand", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Pigman Sword", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Healing Wand", Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if(Config.iceSprayHotkey && KeybindUtils.isPressed("Use Ice Spray")) {
            if(!useItem("ICE_SPRAY_WAND")) {
                sendUsageMessage("Ice Spray Wand");
            };
        }

        if(Config.powerOrbHotkey && KeybindUtils.isPressed("Use Power Orb")) {
            if(!useItem("PLASMAFLUX_POWER_ORB") && !useItem("OVERFLUX_POWER_ORB") && !useItem("MANA_FLUX_POWER_ORB") && !useItem("RADIANT_POWER_ORB")){
                sendUsageMessage("Power Orb");
            }
        }

        if(Config.weirdTubaHotkey && KeybindUtils.isPressed("Use Weird Tuba")) {
            if(!useItem("WEIRD_TUBA")) {
                sendUsageMessage("Weird Tuba");
            }
        }

        if(Config.gyrokineticWandHotkey && KeybindUtils.isPressed("Use Gyrokinetic Wand")) {
            if(!useItem("GYROKINETIC_WAND")) {
                sendUsageMessage("Gyrokinetic Wand");
            }
        }

        if(Config.pigmanSwordHotkey && KeybindUtils.isPressed("Use Pigman Sword")) {
            if(!useItem("PIGMAN_SWORD")) {
                sendUsageMessage("Pigman Sword");
            }
        }

        if(Config.healingWandHotkey && KeybindUtils.isPressed("Use Healing Wand")) {
            if(!useItem("WAND_OF_ATONEMENT") && !useItem("WAND_OF_RESTORATION") && !useItem("WAND_OF_MENDING") && !useItem("WAND_OF_HEALING")) {
                sendUsageMessage("Healing Wand");
            }
        }
    }

    public static boolean useItem(String itemId) {
        for(int i = 0; i < 9; i++) {
            ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
            if(itemId.equals(Utils.getSkyBlockID(item))) {
                int previousItem = Shady.mc.thePlayer.inventory.currentItem;
                Shady.mc.thePlayer.inventory.currentItem = i;
                Shady.mc.playerController.sendUseItem(Shady.mc.thePlayer, Shady.mc.theWorld, item);
                Shady.mc.thePlayer.inventory.currentItem = previousItem;
                return true;
            }
        }

        return false;
    }

    private static void sendUsageMessage(String itemName) {
        Utils.sendModMessage("No &9"+itemName+"&r found in your hotbar");
    }

}
