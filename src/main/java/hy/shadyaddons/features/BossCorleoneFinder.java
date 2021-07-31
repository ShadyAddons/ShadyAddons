package hy.shadyaddons.features;

import hy.shadyaddons.config.Config;
import hy.shadyaddons.utils.RenderUtils;
import hy.shadyaddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BossCorleoneFinder {

    private static Minecraft mc = Minecraft.getMinecraft();

    private EntityOtherPlayerMP corleone = null;

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(Utils.inSkyBlock && Config.isEnabled(Config.Setting.BOSS_CORLEONE_FINDER)) {
            if(corleone == null && event.entity instanceof EntityOtherPlayerMP /*&& event.entity.getName().equals("Team Treasurite") && event.entity.getMaxHealth() == 1000000*/) {
                Utils.sendModMessage("Boss Corleone Found: "+event.entity.posX+"/"+event.entity.posY+"/"+event.entity.posZ);
                corleone = (EntityOtherPlayerMP) event.entity;
            }
        } else {
            corleone = null;
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
