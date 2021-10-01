package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.ParticleEvent;
import cheaters.get.banned.utils.AngleUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.util.vector.Vector3f;

import java.util.Random;

import static cheaters.get.banned.Shady.mc;

public class AutoPick {
    private static float someYaw;
    private static float somePitch;
    private static boolean looking;


    @SubscribeEvent
    public void onParticleEvent(ParticleEvent event) {
        if (event.particleType == EnumParticleTypes.CRIT && mc.thePlayer.getDistance(event.xCoord, event.yCoord, event.zCoord) < 5) {
            Float[] angles = AngleUtils.getLookPosFromCoord(new Vector3f((float) event.xCoord, (float) event.yCoord, (float) event.zCoord))
            if (AngleUtils.getAngleDifference(mc.thePlayer.rotationYaw, angles[0]) < 35 && AngleUtils.getAngleDifference(mc.thePlayer.rotationYaw, angles[1]) < 20) {
                someYaw = angles[0];
                somePitch = angles[1];
                looking = true;
            }
        }
    }

    @SubscribeEvent
    public void onClientTickEventEnd(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.ClientTickEvent.Phase.END) return;
        if (event.phase == TickEvent.ClientTickEvent.Phase.END) {
            EntityPlayerSP player = mc.thePlayer;
            float turn;
            Random rand = new Random();
            turn = rand.nextInt(8);
            turn += 6;
            final float yawDifference = AngleUtils.getAngleDifference((someYaw, player.rotationYaw);
            final float pitchDifference = AngleUtils.getAngleDifference((somePitch, player.rotationPitch);
            player.rotationYaw = player.rotationYaw + (yawDifference > turn ? turn : Math.max(yawDifference, -turn));
            player.rotationPitch = player.rotationPitch + (pitchDifference > turn ? turn : Math.max(pitchDifference, -turn));
            if (Math.abs(yawDifference) < 2 && Math.abs(pitchDifference) < 2 {
                
            }
        }
    }
}
