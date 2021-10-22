package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.RenderEntityModelEvent;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.OutlineUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class MobESP {

    private static HashMap<Entity, Color> highlightedEntities = new HashMap<>();

    public static void highlightEntity(Entity entity, Color color) {
        highlightedEntities.put(entity, color);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(Config.entityESP) {
            if(Utils.inDungeon) {
                if(Config.minibossEsp && event.entity instanceof EntityPlayer) {
                    String name = event.entity.getName();
                    switch(name) {
                        case "Shadow Assassin":
                            highlightEntity(event.entity, Color.MAGENTA);
                            break;

                        case "Lost Adventurer":
                            highlightEntity(event.entity, Color.BLUE);
                            break;

                        case "Diamond Guy":
                            highlightEntity(event.entity, Color.CYAN);
                            break;
                    }
                }

                if(Config.secretBatEsp && event.entity instanceof EntityBat) {
                    float health = ((EntityBat) event.entity).getMaxHealth();
                    if(health == 100 || health == 200 || health == 400 || health == 800) {
                        highlightEntity(event.entity, Color.RED);
                    }
                }
            }

            if(Utils.inSkyBlock && LocationUtils.onIsland(LocationUtils.Island.CRYSTAL_HOLLOWS)) {
                if(Config.sludgeEsp) {
                    if(event.entity instanceof EntitySlime && !(event.entity instanceof EntityMagmaCube)) {
                        highlightEntity(event.entity, Color.GREEN);
                    }
                }

                if(Config.yogEsp) {
                    if(event.entity instanceof EntityMagmaCube) {
                        highlightEntity(event.entity, Color.RED);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderEntityModel(RenderEntityModelEvent event) {
        if(Utils.inDungeon && Config.starredMobEsp) {
            if(event.entity instanceof EntityArmorStand) {
                if(event.entity.hasCustomName() && event.entity.getCustomNameTag().contains("✯")) {
                    // highlightEntity(event.entity, Color.MAGENTA);
                    List<Entity> possibleEntities = event.entity.getEntityWorld().getEntitiesInAABBexcluding(event.entity, event.entity.getEntityBoundingBox().expand(0, 3, 0), entity -> !(entity instanceof EntityArmorStand));
                    if(!possibleEntities.isEmpty()) {
                        highlightEntity(possibleEntities.get(0), Color.ORANGE);
                    }
                    Shady.mc.theWorld.removeEntity(event.entity);
                }
            }

        }

        if(Config.entityESP && !highlightedEntities.isEmpty() && highlightedEntities.containsKey(event.entity)) {
            OutlineUtils.outlineEntity(event, highlightedEntities.get(event.entity));
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        highlightedEntities.clear();
    }

}
