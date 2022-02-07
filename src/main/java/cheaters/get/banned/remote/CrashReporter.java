package cheaters.get.banned.remote;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.MathUtils;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;

public class CrashReporter {

    // Anti-Fire Bot
    public static void douse(File file) {
        try {
            String crashReport = IOUtils.toString(new FileInputStream(file));

            for(String keyword : new String[]{
                    "PizzaClient",
                    "RoseGoldAddons",
                    "ShadyAddons",
                    "kmv5",
                    "modid-1.0-all",
                    "Oringo",
                    "SkyblockClient",
                    "cheeto",
                    "cf4m",
                    "liquidbounce",
                    "cheaters.get.banned",
                    "me.oringo.oringoclient",
                    "examplemod",
                    "OringoClient-1.3-@everyone",
                    "Shady"
            }) {
                crashReport = crashReport.replace(keyword, StringUtils.repeat('A', MathUtils.random(1, 25)));
            }

            IOUtils.write(crashReport, new FileWriter(file));
        } catch(Exception ignored) {}
    }

    public static String hashMod() {
        ModContainer mod = Loader.instance().getIndexedModList().get(Shady.MOD_ID);
        if(mod != null) {
            File file = mod.getSource();
            if(file != null) {
                try {
                    InputStream inputStream = Files.newInputStream(file.toPath());
                    return DigestUtils.sha256Hex(inputStream);
                } catch(Exception ignored) {}
            }
        }
        return null;
    }

}
