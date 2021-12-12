package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.RayCastUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class CrystalReach {

    public static EntityEnderCrystal crystal = null;

    public static boolean isEnabled() {
        return Config.crystalReach &&
                Utils.inDungeon &&
                Shady.mc.thePlayer != null &&
                Shady.mc.thePlayer.getPosition().getY() > 215 &&
                DungeonUtils.dungeonRun != null &&
                DungeonUtils.dungeonRun.inBoss &&
                DungeonUtils.inFloor(DungeonUtils.Floor.FLOOR_7);
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(isEnabled() && Shady.mc.thePlayer.isSneaking()) {
            // crystal = lookingAtCrystal();
            List<EntityEnderCrystal> crystals = RayCastUtils.getFacedEntityOfType(EntityEnderCrystal.class, 32);
            if(crystals != null) {
                crystal = crystals.get(0);
            } else {
                crystal = null;
            }
        } else {
            crystal = null;
        }
    }

    @SubscribeEvent
    public void onRightClick(ClickEvent.Right event) {
        if(isEnabled() && Shady.mc.thePlayer.isSneaking() && crystal != null) {
            List<Entity> armorStand = Shady.mc.theWorld.getEntitiesInAABBexcluding(crystal, crystal.getEntityBoundingBox(), entity -> entity instanceof EntityArmorStand && entity.getCustomNameTag().contains("CLICK HERE"));
            if(!armorStand.isEmpty() && armorStand.get(0) != null) {
                Shady.mc.playerController.interactWithEntitySendPacket(Shady.mc.thePlayer, armorStand.get(0));
                MiscStats.add(MiscStats.Metric.CRYSTALS_REACHED);
                event.setCanceled(true);
            }
        }
    }

    private static EntityEnderCrystal lookingAtCrystal() {
        float range = 32;
        float stepSize = 0.5f;
        if(Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
            Vector3f position = new Vector3f((float) Shady.mc.thePlayer.posX, (float) Shady.mc.thePlayer.posY+ Shady.mc.thePlayer.getEyeHeight(), (float) Shady.mc.thePlayer.posZ);

            Vec3 look = Shady.mc.thePlayer.getLook(0);

            Vector3f step = new Vector3f((float) look.xCoord, (float) look.yCoord, (float) look.zCoord);
            step.scale(stepSize/step.length());

            for(int i = 0; i < Math.floor(range/stepSize)-2; i++) {
                List<EntityEnderCrystal> entities = Shady.mc.theWorld.getEntitiesWithinAABB(EntityEnderCrystal.class, new AxisAlignedBB(position.x-0.5, position.y-0.5, position.z-0.5, position.x+0.5, position.y+0.5, position.z+0.5));
                if(!entities.isEmpty()) return entities.get(0);
                position.translate(step.x, step.y, step.z);
            }
        }
        return null;
    }

}
