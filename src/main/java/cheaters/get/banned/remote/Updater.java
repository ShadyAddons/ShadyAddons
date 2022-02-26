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
        public int users;

        public Update(String version, String download, String description, int users) {
            this.version = version;
            this.download = download;
            this.description = description;
            this.users = users;
        }
    }

    public static void check() {
        new Thread(() -> {
            String url = "https://shady" + "addons.com/api/updates"; // Safeguard for build.gradle
            String response = null;
            try {
                response = HttpUtils.get(url);
            } catch(Exception ignored) {}

            if(response != null) {
                update = new Gson().fromJson(response, Update.class);
                shouldUpdate = !update.version.equals(Shady.VERSION);
                // int comparison = new DefaultArtifactVersion(update.version).compareTo(new DefaultArtifactVersion(Shady.VERSION));
                // shouldUpdate = comparison > 0;
            } else {
                System.out.println("Error checking for updates");
            }
        }, "ShadyAddons-Updater").start();
    }

}
