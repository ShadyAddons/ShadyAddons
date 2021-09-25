package cheaters.get.banned.features;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class MobESP {

    @SubscribeEvent
    public void beforeRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(shouldHighlight(event.entity)) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();

            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if(event.entity.getName().contains("Lost Adventurer") || event.entity.getName().contains("Frozen Adventurer") || event.entity.getName().contains("Angry Archaeologist") || event.entity.getName().contains("Shadow Assassin")) {
                RenderUtils.bindColor(new Color(0, 255, 234));
            } else {
                RenderUtils.bindColor(new Color(255, 170, 0));
            }
        }
    }

    @SubscribeEvent
    public void afterRenderEntity(RenderLivingEvent.Post<EntityLivingBase> event) {
        if(shouldHighlight(event.entity)) {
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

    private static boolean shouldHighlight(Entity entity) {
        return Config.highlightStarredMobs && Utils.inDungeon /*&& entity.getName().contains("✯")*/;
    }

}
