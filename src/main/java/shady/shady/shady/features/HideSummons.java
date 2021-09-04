package shady.shady.shady.features;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HideSummons {
    
    private static final ArrayList<String> summonItemIDs = new ArrayList<String>(Arrays.asList(
            "HEAVY_HELMET",
            "ZOMBIE_KNIGHT_HELMET",
            "SKELETOR_HELMET"
    ));

    public static boolean isSummon(Entity entity) {
        if(entity instanceof EntityPlayer) {
            return entity.getName().equals("Lost Adventurer");
        } else if(entity instanceof EntityZombie || entity instanceof EntitySkeleton) {
            for(int i = 0; i < 5; i++) {
                ItemStack item = ((EntityMob) entity).getEquipmentInSlot(i);
                if(summonItemIDs.contains(Utils.getSkyBlockID(item))) {
                    return true;
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onPreRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(Utils.inSkyBlock && !Utils.inDungeon) {
            if(isSummon(event.entity)) {
                if(Config.isEnabled(Setting.HIDE_SUMMONS) && event.entity.getDistanceToEntity(Shady.mc.thePlayer) < 5) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if(Config.isEnabled(Setting.CLICK_THROUGH_SUMMONS) && Utils.inSkyBlock && !Utils.inDungeon && isSummon(event.target)) {
            Entity excludedEntity = Shady.mc.getRenderViewEntity();
            double reach = Shady.mc.playerController.getBlockReachDistance();
            Vec3 look = excludedEntity.getLook(0);

            AxisAlignedBB boundingBox = excludedEntity.getEntityBoundingBox().addCoord(look.xCoord*reach, look.yCoord*reach, look.zCoord*reach).expand(1, 1, 1);
            List<Entity> entitiesInRange = Shady.mc.theWorld.getEntitiesWithinAABBExcludingEntity(excludedEntity, boundingBox);
            entitiesInRange.removeIf(entity -> !entity.canBeCollidedWith());
            entitiesInRange.removeIf(HideSummons::isSummon);

            if(entitiesInRange.size() > 0) {
                event.setCanceled(true);
                Shady.mc.thePlayer.swingItem();
                Shady.mc.playerController.attackEntity(Shady.mc.thePlayer, entitiesInRange.get(0));
            }
        }
    }

}
