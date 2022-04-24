package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Optional;

public class AutoBlazeDagger {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        // TODO: Add config
        if (!Config.autoBlazeDagger || !Utils.inSkyBlock || !LocationUtils.onIsland(LocationUtils.Island.CRIMSON_ISLE)) return;
        int firedustSlot = -1, twilightSlot = -1;
        for (int i = 0; i < 9; i++) {
            String itemID = Utils.getSkyBlockID(Shady.mc.thePlayer.inventory.getStackInSlot(i));
            if (itemID.equals("FIREDUST_DAGGER") && firedustSlot == -1) firedustSlot = i;
            if (itemID.equals("TWILIGHT_DAGGER") && twilightSlot == -1) twilightSlot = i;
        }
        if (firedustSlot == -1 || twilightSlot == -1) return;

        String message = event.message.getUnformattedText();
        if (message.contains("Strike using the")) {
            if (message.contains("ASHEN")) {
                Shady.mc.thePlayer.inventory.currentItem = firedustSlot;
                if (getDaggerState(Shady.mc.thePlayer.inventory.getCurrentItem()).needsSwitch(DaggerState.ASHEN)) {
                    KeybindUtils.rightClick();
                }
            }
            else if (message.contains("AURIC")) {
                Shady.mc.thePlayer.inventory.currentItem = firedustSlot;
                if (getDaggerState(Shady.mc.thePlayer.inventory.getCurrentItem()).needsSwitch(DaggerState.AURIC)) {
                    KeybindUtils.rightClick();
                }
            }
            else if (message.contains("SPIRIT")) {
                Shady.mc.thePlayer.inventory.currentItem = twilightSlot;
                if (getDaggerState(Shady.mc.thePlayer.inventory.getCurrentItem()).needsSwitch(DaggerState.SPIRIT)) {
                    KeybindUtils.rightClick();
                }
            }
            else if (message.contains("CRYSTAL")) {
                Shady.mc.thePlayer.inventory.currentItem = twilightSlot;
                if (getDaggerState(Shady.mc.thePlayer.inventory.getCurrentItem()).needsSwitch(DaggerState.CRYSTAL)) {
                    KeybindUtils.rightClick();
                }
            }
        }
    }

    public static DaggerState getDaggerState(ItemStack item) {
        Optional<String> attunedLore = Utils.getLore(item).stream().filter(s -> s.contains("Attuned: ")).findFirst();
        if (attunedLore.isPresent()) {
            if (attunedLore.get().endsWith("Ashen")) return DaggerState.ASHEN;
            else if (attunedLore.get().endsWith("Auric")) return DaggerState.AURIC;
            else if (attunedLore.get().endsWith("Spirit")) return DaggerState.SPIRIT;
            else if (attunedLore.get().endsWith("Crystal")) return DaggerState.CRYSTAL;
        }
        return null;
    }

    enum DaggerState {
        ASHEN, AURIC, SPIRIT, CRYSTAL;

        public boolean needsSwitch(DaggerState newState) {
            switch (this) {
                case ASHEN: return newState == AURIC;
                case AURIC: return newState == ASHEN;
                case SPIRIT: return newState == CRYSTAL;
                case CRYSTAL: return newState == SPIRIT;
                default: return false;
            }
        }
    }
}
