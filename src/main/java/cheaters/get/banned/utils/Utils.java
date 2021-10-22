package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.TickEndEvent;
import com.google.common.collect.Iterables;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.util.List;
import java.util.*;

public class Utils {

    public static boolean inSkyBlock = false;
    public static boolean inDungeon = false;
    public static boolean forceSkyBlock = false;
    public static boolean forceDungeon = false;

    public static int romanToInt(String s) {
        Map<Character, Integer> numerals = new HashMap<>();
        numerals.put('I', 1);
        numerals.put('V', 5);
        numerals.put('X', 10);
        numerals.put('L', 50);
        numerals.put('C', 100);
        numerals.put('D', 500);
        numerals.put('M', 1000);

        int result = 0;
        for(int i = 0; i < s.length(); i++) {
            int add = numerals.get(s.charAt(i));
            if(i < s.length() - 1) {
                int next = numerals.get(s.charAt(i + 1));
                if(next / add == 5 || next / add == 10) {
                    add = next - add;
                    i++;
                }
            }
            result = result + add;
        }

        return result;
    }

    public static String getLogo() {
        final ArrayList<String> logos = new ArrayList<>(Arrays.asList("logo-fsr", "logo-sbe", "logo-pride"));

        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        // December 21-31 (Christmas)
        if(month == Calendar.DECEMBER && day > 20) return "logo-christmas";
        // October 26-31 (Halloween)
        if(month == Calendar.OCTOBER && day > 25) return "logo-halloween";
        // October 11 (National Coming Out Day)
        if(month == Calendar.OCTOBER && day == 11) return "logo-pride";

        // 80% Chance For Normal Logo
        if(Math.random() < 0.8) return "logo";

        return (String) getRandomItem(logos);
    }

    public static Object getRandomItem(List<?> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static void openUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch(Exception ignored) {}
    }

    public static String removeFormatting(String input) {
        return input.replaceAll("§[0-9a-fk-or]", "");
    }

    public static String getSkyBlockID(ItemStack item) {
        if(item != null) {
            NBTTagCompound extraAttributes = item.getSubCompound("ExtraAttributes", false);
            if(extraAttributes != null && extraAttributes.hasKey("id")) {
                return extraAttributes.getString("id");
            }
        }
        return "";
    }

    public static boolean facingBlock(BlockPos block, float range) {
        float stepSize = 0.15f;
        if(Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
            Vector3f position = new Vector3f((float) Shady.mc.thePlayer.posX, (float) Shady.mc.thePlayer.posY+ Shady.mc.thePlayer.getEyeHeight(), (float) Shady.mc.thePlayer.posZ);

            Vec3 look = Shady.mc.thePlayer.getLook(0);

            Vector3f step = new Vector3f((float) look.xCoord, (float) look.yCoord, (float) look.zCoord);
            step.scale(stepSize/step.length());

            for(int i = 0; i < Math.floor(range/stepSize)-2; i++) {
                BlockPos blockAtPos = new BlockPos(position.x, position.y, position.z);
                if(blockAtPos.equals(block)) return true;
                position.translate(step.x, step.y, step.z);
            }
        }
        return false;
    }

    public static List<String> getLore(ItemStack item) {
        if(item != null) {
            return item.getTooltip(Shady.mc.thePlayer, false);
        } else {
            return null;
        }
    }

    public static String getInventoryName() {
        if(Shady.mc.thePlayer == null || Shady.mc.theWorld == null) return "null";
        return Shady.mc.thePlayer.openContainer.inventorySlots.get(0).inventory.getName();
    }

    public static String getGuiName(GuiScreen gui) {
        if(gui instanceof GuiChest) {
            return ((ContainerChest) ((GuiChest) gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();
        }
        return "";
    }

    public static void sendMessage(String message) {
        if(Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
            if(!message.contains("§")) {
                message = message.replace("&", "§");
            }
            Shady.mc.thePlayer.addChatMessage(new ChatComponentText(message));
        }
    }

    public static void sendMessageAsPlayer(String message) {
        Shady.mc.thePlayer.sendChatMessage(message);
    }

    public static void sendModMessage(String message) {
        if(message.contains("§")) {
            sendMessage("§cShadyAddons > §f" + message);
        } else {
            sendMessage("&cShadyAddons > &f" + message);
        }
    }

    public static Color addAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static boolean isInteractable(Block block) {
        return new ArrayList<>(Arrays.asList(
                Blocks.chest,
                Blocks.lever,
                Blocks.trapped_chest,
                Blocks.wooden_button,
                Blocks.stone_button,
                Blocks.skull
        )).contains(block);
    }

    public static void copyToClipboard(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
    }

    public static <T> T firstOrNull(Iterable<T> iterable) {
        return Iterables.getFirst(iterable, null);
    }

    @SafeVarargs
    public static <T> T firstNotNull(T...args) {
        for(T arg : args) {
            if(arg != null) {
                return arg;
            }
        }
        return null;
    }

    private int ticks = 0;
    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(forceDungeon || forceSkyBlock) {
            if(forceSkyBlock) inSkyBlock = true;
            if(forceDungeon) inSkyBlock = true; inDungeon = true;
            return;
        }

        if(ticks % 20 == 0) {
            if(Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
                ScoreObjective scoreboardObj = Shady.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
                if(scoreboardObj != null) {
                    inSkyBlock = removeFormatting(scoreboardObj.getDisplayName()).contains("SKYBLOCK");
                }

                inDungeon = inSkyBlock && ScoreboardUtils.scoreboardContains("The Catacombs");
            }
            ticks = 0;
        }
        ticks++;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        forceDungeon = false;
        forceSkyBlock = false;
    }

}
