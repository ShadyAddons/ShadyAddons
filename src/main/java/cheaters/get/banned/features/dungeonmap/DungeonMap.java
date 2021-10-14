package cheaters.get.banned.features.dungeonmap;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.FontUtils;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class DungeonMap {

    private static int xCorner;
    private static int zCorner;
    public static DungeonLayout activeDungeonLayout = null;
    public static boolean scanning = false;
    public static boolean debug = false;

    public static int x = 10;
    public static int y = 10;
    public static float scale = 1f;

    public DungeonMap() {
        // KeybindUtils.register("Peek Rooms", Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        activeDungeonLayout = null;
        scanning = false;
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Utils.inDungeon) {
            if(activeDungeonLayout == null) {
                if(DungeonUtils.dungeonRun != null && DungeonUtils.dungeonRun.floor != null && !scanning) {
                    switch(DungeonUtils.dungeonRun.floor) {
                        case FLOOR_1:
                        case MASTER_1:
                            xCorner = 127;
                            zCorner = 159;
                            break;

                        case FLOOR_2:
                        case MASTER_2:
                            xCorner = 159;
                            zCorner = 159;
                            break;

                        default:
                            xCorner = 191;
                            zCorner = 191;
                    }

                    if(Shady.mc.theWorld.getChunkFromBlockCoords(new BlockPos(xCorner, 70, zCorner)).isLoaded() && Shady.mc.theWorld.getChunkFromBlockCoords(new BlockPos(0, 70, 0)).isLoaded()) {
                        scanning = true;
                        new Thread(() -> {
                            activeDungeonLayout = DungeonScanner.scan(xCorner, zCorner);
                            scanning = false;
                        }, "ShadyAddons-DungeonScanner").start();
                    }
                }
            } else if(Config.announceScore && !activeDungeonLayout.sentScoreMessage && DungeonUtils.calculateScore() >= Config.announceScoreNumber) {
                activeDungeonLayout.sentScoreMessage = true;
                String chatPrefix = new String[]{"/pc", "/ac", "/gc", "/r"}[Config.announceScoreChat];
                Utils.sendMessageAsPlayer(chatPrefix + " ShadyAddons: " + Config.announceScoreNumber + " score reached");
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(activeDungeonLayout != null) {
            if(debug) {
                for(DungeonLayout.ConnectorTile connectorTile : activeDungeonLayout.connectorTiles) {
                    if(connectorTile.type == null) continue;
                    RenderUtils.highlightBlock(new BlockPos(connectorTile.x, 69, connectorTile.z), connectorTile.type == DungeonLayout.ConnectorTile.Type.CONNECTOR ? Color.BLUE : connectorTile.type.color, event.partialTicks);
                }

                for(DungeonLayout.RoomTile roomTile : activeDungeonLayout.roomTiles) {
                    RenderUtils.highlightBlock(new BlockPos(roomTile.x, 99, roomTile.z), roomTile.room.type.color, event.partialTicks);
                }
            }

            if(Config.witherDoorESP && DungeonUtils.dungeonRun != null && !DungeonUtils.dungeonRun.inBoss) {
                for(DungeonLayout.ConnectorTile door : activeDungeonLayout.connectorTiles) {
                    if(!door.isOpen && door.type == DungeonLayout.ConnectorTile.Type.WITHER_DOOR || door.type == DungeonLayout.ConnectorTile.Type.BLOOD_DOOR) {
                        Color color = Config.witherDoorColor == 0 ? Color.WHITE : Color.BLACK;
                        if(door.type == DungeonLayout.ConnectorTile.Type.BLOOD_DOOR) color = Color.RED;

                        Iterable<BlockPos> positions;
                        if(door.direction == EnumFacing.NORTH || door.direction == EnumFacing.SOUTH) positions = BlockPos.getAllInBox(door.getPosition(69).west(1), door.getPosition(72).east(1));
                        else positions = BlockPos.getAllInBox(door.getPosition(69).north(1), door.getPosition(72).south(1));

                        for(BlockPos position : positions) {
                            RenderUtils.highlightBlock(position, color, event.partialTicks);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if(!Config.dungeonMap || event.type != RenderGameOverlayEvent.ElementType.HOTBAR || !Utils.inDungeon || DungeonUtils.dungeonRun == null || DungeonUtils.dungeonRun.inBoss) return;

        x = Config.mapXOffset;
        y = Config.mapYOffset;

        // Draw Map Background
        if(!scanning && activeDungeonLayout != null && Config.showDungeonInformation) {
            Gui.drawRect(x, y, x+200, y+230, new Color(0, 0, 0, 255*Config.mapBackgroundOpacity/100).getRGB());
        } else {
            Gui.drawRect(x, y, x+200, y+200, new Color(0, 0, 0, 255*Config.mapBackgroundOpacity/100).getRGB());
        }

        // Draw Scanning Text
        if(scanning) {
            FontUtils.drawCenteredString("Scanning Dungeon...", x+100, y+100);
            return;
        } else if(activeDungeonLayout == null) {
            FontUtils.drawCenteredString("§cNot Scanned", x+100, y+100);
            return;
        }

        // Center Rooms
        GlStateManager.translate((200-xCorner)/2f, (200-zCorner)/2f, 0);

        // Draw Room Connectors
        for(DungeonLayout.ConnectorTile connector : activeDungeonLayout.connectorTiles) {
            if(connector.type == null) continue;

            int x1 = connector.x - 4;
            int y1 = connector.z - 2; // + w/ border
            int x2 = connector.x + 4;
            int y2 = connector.z + 2; // + w/ border

            if(connector.direction == EnumFacing.EAST || connector.direction == EnumFacing.WEST) {
                x1 = connector.x - 2; // + w/ border
                y1 = connector.z - 6; // + w/ border
                x2 = connector.x + 2; // + w/ border
                y2 = connector.z + 6; // + w/ border
            }

            if(connector.type == DungeonLayout.ConnectorTile.Type.CONNECTOR) {
                x1 = connector.x - 14; // - w/ border
                y1 = connector.z - 2; // + w/ border
                x2 = connector.x + 14; // - w/ border
                y2 = connector.z + 2; // + w/ border

                if(connector.direction == EnumFacing.EAST || connector.direction == EnumFacing.WEST) {
                    x1 = connector.x - 2; // + w/ border
                    y1 = connector.z - 14; // - w/ border
                    x2 = connector.x + 2; // + w/ border
                    y2 = connector.z + 14; // - w/ border
                }
            }

            Gui.drawRect(x+x1, y+y1, x+x2, y+y2, connector.type.color.getRGB());
        }

        // Draw Room Tiles
        for(DungeonLayout.RoomTile room : activeDungeonLayout.roomTiles) {
            Gui.drawRect(x+room.x-14, y+room.z-14, x+room.x+14, y+room.z+14, room.room.type.color.getRGB());
        }

        // Draw Room Names & Secrets
        for(DungeonLayout.RoomTile room : activeDungeonLayout.roomTiles) {
            if(room.room.type == Room.Type.PUZZLE || room.room.type == Room.Type.TRAP && Config.significantRoomNameStyle != 2) {
                switch(Config.significantRoomNameStyle) {
                    case 0: // Shortened Names
                        FontUtils.drawCenteredString(getShortenedRoomName(room.room.name), x+room.x, y+room.z);
                        break;

                    case 1: // Full Names
                        FontUtils.drawCenteredString(room.room.name.replace(" ", "\n"), x+room.x, y+room.z);
                        break;
                }
            } /*else if(room.room.secrets > 0) {
                FontUtils.drawCenteredString(String.valueOf(room.room.secrets), room.x, room.z);
            }*/
        }

        // Draw Player Heads
        if(Shady.mc.thePlayer != null) {
            int size = 14;

            HashSet<String> players = DungeonUtils.dungeonRun.team;
            players.add(Shady.mc.getSession().getUsername());

            for(String player : DungeonUtils.dungeonRun.team) {
                EntityPlayer playerEntity = Shady.mc.theWorld.getPlayerEntityByName(player);
                int playerX = MathHelper.clamp_int(playerEntity.getPosition().getX() - size/2, 0, xCorner-14);
                int playerZ = MathHelper.clamp_int(playerEntity.getPosition().getZ() - size/2, 0, zCorner-14);
                float playerRotation = playerEntity.getRotationYawHead() - 180;
                drawPlayerIcon(playerEntity, size, x+playerX, y+playerZ, (int)playerRotation);
            }
        }

        // Reset Room Centering
        GlStateManager.translate(-((200-xCorner)/2f), -((200-zCorner)/2f), 0);

        // Draw Dungeon Info
        if(Config.showDungeonInformation) {
            String dungeonStats = "§7Secrets: §a"+DungeonUtils.dungeonRun.secretsFound+"§7/"+activeDungeonLayout.totalSecrets+"   Crypts: §a"+DungeonUtils.dungeonRun.cryptsFound+"§7/"+(activeDungeonLayout.uncertainCrypts ? "~" : "")+activeDungeonLayout.totalCrypts+"\n";
            dungeonStats += "§7Puzzles: §a"+activeDungeonLayout.totalPuzzles+"§7"+"   Deaths: §c"+DungeonUtils.dungeonRun.deaths+"§7"+"   Score: §e~"+DungeonUtils.calculateScore();
            FontUtils.drawCenteredString(dungeonStats, x+100, y+207);
        }
    }

    private static void drawPlayerIcon(EntityPlayer player, int size, int x, int y, int angle) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(x+size/2f, y+size/2f, 0);
        GlStateManager.rotate(angle, 0, 0, 1);
        GlStateManager.translate(-x-size/2f, -y-size/2f, 0);

        Gui.drawRect(x, y, x+size, y+size, Color.BLACK.getRGB());
        GlStateManager.color(255, 255, 255);
        RenderUtils.drawPlayerIcon(player, size-2, x+1, y+1);

        GlStateManager.popMatrix();
    }

    public static final HashMap<String, String> shortNames = new HashMap<String, String>(){{
        put("Old Trap", "Old");
        put("New Trap", "New");
        put("Boulder", "Box");
        put("Creeper Beams", "Beams");
        put("Teleport Maze", "Maze");
        put("Ice Path", "S.Fish");
        put("Ice Fill", "Fill");
        put("Tic Tac Toe", "TTT");
        put("Water Board", "Water");
        put("Bomb Defuse", "Bomb");
    }};

    private static String getShortenedRoomName(String longName) {
        String shortName = shortNames.get(longName);
        return shortName == null ? longName : shortName;
    }

}