package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import net.minecraft.network.Packet;

public class NetworkUtils {

    public static void sendPacket(Packet<?> packet) {
        Shady.mc.getNetHandler().addToSendQueue(packet);
    }

}
