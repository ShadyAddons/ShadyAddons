package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
        KeybindUtils.register("Use Rogue Sword", Keyboard.KEY_NONE);
        KeybindUtils.register("Use Fishing Rod", Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if(Config.iceSprayHotkey && KeybindUtils.isPressed("Use Ice Spray")) {
            if(!useSkyBlockItem("ICE_SPRAY_WAND", true)) {
                sendMissingItemMessage("Ice Spray Wand");
            };
        }

        if(Config.powerOrbHotkey && KeybindUtils.isPressed("Use Power Orb")) {
            if(!useSkyBlockItem("PLASMAFLUX_POWER_ORB", true) && !useSkyBlockItem("OVERFLUX_POWER_ORB", true) && !useSkyBlockItem("MANA_FLUX_POWER_ORB", true) && !useSkyBlockItem("RADIANT_POWER_ORB", true)){
                sendMissingItemMessage("Power Orb");
            }
        }

        if(Config.weirdTubaHotkey && KeybindUtils.isPressed("Use Weird Tuba")) {
            if(!useSkyBlockItem("WEIRD_TUBA", true)) {
                sendMissingItemMessage("Weird Tuba");
            }
        }

        if(Config.gyrokineticWandHotkey && KeybindUtils.isPressed("Use Gyrokinetic Wand")) {
            if(!useSkyBlockItem("GYROKINETIC_WAND", false)) {
                sendMissingItemMessage("Gyrokinetic Wand");
            }
        }

        if(Config.pigmanSwordHotkey && KeybindUtils.isPressed("Use Pigman Sword")) {
            if(!useSkyBlockItem("PIGMAN_SWORD", true)) {
                sendMissingItemMessage("Pigman Sword");
            }
        }

        if(Config.healingWandHotkey && KeybindUtils.isPressed("Use Healing Wand")) {
            if(!useSkyBlockItem("WAND_OF_ATONEMENT", true) && !useSkyBlockItem("WAND_OF_RESTORATION", true) && !useSkyBlockItem("WAND_OF_MENDING", true) && !useSkyBlockItem("WAND_OF_HEALING", true)) {
                sendMissingItemMessage("Healing Wand");
            }
        }

        if(Config.rogueSwordHotkey && KeybindUtils.isPressed("Use Rogue Sword")) {
            if(!useRogueSword()) {
                sendMissingItemMessage("Rogue Sword");
            }
        }

        if(Config.fishingRodHotkey && KeybindUtils.isPressed("Use Fishing Rod")) {
            if(!useVanillaItem(Items.fishing_rod)) {
                sendMissingItemMessage("Fishing Rod");
            }
        }
    }

    public static boolean useSkyBlockItem(String itemId, boolean rightClick) {
        for(int i = 0; i < 9; i++) {
            ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
            if(itemId.equals(Utils.getSkyBlockID(item))) {
                int previousItem = Shady.mc.thePlayer.inventory.currentItem;
                Shady.mc.thePlayer.inventory.currentItem = i;
                if(rightClick) {
                    Shady.mc.playerController.sendUseItem(Shady.mc.thePlayer, Shady.mc.theWorld, item);
                } else {
                    KeybindUtils.leftClick();
                }
                Shady.mc.thePlayer.inventory.currentItem = previousItem;
                return true;
            }
        }

        return false;
    }

    public static boolean useRogueSword() {
        for(int i = 0; i < 9; i++) {
            ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
            if("ROGUE_SWORD".equals(Utils.getSkyBlockID(item))) {
                int previousItem = Shady.mc.thePlayer.inventory.currentItem;
                Shady.mc.thePlayer.inventory.currentItem = i;
                for(int j = 0; j < 40; j++) {
                    Shady.mc.playerController.sendUseItem(Shady.mc.thePlayer, Shady.mc.theWorld, item);
                }
                Shady.mc.thePlayer.inventory.currentItem = previousItem;
                return true;
            }
        }
        return false;
    }

    public static boolean useVanillaItem(Item item) {
        for(int i = 0; i < 9; i++) {
            ItemStack itemStack = Shady.mc.thePlayer.inventory.getStackInSlot(i);
            if(itemStack.getItem() == item) {
                int previousItem = Shady.mc.thePlayer.inventory.currentItem;
                Shady.mc.thePlayer.inventory.currentItem = i;
                Shady.mc.playerController.sendUseItem(Shady.mc.thePlayer, Shady.mc.theWorld, itemStack);
                Shady.mc.thePlayer.inventory.currentItem = previousItem;
                return true;
            }
        }

        return false;
    }

    private static void sendMissingItemMessage(String itemName) {
        Utils.sendModMessage("No &9"+itemName+"&r found in your hotbar");
    }

}
