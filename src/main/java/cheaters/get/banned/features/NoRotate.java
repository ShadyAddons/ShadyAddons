package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.Utils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate {

    private static float prevPitch = 0;
    private static float prevYaw = 0;

    public static void handlePlayerPosLookPre() {
        if(Shady.mc.thePlayer == null || Shady.mc.thePlayer.rotationPitch % 1 == 0f || Shady.mc.thePlayer.rotationYaw % 1 == 0f) return;
        prevPitch = Shady.mc.thePlayer.rotationPitch % 360;
        prevYaw = Shady.mc.thePlayer.rotationYaw % 360;
    }

    public static void handlePlayerPosLook(S08PacketPlayerPosLook packet) {
        if(!Config.noRotate || !Utils.inSkyBlock || packet.getPitch() % 1 == 0f || packet.getYaw() % 1 == 0f) return;

        float prevPitch1 = Shady.mc.thePlayer.rotationPitch;
        float prevYaw1 = Shady.mc.thePlayer.rotationYaw;

        Shady.mc.thePlayer.rotationPitch = prevPitch;
        Shady.mc.thePlayer.rotationYaw = prevYaw;

        prevPitch = prevPitch1;
        prevYaw = prevYaw1;
    }

}
