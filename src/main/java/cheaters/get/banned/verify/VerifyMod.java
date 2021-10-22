package cheaters.get.banned.verify;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.HttpUtils;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class VerifyMod {

    public static void verify() {
        ModContainer mod = Loader.instance().getIndexedModList().get(Shady.MODID);
        if(mod != null) {
            File file = mod.getSource();
            if(file != null) {
                try {
                    InputStream inputStream = Files.newInputStream(file.toPath());
                    String hash = DigestUtils.sha256Hex(inputStream);

                    // Intentionally Blocking
                    String allHashes = HttpUtils.fetch("https://cheatersgetbanned.me/hashes.txt");
                    if(allHashes != null && allHashes.contains(Shady.VERSION + ": " + hash)) {
                        return;
                    }
                } catch(Exception ignored) {}
            }
        }
        Shady.guiToOpen = new DangerousModGui();
    }

}
