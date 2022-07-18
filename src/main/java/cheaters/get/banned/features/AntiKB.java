package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.Utils;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AntiKB {

    public static void handleExplosion(S27PacketExplosion packet) {
        if(isEnabled()) {
            Shady.mc.thePlayer.motionX -= packet.func_149149_c();
            Shady.mc.thePlayer.motionY -= packet.func_149144_d();
            Shady.mc.thePlayer.motionZ -= packet.func_149147_e();
        }
    }

    public static boolean handleEntityVelocity(S12PacketEntityVelocity packet) {
        if(isEnabled()) return Shady.mc.theWorld.getEntityByID(packet.getEntityID()) == Shady.mc.thePlayer;
        return false;
    }

    private static boolean isEnabled() {
        if(!Config.antiKb || !Utils.inSkyBlock) return false;
        if(Shady.mc.thePlayer == null || Shady.mc.thePlayer.isInLava()) return false;

        if(Shady.mc.thePlayer.getHeldItem() != null) {
            String itemId = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
            return !itemId.equals("JERRY_STAFF") || !itemId.equals("BONZO_STAFF") || !itemId.equals("STARRED_BONZO_STAFF");
        }

        return true;
    }

}
