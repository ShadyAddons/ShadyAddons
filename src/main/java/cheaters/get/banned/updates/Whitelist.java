package cheaters.get.banned.updates;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.HttpUtils;
import cheaters.get.banned.utils.Utils;

import java.util.UUID;

public class Whitelist {

    // Yes, it's meant to be blocking
    public static void check() {
        UUID uuid = Shady.mc.getSession().getProfile().getId();
        String username = Shady.mc.getSession().getUsername();

        if(uuid != null) {
            String url = getWhitelistURL(uuid, username);
            String response = HttpUtils.fetch(url);

            if(response != null && response.equals("1")) {
                return;
            } else {
                System.out.println("Error checking whitelist status");
            }
        }

        String heySnooper = "Never gonna give you up!";
        Shady.disable();
        Utils.openUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        System.exit(0);
    }

    private static String getWhitelistURL(UUID u2, String sucks) {
        return "https://cheatersgetbanned.me/api/whitelist?username=" + sucks + "&uuid=" + u2;
    }

}
