package cheaters.get.banned.features.include.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;

public class FakeBan {

    private boolean banFailed = false;

    public boolean isPerm = false;
    private long startTime = System.currentTimeMillis() - 1000;
    public long duration = 30*24*60*60*1000L;
    public BanType type;

    enum BanType {
        USERNAME, CHEATING_TIMED, BOOSTING, CHEATING_PERM
    }

    public FakeBan() {
    }

    public FakeBan(BanType type, long duration, boolean isPerm) {
        this.type = type;
        this.duration = duration;
        this.isPerm = isPerm;
    }

    public void execute() {
        execute(Shady.mc.getNetHandler().getNetworkManager());
    }

    public void execute(NetworkManager manager) {
        if(isPerm) Utils.sendMessageAsPlayer("/status Appear Offline");

        try {
            switch(type) {
                case CHEATING_TIMED:
                    fakeGenericBan(Shady.mc.getNetHandler().getNetworkManager());
                    break;

                case USERNAME:
                    fakeUsernameBan(Shady.mc.getNetHandler().getNetworkManager());
                    break;

                case CHEATING_PERM:
                    duration = Long.MAX_VALUE;
                    fakePermBan(Shady.mc.getNetHandler().getNetworkManager());
                    break;

                case BOOSTING:
                    duration = TimeUnit.MICROSECONDS.convert(90, TimeUnit.DAYS);
                    fakeBoostingBan(Shady.mc.getNetHandler().getNetworkManager());
                    break;
            }
        } catch(Exception ignored) {
            if(!banFailed) {
                Utils.sendMessageAsPlayer("/r Something went wrong!");
                banFailed = true;
            }
        }
    }

    private void fakeBan(ChatComponentText message, NetworkManager manager) {
        manager.closeChannel(message);
    }

    private void fakeGenericBan(NetworkManager manager) {
        String formattedDuration = DurationFormatUtils.formatDuration(getTimeLeft(), "d'd' H'h' m'm' s's'", false);

        ChatComponentText component = new ChatComponentText("§cYou are temporarily banned for §f" + formattedDuration + " §cfrom this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §rCheating through the use of unfair game advantages.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/appeal");
        component.appendText("\n");
        component.appendText("\n§7Ban ID: §r#" + getBanId());
        component.appendText("\n§7Sharing your Ban ID may affect the processing of your appeal!");

        fakeBan(component, manager);
    }

    private void fakePermBan(NetworkManager manager) {
        ChatComponentText component = new ChatComponentText("§cYou are permanently banned from this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §rCheating through the use of unfair game advantages.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/appeal");
        component.appendText("\n");
        component.appendText("\n§7Ban ID: §r#" + getBanId());
        component.appendText("\n§7Sharing your Ban ID may affect the processing of your appeal!");

        fakeBan(component, manager);
    }

    private void fakeUsernameBan(NetworkManager manager) {
        ChatComponentText component = new ChatComponentText("\n§cYou are currently blocked from joining this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §fYour username, " + Shady.mc.getSession().getUsername() + ", is not allowed on the server and is breaking our rules.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/rules");
        component.appendText("\n");
        component.appendText("\n§cPlease change your Minecraft username before trying to join again.");

        fakeBan(component, manager);
        // Shady.guiToOpen = new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "Failed to connect to the server", component);
    }

    private void fakeBoostingBan(NetworkManager manager) {
        String formattedDuration = DurationFormatUtils.formatDuration(getTimeLeft(), "d'd' H'h' m'm' s's'", false);

        ChatComponentText component = new ChatComponentText("§cYou are temporarily banned for §f" + formattedDuration + " §cfrom this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §rBoosting detected on one or multiple SkyBlock profiles.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/appeal");
        component.appendText("\n");
        component.appendText("\n§7Ban ID: §r#" + getBanId());
        component.appendText("\n§7Sharing your Ban ID may affect the processing of your appeal!");

        fakeBan(component, manager);
    }

    public long getTimeLeft() {
        return startTime + duration - System.currentTimeMillis();
    }

    private static String getBanId() {
        CRC32 hash = new CRC32();
        hash.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        return Long.toHexString(hash.getValue()).toUpperCase();
    }

}
