package cheaters.get.banned.remote;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.HttpUtils;
import cheaters.get.banned.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.UUID;

public class YearInReview {

    public static void open() {
        new Thread(() -> {
            Utils.sendModMessage("Your session on the site will expire in 1 hour");
            Utils.openUrl("https://cheatersgetbanned.me/2021/?code=" + getCode());
        }, "ShadyAddons-YearInReview").start();
    }

    private static String getCode() {
        String serverId = UUID.randomUUID().toString().replace("-", "");
        String response = null;

        try {
            Shady.mc.getSessionService().joinServer(Shady.mc.getSession().getProfile(), Shady.mc.getSession().getToken(), serverId);
        } catch(Exception ignored) {
            return "";
        }

        StringBuilder url =
                new StringBuilder("https://cheatersgetbanned.me/2021/api/code")
                        .append("?username=").append(Shady.mc.getSession().getProfile().getName())
                        .append("&server_id=").append(serverId);

        try {
            response = HttpUtils.fetch(url.toString());
        } catch(Exception ignored) {}

        if(response == null) return "";

        return new Gson().fromJson(response, JsonObject.class).get("code").getAsString();
    }

}
