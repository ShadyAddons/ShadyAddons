package cheaters.get.banned.remote;

import cheaters.get.banned.Shady;
import cheaters.get.banned.stats.Analytics;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class CrashReporter {

    public static void send(File file, String reason) {
        try {
            String serverId = UUID.randomUUID().toString().replace("-", "");
            String hash = Analytics.hashMod();
            String report = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8).replace(Shady.mc.getSession().getProfile().getName(), "<USERNAME>");
            report = Base64.getEncoder().encodeToString(report.getBytes(StandardCharsets.UTF_8));
            reason = Base64.getEncoder().encodeToString(reason.getBytes(StandardCharsets.UTF_8));

            final String note = "Good for you, checking out your mods! See cheaters.get.banned.remove.Analytics for information on how this works!";
            Shady.mc.getSessionService().joinServer(Shady.mc.getSession().getProfile(), Shady.mc.getSession().getToken(), serverId);

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost("https://cheatersgetbanned.me/api/crashes/");

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("username", Shady.mc.getSession().getProfile().getName()));
            parameters.add(new BasicNameValuePair("server_id", serverId));
            parameters.add(new BasicNameValuePair("hash", hash));
            parameters.add(new BasicNameValuePair("version", Shady.VERSION));
            parameters.add(new BasicNameValuePair("report", report));
            parameters.add(new BasicNameValuePair("reason", reason));

            post.setEntity(new UrlEncodedFormEntity(parameters));
            client.execute(post);
        } catch(Exception ignored) {}
    }

}