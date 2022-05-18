package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.events.ShutdownEvent;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;
import com.google.common.base.Utf8;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class BlockAbilities {

    public static HashMap<String, Integer> blockedAbilities;
    public static HashMap<Integer, String> click;
    public static final File abilities = new File(Shady.dir, "abilities.json");

    public BlockAbilities(){
        KeybindUtils.register("Add/Remove from blocked abilities", Keyboard.KEY_P);
    }

    public static void load(){
        click = new HashMap<>();
        click.put(1, "Right Click");
        click.put(2, "Left Click");
        click.put(3, "Both");
        if (abilities.exists()) {
            Reader reader = null;
            try {
                reader = Files.newBufferedReader(abilities.toPath());
                Type type = new TypeToken<HashMap<String, Integer>>() {
                }.getType();
                blockedAbilities = new Gson().fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                abilities.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(blockedAbilities == null) blockedAbilities = new HashMap<>();
    }

    public static void save(){
        String json = new Gson().toJson(blockedAbilities);
        try {
            Files.write(abilities.toPath(), json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Maybe also with a command to make it easier to choose what ability gets blocked
    //Message looks ugly
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeybindUtils.get("Add/Remove from blocked abilities").isPressed() && Utils.inSkyBlock) {
            if(Shady.mc.thePlayer.getHeldItem() != null){
                String skyBlockID = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
                if(blockedAbilities.containsKey(skyBlockID)){
                    if(blockedAbilities.get(skyBlockID) == 3) {
                        blockedAbilities.remove(skyBlockID);
                        Utils.sendModMessage("Removed " + skyBlockID + " from Blocked Abilities");
                        save();
                        return;
                    }
                }
                blockedAbilities.putIfAbsent(skyBlockID, 0);
                blockedAbilities.replace(skyBlockID, blockedAbilities.get(skyBlockID)+1);
                Utils.sendModMessage("Added " + skyBlockID + " (" + click.get(blockedAbilities.get(skyBlockID)) + ")" + " to Blocked Abilities");
                save();
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(Shady.mc.thePlayer.getHeldItem() == null) return;
        String skyBlockID = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (
                    (Config.blockCellsAlignment && skyBlockID.equals("GYROKINETIC_WAND")) ||
                    (Config.blockGiantsSlam && skyBlockID.equals("GIANTS_SWORD")) ||
                    (Config.blockValkyrie && Utils.inDungeon && skyBlockID.equals("VALKYRIE")) ||
                    (Config.blockCustomAbilities && BlockAbilities.blockedAbilities.containsKey(skyBlockID) && BlockAbilities.blockedAbilities.get(skyBlockID) % 2 == 1)

            ) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onClick(ClickEvent.Left event){
        if(Shady.mc.thePlayer.getHeldItem() == null) return;
        String skyBlockID = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
        if (
                (Config.blockCustomAbilities && BlockAbilities.blockedAbilities.containsKey(skyBlockID) && BlockAbilities.blockedAbilities.get(skyBlockID) >= 2)

        ) {
            KeyBinding.setKeyBindState(Shady.mc.gameSettings.keyBindAttack.getKeyCode(), false);
            event.setCanceled(true);
        }
    }
}
