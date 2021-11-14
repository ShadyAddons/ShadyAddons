package cheaters.get.banned.features;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.SettingChangeEvent;
import cheaters.get.banned.remote.Updater;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.time.OffsetDateTime;

/**
 * Modified from SkyBlockReinvented under GPL-3.0
 * https://github.com/theCudster/SkyblockReinvented/blob/main/LICENSE
 */
public class DiscordRPC implements IPCListener {

    public static final long APP_ID = 909348215806115860L;

    private IPCClient client;
    private boolean connected;

    public DiscordRPC() {
        start();
    }

    @SubscribeEvent
    public void onSettingChange(SettingChangeEvent event) {
        if(event.setting.name.equals("Discord Rich Presence")) {
            if((Boolean) event.newValue) {
                if(isActive()) {
                    client.sendRichPresence(null);
                } else {
                    start();
                }
                setStatus();
            } else {
                client.sendRichPresence(null);
            }
        }
    }

    public void start() {
        try {
            if(isActive() || Config.discordRpc) return;

            client = new IPCClient(APP_ID);
            client.setListener(this);
            client.connect();
        } catch(Exception ignored) {}
    }

    public void stop() {
        if(isActive()) {
            client.sendRichPresence(null);
            client.close();
            connected = false;
        }
    }

    public boolean isActive() {
        return client != null && connected;
    }

    public void setStatus() {
        RichPresence.Builder presenceBuilder = new RichPresence.Builder()
                .setState("CheatersGetBanned.me")
                .setStartTimestamp(OffsetDateTime.now())
                .setLargeImage("repeated", Updater.update == null ? null : NumberFormat.getInstance().format(Updater.update.users) + "+ users");
        client.sendRichPresence(presenceBuilder.build());
    }

    @Override
    public void onReady(IPCClient client) {
        connected = true;
        if(Config.discordRpc) setStatus();
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {
        this.client = null;
        connected = false;
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        this.client = null;
        connected = false;
    }

}
