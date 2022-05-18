package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.LocationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoSwitchSwordDojo {

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if(Config.dojoDiscipline && LocationUtils.onIsland(LocationUtils.Island.CRIMSON_ISLE)){
            Shady.mc.entityRenderer.getMouseOver(1.0f);
            Entity entity = Shady.mc.pointedEntity;
            if(entity instanceof EntityZombie){
                EntityZombie target = (EntityZombie) entity;
                ItemStack i = target.getCurrentArmor(3);
                if(i == null || !(target.getCurrentArmor(0) == null) || !(target.getCurrentArmor(1) == null) || !(target.getCurrentArmor(2) == null)) return;
                int item;
                switch(i.getItem().getRegistryName()){
                    case "minecraft:leather_helmet":
                        item = 0;
                        break;
                    case "minecraft:iron_helmet":
                        item = 1;
                        break;
                    case "minecraft:golden_helmet":
                        item = 2;
                        break;
                    case "minecraft:diamond_helmet":
                        item = 3;
                        break;
                    default :
                        return;
                }
                Shady.mc.thePlayer.inventory.currentItem = item;
            }
        }
    }
}
