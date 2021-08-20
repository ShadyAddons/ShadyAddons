package shady.shady.shady.updates;

import com.google.gson.Gson;
import shady.shady.shady.Shady;
import shady.shady.shady.utils.HttpUtils;

import java.util.UUID;

public class Updater {

    public static boolean shouldUpdate = false;
    public static Update update = null;

    public static class Update {
        public String version;
        public String download;

        public Update(String version, String download) {
            this.version = version;
            this.download = download;
        }
    }

    public static void check() {
        new Thread(() -> {
            UUID uuid = Shady.mc.getSession().getProfile().getId();
            if(uuid != null) {
                String url = "https://cheatersgetbanned.me/api/updates?uuid="+uuid.toString()+"&username="+Shady.mc.getSession().getUsername()+"&version="+Shady.VERSION;
                String response = HttpUtils.fetch(url);
                System.out.println(url);

                if(response != null) {
                    update = new Gson().fromJson(response, Update.class);
                    shouldUpdate = !update.version.equals(Shady.VERSION);
                }
            }
        }, "ShadyAddons-Updater").start();
    }

}
