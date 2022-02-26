package cheaters.get.banned.stats;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.routines.Routines;
import cheaters.get.banned.utils.HttpUtils;
import cheaters.get.banned.utils.Utils;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.URIBuilder;

import java.io.File;
import java.util.UUID;

public class RoutinesAPI {

    public static void sync() {
        new Thread(() -> {
            String serverId = UUID.randomUUID().toString().replace("-", "");

            try {
                Shady.mc.getSessionService().joinServer(Shady.mc.getSession().getProfile(), Shady.mc.getSession().getToken(), serverId);
            } catch(Exception ignored) {
                return;
            }

            try {
                URIBuilder url = new URIBuilder("https://shadyaddons.com/api/routines/list")
                        .addParameter("username", Shady.mc.getSession().getUsername())
                        .addParameter("uuid", Shady.mc.getSession().getPlayerID())
                        .addParameter("server_id", serverId);

                String json = new Gson().toJson(Routines.routinesJson);
                System.out.println(json);
                HttpUtils.post(url.toString(), json);
            } catch(Exception ignored) {}
        }, "ShadyAddons-RoutineListAPI").start();
    }

    public static void download(String id) {
        new Thread(() -> {
            try {
                String response = HttpUtils.get("https://shadyaddons.com/api/routines/download?id=" + id);

                if(response != null) {
                    File file = new File(Routines.routinesDir, id + ".json");
                    FileUtils.writeStringToFile(file, response);
                    Routines.load();

                    if(Shady.mc.theWorld != null) {
                        Utils.sendModMessage("&aInstalled and loaded routine!");
                    }
                } else {
                    if(Shady.mc.theWorld != null) {
                        Utils.sendModMessage("&cA routine with that ID does not exist!");
                    }
                }
            } catch(Exception ignored) {}
        }, "ShadyAddons-InstallRoutineAPI").start();
    }

    public static void openAuthWebsite() {
        try {
            String serverId = UUID.randomUUID().toString().replace("-", "");

            Shady.mc.getSessionService().joinServer(Shady.mc.getSession().getProfile(), Shady.mc.getSession().getToken(), serverId);

            URIBuilder url = new URIBuilder("https://shadyaddons.com/auth")
                    .addParameter("username", Shady.mc.getSession().getUsername())
                    .addParameter("redirect", "routines")
                    .addParameter("server_id", serverId);
            Utils.openUrl(url.toString());
        } catch(Exception ignored) {}
    }

}
