package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class AutoCloak {

    public boolean pillar = false, cloak = false;
    public long pillarFoundTime = 0;

    //TODO: Check if a quest is active
    @SubscribeEvent
    public void onTick(TickEvent event){
        if(!Config.autoCloak || !LocationUtils.onIsland(LocationUtils.Island.CRIMSON_ISLE)) return;
        if(System.currentTimeMillis()-pillarFoundTime >= 5000 && pillar){ pillar=false; cloak = false;}
        if(!pillar) {
            World world = Shady.mc.theWorld;
            List<EntityArmorStand> list = world.getEntities(EntityArmorStand.class, entity -> entity.getDisplayName().getUnformattedText().contains("hits") && entity.getDisplayName().getUnformattedText().contains("2s"));
            pillar = !list.isEmpty();
            pillarFoundTime = System.currentTimeMillis();
        } else if(!cloak){
            int cloakSpot = -1;
            for(int i=0; i<9; i++) {
                ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
                if(Utils.getSkyBlockID(item).equals("WITHER_CLOAK")) cloakSpot =  i;
            }
            if(cloakSpot == -1){ Utils.sendModMessage("§cNo Cloak found"); return;}
            int old = Shady.mc.thePlayer.inventory.currentItem;
            Shady.mc.thePlayer.inventory.currentItem = cloakSpot;
            KeybindUtils.rightClick();
            Shady.mc.thePlayer.inventory.currentItem = old;
            cloak = true;
        }
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        if(cloak){
            if(!event.message.getUnformattedText().toLowerCase().contains("true damage from an")) return;
            int cloakSpot = -1;
            for(int i=0; i<9; i++) {
                ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
                if(Utils.getSkyBlockID(item).equals("WITHER_CLOAK")) cloakSpot =  i;
            }
            if(cloakSpot == -1) return;
            int old = Shady.mc.thePlayer.inventory.currentItem;
            Shady.mc.thePlayer.inventory.currentItem = cloakSpot;
            KeybindUtils.rightClick();
            Shady.mc.thePlayer.inventory.currentItem = old;
            pillar = false;
            cloak = false;
        }
    }

    @SubscribeEvent
    public void onWordChange(WorldEvent.Load event){
        pillar = false;
        cloak = false;
    }


}
