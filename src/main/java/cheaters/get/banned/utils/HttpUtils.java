package cheaters.get.banned.utils;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URL;

public class HttpUtils {

    public static String get(String url) {
        String response = null;

        HttpClientBuilder client = HttpClients.custom().addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
            if(!request.containsHeader("Pragma")) request.addHeader("Pragma", "no-cache");
            if(!request.containsHeader("Cache-Control")) request.addHeader("Cache-Control", "no-cache");
        });

        client.setUserAgent("ShadyAddons/@VERSION@");

        try {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse httpResponse = client.build().execute(request);
            if(httpResponse.getStatusLine().getStatusCode() < 299) {
                response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            } else {
                Utils.log("Error code " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception ignored) {}

        return response;
    }

    /**
     * Posts JSON data to a URL
     *
     * @return Returns the {@link String} response or null if something goes wrong
     */
    public static String post(String url, String jsonData) {
        String response = null;

        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(jsonData);
            request.setEntity(params);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("User-Agent", "ShadyAddons/@VERSION@");
            HttpResponse httpResponse = client.execute(request);
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch(Exception ignored) {}

        return response;
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
        } catch(Exception e) {
            return false;
        }

        return true;
    }

}
