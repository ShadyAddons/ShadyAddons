package shady.shady.shady.utils;

import com.google.common.collect.Iterables;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.util.vector.Vector3f;
import shady.shady.shady.Shady;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static boolean inSkyBlock = false;
    public static boolean inDungeon = false;
    public static boolean forceSkyBlock = false;
    public static boolean forceDungeon = false;

    public static void openUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch(Exception ignored) {}
    }

    /**
     * Remove Minecraft chat formatting from a message
     * @param input The string to clean
     * @return The cleaned string
     */
    public static String removeFormatting(String input) {
        return input.replaceAll("§[0-9a-fk-or]", "");
    }

    /**
     * Get SkyBlock ID
     * @param item The item to check
     * @return The item's ID, or null if it doesn't have one
     */
    public static String getSkyBlockID(ItemStack item) {
        if(item != null) {
            NBTTagCompound extraAttributes = item.getSubCompound("ExtraAttributes", false);
            if(extraAttributes != null && extraAttributes.hasKey("id")) {
                return extraAttributes.getString("id");
            }
        }
        return null;
    }

    /**
     * Check if player is looking at block using raytracing
     * @param block The block position to check
     * @return If the player is looking at the provided block
     */
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

    /**
     * Get the description (lore) of an item
     * @param item The item to get the lore for
     * @return The provided item's lore
     */
    public static List<String> getLore(ItemStack item) {
        if(item != null) {
            return item.getTooltip(Shady.mc.thePlayer, false);
        } else {
            return null;
        }
    }

    /**
     * Get inventory name
     * @return The inventory name, or "null" (as a string) if null
     */
    public static String getInventoryName() {
        String inventoryName = Shady.mc.thePlayer.openContainer.inventorySlots.get(0).inventory.getName();
        if(inventoryName == null) return "null";
        return inventoryName;
    }

    /**
     * Send chat message
     * @param message The text to be sent, can include § or & as formatting codes
     */
    public static void sendMessage(String message) {
        if(Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
            if(!message.contains("§")) {
                message = message.replace("&", "§");
            }
            Shady.mc.thePlayer.addChatMessage(new ChatComponentText(message));
        } else {
            System.out.println("Unable to send chat message, player is null: "+message);
        }
    }

    /**
     * Send mod message
     * @param message The message to be sent with the mod's prefix
     */
    public static void sendModMessage(String message) {
        if(message.contains("§")) {
            sendMessage("§3ShadyAddons > §f" + message);
        } else {
            sendMessage("&3ShadyAddons > &f" + message);
        }
    }

    /**
     * Add alpha (transparency) to a color
     * @param color The color to modify
     * @param alpha The alpha amount to set, as a percentage
     * @return The original color with the alpha channel
     */
    public static Color addAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Check if block is interactable
     * @param block The block to check
     * @return Whether or not the block can be interacted with
     */
    public static boolean isInteractable(Block block) {
        return new ArrayList<Block>(Arrays.asList(
                Blocks.acacia_door,
                Blocks.anvil,
                Blocks.beacon,
                Blocks.bed,
                Blocks.birch_door,
                Blocks.brewing_stand,
                Blocks.command_block,
                Blocks.crafting_table,
                Blocks.chest,
                Blocks.dark_oak_door,
                Blocks.daylight_detector,
                Blocks.daylight_detector_inverted,
                Blocks.dispenser,
                Blocks.dropper,
                Blocks.enchanting_table,
                Blocks.ender_chest,
                Blocks.furnace,
                Blocks.hopper,
                Blocks.jungle_door,
                Blocks.lever,
                Blocks.noteblock,
                Blocks.powered_comparator,
                Blocks.unpowered_comparator,
                Blocks.powered_repeater,
                Blocks.unpowered_repeater,
                Blocks.standing_sign,
                Blocks.wall_sign,
                Blocks.trapdoor,
                Blocks.trapped_chest,
                Blocks.wooden_button,
                Blocks.stone_button,
                Blocks.oak_door,
                Blocks.skull)).contains(block);
    }

    public static void copyToClipboard(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
    }

    public static <T> T firstOrNull(Iterable<T> iterable) {
        return Iterables.getFirst(iterable, null);
    }

    /**
     * Sets the inSkyBlock and inDungeon variables based off the scoreboard
     */
    private int ticks = 0;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
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
