package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.ResourceLocation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EstonianUtils {

    private static HashMap<String, String> englishToEstonian = new HashMap<>();
    private static final File folkSongFile = new File(Shady.dir, "folk-music.wav");

    public static boolean isEstoniaDay() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return month == Calendar.APRIL && day == 1;
    }

    public static void loadEstonian() {
        try {
            downloadFolkSong();

            final ResourceLocation estonianFile = new ResourceLocation("shadyaddons:estonian.json");
            BufferedReader estonianReader = new BufferedReader(new InputStreamReader(Shady.mc.getResourceManager().getResource(estonianFile).getInputStream()));
            JsonObject estonianJson = new JsonParser().parse(estonianReader).getAsJsonObject();

            for(Map.Entry<String, JsonElement> pair : estonianJson.entrySet()) {
                englishToEstonian.put(
                        pair.getKey(),
                        pair.getValue().getAsString()
                );
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String replaceEstonian(String notEstonian) {
        if(notEstonian == null) return null;

        for(Map.Entry<String, String> pair : englishToEstonian.entrySet()) {
            String estonian = notEstonian.replace(pair.getKey(), pair.getValue());
            if(!estonian.equals(notEstonian)) return estonian;
        }

        return notEstonian;
    }

    public static void playFolkSong() {
        try {
            if(!folkSongFile.exists()) return;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(folkSongFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void downloadFolkSong() {
        if(folkSongFile.exists()) return;
        try {
            Files.copy(new URL("https://shadyaddons.com/assets/folk-music.wav").openStream(), folkSongFile.toPath());
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

}
