package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class MobESP {

    private HashSet<Entity> entitiesToHighlight = new HashSet<>();

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(!Utils.inDungeon || !Config.highlightStarredMobs) return;
        if(event.entity instanceof EntityPlayer) {
            String name = event.entity.getName();
            if(name.equals("Shadow Assassin") || name.equals("Lost Adventurer") || name.equals("Diamond Guy")) {
                entitiesToHighlight.add(event.entity);
            }
        }
    }

    @SubscribeEvent
    public void beforeRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(!Utils.inDungeon || !Config.highlightStarredMobs) return;
        if(event.entity instanceof EntityArmorStand) {
            if(event.entity.hasCustomName() && event.entity.getCustomNameTag().contains("✯")) {
                entitiesToHighlight.add(event.entity);
                List<Entity> possibleEntities = event.entity.getEntityWorld().getEntitiesInAABBexcluding(event.entity, event.entity.getEntityBoundingBox().expand(0, 3, 0), entity -> !(entity instanceof EntityArmorStand));
                if(!possibleEntities.isEmpty()) {
                    System.out.println(possibleEntities);
                    entitiesToHighlight.add(possibleEntities.get(0));
                }
                Shady.mc.theWorld.removeEntity(event.entity);
            }
        }

        if(entitiesToHighlight.contains(event.entity)) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();

            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderUtils.bindColor(new Color(255, 170, 0));
        }
    }

    @SubscribeEvent
    public void afterRenderEntity(RenderLivingEvent.Post<EntityLivingBase> event) {
        if(!Utils.inDungeon || !Config.highlightStarredMobs) return;
        if(entitiesToHighlight.contains(event.entity)) {
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

}
