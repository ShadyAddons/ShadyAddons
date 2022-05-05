package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

public class AutoBlazeSword {

    //Inspired by OneLemonyBoi and optimized

    HashSet<String> ash = new HashSet<>();
    HashSet<String> spirit = new HashSet<>();
    boolean enabled = false;

    public void enable(){
        enabled = true;
        spirit.add("MAWDUST_DAGGER");
        spirit.add("BURSTMAW_DAGGER");
        spirit.add("HEARTMAW_DAGGER");
        ash.add("FIREDUST_DAGGER");
        ash.add("BURSTFIRE_DAGGER");
        ash.add("HEARTFIRE_DAGGER");
    }


    //TODO:Better detection of attunement
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event){
        if(!enabled) enable();
        if(!LocationUtils.onIsland(LocationUtils.Island.CRIMSON_ISLE) || !Config.autoBlazeDagger) return;
        if(event.message.getUnformattedText().contains("attunement")){
            String blazeAttunement = event.message.getUnformattedText().split(" ")[3];
            int daggerAsh = -1, daggerSpirit = -1;
            for(int i=0; i<9; i++) {
                ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
                if(spirit.contains(Utils.getSkyBlockID(item))) daggerSpirit = i;
                else if(ash.contains(Utils.getSkyBlockID(item))) daggerAsh = i;
            }
            switch(blazeAttunement) {
                case "ASHEN":
                    if (daggerAsh != -1){
                        if (daggerAsh != Shady.mc.thePlayer.inventory.currentItem)
                            Shady.mc.thePlayer.inventory.currentItem = daggerAsh;
                        if (Shady.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.stone_sword)
                            KeybindUtils.rightClick();
                    }
                    break;
                case"AURIC":
                    if (daggerAsh != -1) {
                        if (daggerAsh != Shady.mc.thePlayer.inventory.currentItem)
                            Shady.mc.thePlayer.inventory.currentItem = daggerAsh;
                        if (Shady.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.golden_sword)
                            KeybindUtils.rightClick();
                    }
                    break;
                case"SPIRIT":
                    if (daggerSpirit != -1) {
                        if (daggerSpirit != Shady.mc.thePlayer.inventory.currentItem)
                            Shady.mc.thePlayer.inventory.currentItem = daggerSpirit;
                        if (Shady.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.iron_sword)
                            KeybindUtils.rightClick();
                    }
                    break;
                case"CRYSTAL":
                    if (daggerSpirit != -1) {
                        if (daggerSpirit != Shady.mc.thePlayer.inventory.currentItem)
                            Shady.mc.thePlayer.inventory.currentItem = daggerSpirit;
                        if (Shady.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.diamond_sword)
                            KeybindUtils.rightClick();
                    }
                    break;
            }
        }
    }
}
