package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.RenderUtils;
import shady.shady.shady.utils.Utils;

public class BossCorleoneFinder {

    private static Minecraft mc = Minecraft.getMinecraft();

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
        if(corleone != null) {
            RenderUtils.renderWaypointText("Boss Corleone", corleone.posX, corleone.posY+1, corleone.posZ, event.partialTicks);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        corleone = null;
    }

}
