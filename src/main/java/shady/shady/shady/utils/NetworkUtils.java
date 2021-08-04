package shady.shady.shady.utils;

import net.minecraft.network.Packet;
import shady.shady.shady.Shady;

public class NetworkUtils {

    /**
     * Send a packet
     * @param packet The packet Object to be sent
     */
    public static void sendPacket(Packet<?> packet) {
        Shady.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

}
