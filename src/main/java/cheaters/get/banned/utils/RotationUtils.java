package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RotationUtils {

    private static float pitchDifference;
    public static float yawDifference;
    private static int ticks = -1;
    private static int tickCounter = 0;

    public static void smoothLook(float pitch, float yaw, int ticks) {
        if(ticks == 0) {
            look(pitch, yaw);
            return;
        }

        pitchDifference = pitch - Shady.mc.thePlayer.rotationPitch;
        yawDifference = yaw - Shady.mc.thePlayer.rotationYaw;

        RotationUtils.ticks = ticks;
        RotationUtils.tickCounter = 0;
    }

    public static void smartLook(float pitch, float yaw, int ticksPer180) {
        float rotationDifference = Math.max(
                Math.abs(pitch - Shady.mc.thePlayer.rotationPitch),
                Math.abs(yaw - Shady.mc.thePlayer.rotationYaw)
        );
        smoothLook(pitch, yaw, (int) (rotationDifference / 180 * ticksPer180));
    }

    public static void look(float pitch, float yaw) {
        Shady.mc.thePlayer.rotationPitch = pitch;
        Shady.mc.thePlayer.rotationYaw = yaw;
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if(tickCounter < ticks) {
            Shady.mc.thePlayer.rotationPitch += pitchDifference / ticks;
            Shady.mc.thePlayer.rotationYaw += yawDifference / ticks;
            tickCounter++;
        }
    }

}
