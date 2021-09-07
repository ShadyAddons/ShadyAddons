package cheaters.get.banned.features;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.Setting;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;

public class BossCorleoneFinder {

    private EntityOtherPlayerMP corleone = null;

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if(Utils.inSkyBlock && corleone == null && event.entity instanceof EntityOtherPlayerMP && event.entity.getName().equals("Team Treasurite") && ((EntityOtherPlayerMP) event.entity).getMaxHealth() == 1000000) {
            corleone = (EntityOtherPlayerMP) event.entity;
            if(Config.isEnabled(Setting.BOSS_CORLEONE_FINDER)) {
                Utils.sendModMessage("Boss Corleone Found at "+corleone.posX+"/"+corleone.posY+"/"+corleone.posZ);
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(corleone != null && Config.isEnabled(Setting.BOSS_CORLEONE_FINDER)) {
            RenderUtils.renderWaypointText("Boss Corleone", corleone.posX, corleone.posY+1, corleone.posZ, event.partialTicks);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        corleone = null;
    }

}
