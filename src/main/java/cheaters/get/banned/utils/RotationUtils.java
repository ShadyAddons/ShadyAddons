package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RotationUtils {

    private static float pitchDifference;
    public static float yawDifference;
    private static int ticks = -1;
    private static int tickCounter = 0;
    private static Runnable callback = null;

    public static class Rotation {
        public float pitch;
        public float yaw;

        public Rotation(float pitch, float yaw) {
            this.pitch = pitch;
            this.yaw = yaw;
        }
    }

    private static double wrapAngleTo180(double angle) {
        return angle - Math.floor(angle / 360 + 0.5) * 360;
    }

    public static Rotation getRotationToBlock(BlockPos block) {
        double diffX = block.getX() - Shady.mc.thePlayer.posX + 0.5;
        double diffY = block.getY() - Shady.mc.thePlayer.posY + 0.5 - Shady.mc.thePlayer.getEyeHeight();
        double diffZ = block.getZ() - Shady.mc.thePlayer.posZ + 0.5;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float pitch = (float) -Math.atan2(dist, diffY);
        float yaw = (float) Math.atan2(diffZ, diffX);
        pitch = (float) wrapAngleTo180((pitch * 180F / Math.PI + 90)*-1);
        yaw = (float) wrapAngleTo180((yaw * 180 / Math.PI) - 90);

        return new Rotation(pitch, yaw);
    }

    public static void smoothLook(Rotation rotation, int ticks, Runnable callback) {
        if(ticks == 0) {
            look(rotation);
            if(callback != null) callback.run();
            return;
        }

        RotationUtils.callback = callback;

        pitchDifference = rotation.pitch - Shady.mc.thePlayer.rotationPitch;
        yawDifference = rotation.yaw - Shady.mc.thePlayer.rotationYaw;

        RotationUtils.ticks = ticks * 20;
        RotationUtils.tickCounter = 0;
    }

    public static void smartLook(Rotation rotation, int ticksPer180, Runnable callback) {
        float rotationDifference = Math.max(
                Math.abs(rotation.pitch - Shady.mc.thePlayer.rotationPitch),
                Math.abs(rotation.yaw - Shady.mc.thePlayer.rotationYaw)
        );
        smoothLook(rotation, (int) (rotationDifference / 180 * ticksPer180), callback);
    }

    public static void look(Rotation rotation) {
        Shady.mc.thePlayer.rotationPitch = rotation.pitch;
        Shady.mc.thePlayer.rotationYaw = rotation.yaw;
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if(tickCounter < ticks) {
            Shady.mc.thePlayer.rotationPitch += pitchDifference / ticks;
            Shady.mc.thePlayer.rotationYaw += yawDifference / ticks;
            tickCounter++;
        } else if(callback != null) {
            callback.run();
            callback = null;
        }
    }

}
