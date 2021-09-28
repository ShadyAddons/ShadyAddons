package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

    public static String fetch(String url) {
        return fetch(url, true);
    }

    public static String fetch(String url, boolean includeUserAgent) {
        String response = null;

        HttpClientBuilder client = HttpClients.custom().addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
            if(!request.containsHeader("Pragma")) request.addHeader("Pragma", "no-cache");
            if(!request.containsHeader("Cache-Control")) request.addHeader("Cache-Control", "no-cache");
        });

        if(includeUserAgent) client.setUserAgent("ShadyAddons/" + Shady.VERSION);

        try {
            HttpGet request = new HttpGet(url);
            response = EntityUtils.toString(client.build().execute(request).getEntity(), "UTF-8");
        } catch (Exception ignored) {}

        return response;
    }

}
