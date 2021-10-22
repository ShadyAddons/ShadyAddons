package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.List;

public class CrystalReach {

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(Config.crystalReach && Utils.inDungeon && Shady.mc.thePlayer.isSneaking()) {
            Vec3 look = Shady.mc.getRenderViewEntity().getLook(event.partialTicks);
            AxisAlignedBB boundingBox = Shady.mc.getRenderViewEntity().getEntityBoundingBox().addCoord(look.xCoord * 100, look.yCoord * 100, look.zCoord * 100).expand(1, 1, 1);
            List<EntityEnderCrystal> crystals = Shady.mc.theWorld.getEntitiesWithinAABB(EntityEnderCrystal.class, boundingBox);

            if(!crystals.isEmpty()) {
                for(EntityEnderCrystal crystal : crystals) {
                    MobESP.highlightEntity(crystal, Color.CYAN);
                }
            }
        }
    }

}
