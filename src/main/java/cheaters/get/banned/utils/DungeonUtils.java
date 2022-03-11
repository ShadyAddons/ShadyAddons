package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.PacketEvent;
import cheaters.get.banned.events.TickEndEvent;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.StringUtils;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DungeonUtils {

    public static Floor floor = null;
    public static int secretsFound = 0;
    public static int cryptsFound = 0;
    public static boolean inBoss = false;
    public static boolean foundMimic = false;
    public static int deaths = 0;
    public static ArrayList<EntityPlayer> teammates = new ArrayList<>();
    public static boolean activeRun = false;

    public static void reset() {
        floor = null;
        secretsFound = 0;
        cryptsFound = 0;
        inBoss = false;
        foundMimic = false;
        deaths = 0;
        teammates.clear();
        activeRun = false;
    }

    public enum Floor {
        ENTERANCE("(E)"),
        FLOOR_1("(F1)"),
        FLOOR_2("(F2)"),
        FLOOR_3("(F3)"),
        FLOOR_4("(F4)"),
        FLOOR_5("(F5)"),
        FLOOR_6("(F6)"),
        FLOOR_7("(F7)"),
        MASTER_1("(M1)"),
        MASTER_2("(M2)"),
        MASTER_3("(M3)"),
        MASTER_4("(M4)"),
        MASTER_5("(M5)"),
        MASTER_6("(M6)"),
        MASTER_7("(M7)");

        public final String name;

        Floor(String name) {
            this.name = name;
        }
    }

    private static final Pattern deathsPattern = Pattern.compile("§r§a§lDeaths: §r§f\\((?<deaths>\\d+)\\)§r");
    private static final Pattern secretsPattern = Pattern.compile("§r Secrets Found: §r§b(?<secrets>\\\\d+)§r");
    private static final Pattern cryptsPattern = Pattern.compile("§r Crypts: §r§6(?<crypts>\\\\d+)§r");
    private static final List<String> entryMessages = Arrays.asList(
            "[BOSS] Bonzo: Gratz for making it this far, but I’m basically unbeatable.",
            "[BOSS] Scarf: This is where the journey ends for you, Adventurers.",
            "[BOSS] The Professor: I was burdened with terrible news recently...",
            "[BOSS] Thorn: Welcome Adventurers! I am Thorn, the Spirit! And host of the Vegan Trials!",
            "[BOSS] Livid: Welcome, you arrive right on time. I am Livid, the Master of Shadows.",
            "[BOSS] Sadan: So you made it all the way here...and you wish to defy me? Sadan?!",
            "[BOSS] Maxor: WELL WELL WELL LOOK WHO’S HERE!"
    );
    private static final String[] mimicMessages = new String[]{
            "Mimic Dead!",
            "$SKYTILS-DUNGEON-SCORE-MIMIC$",
            "Child Destroyed!",
            "Mimic Obliterated!",
            "Mimic Exorcised!",
            "Mimic Destroyed!",
            "Mimic Annhilated!"
    };

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatPacket(PacketEvent.ReceiveEvent event) {
        if(Utils.inDungeon && event.packet instanceof S02PacketChat) {
            String text = StringUtils.stripControlCodes(((S02PacketChat) event.packet).getChatComponent().getUnformattedText());
            if("[NPC] Mort: Here, I found this map when I first entered the dungeon.".equals(text)) {
                updateTeammates(getTabList());
            } else {
                if(entryMessages.contains(text)) {
                    inBoss = true;
                } else {
                    for(String message : mimicMessages) {
                        if(text.contains(message)) {
                            foundMimic = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if(Utils.inDungeon && !foundMimic) {
            if(event.entity.getClass() == EntityZombie.class) {
                EntityZombie entity = (EntityZombie) event.entity;
                if(entity.isChild()) {
                    if(entity.getCurrentArmor(0) == null && entity.getCurrentArmor(1) == null && entity.getCurrentArmor(2) == null && entity.getCurrentArmor(3) == null) {
                        foundMimic = true;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(!Utils.inDungeon || !event.every(10)) return;

        if(floor == null) {
            activeRun = updateFloor();
            if(!activeRun) return;
        }

        List<String> tabList = getTabList();
        if(tabList != null) {
            updateStats(tabList);
            if(teammates.isEmpty()) updateTeammates(tabList);
        }
    }

    /**
     * Scrapes players from the tab list and finds their corresponding {@link EntityPlayer} object
     *
     * @param tabList A tab list obtained from {@link DungeonUtils#getTabList()}
     */
    private static void updateTeammates(List<String> tabList) {
        teammates.clear();
        for(int i = 0; i < 4; i++) {
            String text = StringUtils.stripControlCodes(tabList.get(1 + i * 4)).trim();
            String username = text.split(" ")[0];
            if(Objects.equals(username, "")) continue;
            for(EntityPlayer playerEntity : Shady.mc.theWorld.playerEntities) {
                if(playerEntity.getName().equals(username)) {
                    teammates.add(playerEntity);
                }
            }
        }
    }

    /**
     * Scrapes the floor from the scoreboard
     */
    private static boolean updateFloor() {
        String cataLine = ScoreboardUtils.getLineThatContains("The Catacombs");

        if(cataLine != null) {
            for(Floor floorOption : Floor.values()) {
                if(cataLine.contains(floorOption.name)) {
                    floor = floorOption;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Updates miscellaneous dungeon stats
     *
     * @param tabList A tab list obtained from {@link DungeonUtils#getTabList()}
     */
    private static void updateStats(List<String> tabList) {
        try {
            for(String item : tabList) {
                if(item.contains("Deaths: ")) {
                    Matcher deathsMatcher = deathsPattern.matcher(item);
                    if(!deathsMatcher.matches()) continue;
                    deaths = Integer.parseInt(deathsMatcher.group("deaths"));
                } else if(item.contains("Secrets Found: ")) {
                    Matcher secretsMatcher = secretsPattern.matcher(item);
                    if(!secretsMatcher.matches()) continue;
                    secretsFound = Integer.parseInt(secretsMatcher.group("secrets"));
                } else if(item.contains("Crypts: ")) {
                    Matcher cryptsMatcher = secretsPattern.matcher(item);
                    if(!cryptsMatcher.matches()) continue;
                    cryptsFound = Integer.parseInt(cryptsMatcher.group("crypts"));
                }
            }
        } catch(Exception exception) {
            exception.printStackTrace();
            Utils.sendModMessage("&cException in class DungeonUtils");
        }
    }

    /**
     * Gets the tab list for the dungeon as a {@link List<String>}
     *
     * @return Returns null if the list is not a dungeon's tab list
     */
    private static List<String> getTabList() {
        List<String> tabList = TabUtils.getTabList();
        if(tabList.size() < 18 || !tabList.get(0).contains("§r§b§lParty §r§f(")) return null;
        return tabList;
    }

    /**
     * Checks if the player is currently in one of the given floors
     */
    public static boolean inFloor(Floor...floors) {
        for(Floor floorToCheck : floors) {
            if(floorToCheck == floor) return true;
        }
        return false;
    }

    public static void debug() {
        if(Utils.inDungeon) {
            Utils.sendModMessage("Floor: " + floor.name());
            Utils.sendModMessage("In Boss: " + inBoss);
            Utils.sendModMessage("Secrets Found: " + secretsFound);
            Utils.sendModMessage("Crypts Found: " + cryptsFound);
            Utils.sendModMessage("Team:");
            for(EntityPlayer teammate : teammates) {
                Utils.sendModMessage("- " + teammate.getName());
            }
        } else {
            Utils.sendMessage("You must be in a dungeon to debug a dungeon!");
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        reset();
    }

}
