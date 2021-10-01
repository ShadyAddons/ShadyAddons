package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class AngleUtils {
    public static float getAngleDifference(float a, float b) {
        return ((((a - b) % 360F) + 540F) % 360F) - 180F;
    }

    public static Float[] getLookPosFromEntity(Entity entity){
        EntityPlayerSP player = Shady.mc.thePlayer;
        double d0 = player.posX - entity.posX;
        double d1 = (player.posY + player.getEyeHeight()) - (entity.posY + (double) entity.getEyeHeight() - 1.5);
        double d2 = player.posZ - entity.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f = (float) (MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI));
        float targetYaw = f; // yaw
        float targetPitch = -f1;
        if (f > 0) targetYaw = (f - 180);
        if (f < 0) targetYaw = (f + 180);
        Float[] values = new Float[2];
        values[0] = targetYaw;
        values[1] = targetPitch;
        return values;
    }
    public static Float[] getLookPosFromCoord(Vector3f vec){
        EntityPlayerSP player = Shady.mc.thePlayer;
        double d0 = player.posX - vec.getX();
        double d1 = (player.posY + player.getEyeHeight()) - vec.getY() + 1;
        double d2 = player.posZ - vec.getZ();
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f = (float) (MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI));
        float targetYaw = f; // yaw
        float targetPitch = -f1;
        if (f > 0) targetYaw = (f - 180);
        if (f < 0) targetYaw = (f + 180);
        Float[] values = new Float[2];
        values[0] = targetYaw;
        values[1] = targetPitch;
        return values;
    }

}
