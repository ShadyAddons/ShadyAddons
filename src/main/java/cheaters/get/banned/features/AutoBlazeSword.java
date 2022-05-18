package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean checkSkeleton(Entity entity){
        if(entity instanceof EntitySkeleton){
            if(((EntitySkeleton)entity).getSkeletonType() == 1){
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onLeftClick(ClickEvent.Left event){
        if(!enabled) enable();
        if(!LocationUtils.onIsland(LocationUtils.Island.CRIMSON_ISLE) || !Config.autoBlazeDagger) return;
        Entity pointed = Shady.mc.pointedEntity;
        if(pointed instanceof EntityBlaze || pointed instanceof EntityPigZombie || checkSkeleton(pointed)){
            int daggerAsh = -1, daggerSpirit = -1, attunement = 0;
            List<Entity> entities = Shady.mc.theWorld.getEntitiesWithinAABBExcludingEntity(pointed, pointed.getEntityBoundingBox().expand(0.5, 0.5, 0.5)).stream().filter(entity -> entity instanceof EntityArmorStand).collect(Collectors.toList());
            for(Entity e : entities) {
                String name = e.getDisplayName().getUnformattedText().toLowerCase();
                if(name.contains("ashen")) attunement = 1;
                else if(name.contains("auric")) attunement = 2;
                else if(name.contains("spirit")) attunement = 3;
                else if(name.contains("crystal")) attunement = 4;
            }
            for(int i=0; i<9; i++) {
                ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
                if(spirit.contains(Utils.getSkyBlockID(item))) daggerSpirit = i;
                else if(ash.contains(Utils.getSkyBlockID(item))) daggerAsh = i;
            }
            switch(attunement) {
                case 0:
                    break;
                case 1:
                    if (daggerAsh != -1){
                        if (daggerAsh != Shady.mc.thePlayer.inventory.currentItem)
                            Shady.mc.thePlayer.inventory.currentItem = daggerAsh;
                        if (Shady.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.stone_sword)
                            KeybindUtils.rightClick();
                    }
                    break;
                case 2:
                    if (daggerAsh != -1) {
                        if (daggerAsh != Shady.mc.thePlayer.inventory.currentItem)
                            Shady.mc.thePlayer.inventory.currentItem = daggerAsh;
                        if (Shady.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.golden_sword)
                            KeybindUtils.rightClick();
                    }
                    break;
                case 3:
                    if (daggerSpirit != -1) {
                        if (daggerSpirit != Shady.mc.thePlayer.inventory.currentItem)
                            Shady.mc.thePlayer.inventory.currentItem = daggerSpirit;
                        if (Shady.mc.thePlayer.inventory.getCurrentItem().getItem() != Items.iron_sword)
                            KeybindUtils.rightClick();
                    }
                    break;
                case 4:
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
