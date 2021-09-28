package cheaters.get.banned.utils;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

    /**
     * Modified from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     */
    public static CloseableHttpClient client = HttpClients.custom().setUserAgent("ShadyAddons/@VERSION@").addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
        if (!request.containsHeader("Pragma")) request.addHeader("Pragma", "no-cache");
        if (!request.containsHeader("Cache-Control")) request.addHeader("Cache-Control", "no-cache");
    }).build();

    public static String fetch(String url) {
        String response = null;

        try {
            HttpGet request = new HttpGet(url);
            response = EntityUtils.toString(client.execute(request).getEntity(), "UTF-8");
        } catch (Exception exception) {
            System.out.println("There was a problem fetching '"+url.toString()+"': "+exception.getMessage());
        }

        return response;
    }

}
