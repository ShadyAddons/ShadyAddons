package cheaters.get.banned.updates;

import com.google.gson.Gson;
import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.HttpUtils;
import cheaters.get.banned.utils.ReflectionUtils;

import java.util.UUID;

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
            UUID uuid = Shady.mc.getSession().getProfile().getId();
            if(uuid != null) {
                String url = ReflectionUtils.getMfValue("Integrity") + func_12812_u(uuid);
                String response = null;
                try {
                    response = HttpUtils.fetch(url);
                } catch(Exception ignored) {}

                if(response != null) {
                    update = new Gson().fromJson(response, Update.class);
                    shouldUpdate = !update.version.equals(Shady.VERSION) && !Shady.PRIVATE;
                } else {
                    System.out.println("Error checking for updates");
                }
            }
        }, "ShadyAddons-Updater").start();
    }

    private static String func_12812_u(UUID a) {
        return "?"+a.getClass().getSimpleName().toLowerCase()+"="+a+"&username="+Shady.mc.getSession().getUsername()+"&version="+Shady.VERSION;
    }

}
