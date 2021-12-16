package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class RayMarchUtils {

    public static boolean isFacingBlock(BlockPos block, float range) {
        float stepSize = 0.15f;
        if(Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
            Vector3f position = new Vector3f((float) Shady.mc.thePlayer.posX, (float) Shady.mc.thePlayer.posY+ Shady.mc.thePlayer.getEyeHeight(), (float) Shady.mc.thePlayer.posZ);

            Vec3 look = Shady.mc.thePlayer.getLook(0);

            Vector3f step = new Vector3f((float) look.xCoord, (float) look.yCoord, (float) look.zCoord);
            step.scale(stepSize/step.length());

            for(int i = 0; i < Math.floor(range/stepSize)-2; i++) {
                BlockPos blockAtPos = new BlockPos(position.x, position.y, position.z);
                if(blockAtPos.equals(block)) return true;
                position.translate(step.x, step.y, step.z);
            }
        }
        return false;
    }

    public static <T extends Entity> List<T> getFacedEntityOfType(Class<T> _class, float range) {
        float stepSize = 0.5f;
        if(Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
            Vector3f position = new Vector3f((float) Shady.mc.thePlayer.posX, (float) Shady.mc.thePlayer.posY+ Shady.mc.thePlayer.getEyeHeight(), (float) Shady.mc.thePlayer.posZ);

            Vec3 look = Shady.mc.thePlayer.getLook(0);

            Vector3f step = new Vector3f((float) look.xCoord, (float) look.yCoord, (float) look.zCoord);
            step.scale(stepSize/step.length());

            for(int i = 0; i < Math.floor(range/stepSize)-2; i++) {
                List<T> entities = Shady.mc.theWorld.getEntitiesWithinAABB(_class, new AxisAlignedBB(position.x-0.5, position.y-0.5, position.z-0.5, position.x+0.5, position.y+0.5, position.z+0.5));
                if(!entities.isEmpty()) return entities;
                position.translate(step.x, step.y, step.z);
            }
        }
        return null;
    }

}
