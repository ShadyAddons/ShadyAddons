package cheaters.get.banned.remote;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.HttpUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class Analytics {

    private static final String whyCollectAnalytics = "Basic analytics are collected to gague version adoption rates. Other metrics, such as play time and rare drops may be collected for the creator's personal hobby projects in the future. Data is only associated with the player's hashed UUID.";
    private static final String howAreTheySent = "This system allows Shady to verify the authenticity of your data WITHOUT your session ID. This is the same process that Minecraft servers (net.minecraft.client.network.NetHandlerLoginClient) use to make sure you are who you say you are. Optifine (net.optifine.gui.GuiScreenCapeOF) and Skytils (skytils.skytilsmod.features.impl.handlers.MayorInfo.kt) do the exact same thing. See https://wiki.vg/Protocol_Encryption#Authentication for more information.";
    private static final String pleaseDontCollectAnalytics = "Sorry dude. In exchange for your safe, free, and relatively high-quality block game cheats, I'd like to collect a little information for personal data science projects and so I can scratch some curious itches I have about this game.";

    public static void collect(String key, String value) {
        new Thread(() -> {
            String serverId = UUID.randomUUID().toString().replace("-", "");

            try {
                Shady.mc.getSessionService().joinServer(Shady.mc.getSession().getProfile(), Shady.mc.getSession().getToken(), serverId);
            } catch(Exception ignored) {
                return;
            }

            StringBuilder url =
                    new StringBuilder("https://cheatersgetbanned.me/api/analytics")
                        .append("?username=").append(Shady.mc.getSession().getProfile().getName())
                        .append("&server_id=").append(serverId)
                        .append("&hashed_uuid=").append(DigestUtils.sha256Hex(Shady.mc.getSession().getProfile().getId().toString().replace("-", "")))
                        .append("&").append(key).append("=").append(value);

            try {
                HttpUtils.fetch(url.toString());
            } catch(Exception ignored) {}
        }, "ShadyAddons-Analytics").start();
    }

}
