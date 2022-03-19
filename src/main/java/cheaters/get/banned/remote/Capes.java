package cheaters.get.banned.remote;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.HttpUtils;
import cheaters.get.banned.utils.PatchedGIFReader;
import cheaters.get.banned.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Capes {

    // Maps an MD5-hashed UUID to a cape name
    private static HashMap<String, String> playerCapes = new HashMap<>();
    private static HashMap<String, ResourceLocation> capes = new HashMap<>();
    private static HashMap<String, ArrayList<ResourceLocation>> animatedCapes = new HashMap<>();

    public static final File capesDir = new File(Shady.dir, "cape-cache");

    public static ResourceLocation getCape(NetworkPlayerInfo player) {
        String userHash = DigestUtils.md5Hex(player.getGameProfile().getId().toString().replace("-", ""));
        String capeName = playerCapes.get(userHash);
        if(capeName == null) return null;

        if(capeName.endsWith("_anim")) return getAnimatedCape(capeName);
        return capes.get(capeName);
    }

    private static ResourceLocation getAnimatedCape(String capeName) {
        ArrayList<ResourceLocation> capeFrames = animatedCapes.get(capeName);
        if(capeFrames == null) return null;
        return capeFrames.get((int) (System.currentTimeMillis() / (1000 / 12) % capeFrames.size()));
    }

    public static void load() {
        Utils.log("Downloading capes...");
        capesDir.mkdirs();

        try {
            String capeJson = HttpUtils.get("https://shadyaddons.com/api/capes");

            JsonObject json = new Gson().fromJson(capeJson, JsonObject.class);
            JsonObject jsonCapes = json.get("capes").getAsJsonObject();
            JsonObject jsonOwners = json.get("purchased").getAsJsonObject();

            for(Map.Entry<String, JsonElement> element : jsonCapes.entrySet()) {
                String capeName = element.getKey();
                String capeUrl = element.getValue().getAsString();

                Utils.log("Loading cape: " + capeName + " (" + capeUrl + ")");

                if(capeName.endsWith("_anim")) {
                    animatedCapes.put(capeName, animatedCapeFromFile(capeName, capeUrl));
                } else {
                    capes.put(capeName, capeFromFile(capeName, capeUrl));
                }
            }

            for(Map.Entry<String, JsonElement> capeOwner : jsonOwners.entrySet()) {
                playerCapes.put(capeOwner.getKey(), capeOwner.getValue().getAsString());
            }
        } catch(Exception exception) {
            Utils.log("Error downloading capes");
            exception.printStackTrace();
        }
    }

    /**
     * Gets a cape from the cache or downloads it if it doesn't exist
     */
    private static ResourceLocation capeFromFile(String capeName, String capeUrl) {
        try {
            File file = new File(capesDir, capeName + ".png");
            if(!file.exists()) Files.copy(new URL(capeUrl).openStream(), file.toPath());

            return Shady.mc.getTextureManager().getDynamicTextureLocation(
                    "shadyaddons",
                    new DynamicTexture(ImageIO.read(file))
            );
        } catch(Exception exception) {
            Utils.log("Error loading cape from file/URL");
            exception.printStackTrace();
        }

        return null;
    }

    private static ArrayList<ResourceLocation> animatedCapeFromFile(String capeName, String capeUrl) {
        try {
            File file = new File(capesDir, capeName + ".gif");
            if(!file.exists()) Files.copy(new URL(capeUrl).openStream(), file.toPath());

            ImageReader reader = new PatchedGIFReader(null);
            ImageInputStream stream = ImageIO.createImageInputStream(file);
            reader.setInput(stream);

            ArrayList<ResourceLocation> frames = new ArrayList<>();

            int frameCount = reader.getNumImages(true);
            for(int i = 0; i < frameCount; i++) {
                BufferedImage frame = reader.read(i);
                ResourceLocation frameImage = Shady.mc.getTextureManager().getDynamicTextureLocation(
                        "shadyaddons",
                        new DynamicTexture(frame)
                );
                frames.add(frameImage);
            }

            return frames.isEmpty() ? null : frames;
        } catch(Exception exception) {
            Utils.log("Error loading animated cape from file/URL");
            exception.printStackTrace();
        }

        return null;
    }

}
