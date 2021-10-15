package cheaters.get.banned.remote;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.HttpUtils;
import com.google.gson.Gson;

public class Updater {

    public static boolean shouldUpdate = false;
    public static Update update = null;

    public static class Update {
        public String version;
        public String download;
        public String description;

        public Update(String version, String download, String description) {
            this.version = version;
            this.download = download;
            this.description = description;
        }
    }

    public static void check() {
        new Thread(() -> {
            String url = "https://cheatersgetbanned.me/api/updates";
            String response = null;
            try {
                response = HttpUtils.fetch(url);
            } catch(Exception ignored) {}

            if(response != null) {
                update = new Gson().fromJson(response, Update.class);
                shouldUpdate = !update.version.equals(Shady.VERSION);
            } else {
                System.out.println("Error checking for updates");
            }
        }, "ShadyAddons-Updater").start();
    }

}
