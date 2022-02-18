package cheaters.get.banned.remote;

import cheaters.get.banned.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DisableFeatures {

    public static List<String> load() {
        String url = "https://shadyaddons.com/api/disabled-features.json";
        String response = null;

        try {
            response = HttpUtils.get(url);
        } catch(Exception ignored) {}

        if(response != null) {
            try {
                return Arrays.asList(new Gson().fromJson(response, String[].class));
            } catch(Exception ignored) {}
        }

        return Collections.emptyList();
    }

}
