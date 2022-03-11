package cheaters.get.banned.features.include.map;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.map.elements.DungeonColors;
import cheaters.get.banned.features.include.map.elements.MapTile;
import cheaters.get.banned.features.include.map.elements.doors.Door;
import cheaters.get.banned.features.include.map.elements.rooms.Separator;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

// Hey future self, I've over-documented this because rendering code can become confusing
public class MapRender {

    public static int tileSize = 8;
    public static int xCorner = 10;
    public static int yCorner = 10;
    public static final int maxMapPx = tileSize * 23; // Max width/height in pixels
    public static final int maxMapBlocks = 197; // Max width/heigh in blocks

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if(MapManager.map == null) return;
        if(event.type != RenderGameOverlayEvent.ElementType.HOTBAR) return;

        // See Config#witherDoorColor
        DungeonColors.WITHER_DOOR = new Color[]{
                DungeonColors.BROWN,
                DungeonColors.DARK_BROWN,
                DungeonColors.BLACK,
                Color.GREEN
        }[Config.witherDoorColor];

        GlStateManager.translate(xCorner, yCorner, 0);

        MapTile[][] elements = MapManager.map.elements;
        for(int rowNum = 0; rowNum < elements.length; rowNum++) {
            MapTile[] row = elements[rowNum];

            for(int colNum = 0; colNum < row.length; colNum++) {
                MapTile tile = row[colNum];
                if(tile == null) continue;

                // (number of rooms * room size) + (number of doors/seperators * door size)
                int x = (int) ((Math.ceil(colNum / 2f) * tileSize * 3) + (Math.floor(colNum / 2f) * tileSize));
                int y = (int) ((Math.ceil(rowNum / 2f) * tileSize * 3) + (Math.floor(rowNum / 2f) * tileSize));

                boolean colEven = colNum % 2 == 0;
                boolean rowEven = rowNum % 2 == 0;

                if(colEven && rowEven) { // Rooms are only in even columns and rows, draw them
                    Gui.drawRect(
                            x,
                            y,
                            x + tileSize * 3,
                            y + tileSize * 3,
                            tile.color
                    );
                } else if(tile instanceof Door) { // Render doors
                    /*
                     * Even column and odd row mean that it must be horizontal because rooms
                     * are in even columns and vertical doors and separators are in odd rows.
                     * Horizontal separators and doors are in even columns and odd rows. Does
                     * that make sense? Try drawing a diagram.
                     */
                    if(colEven) { // Horizontal doors
                        Gui.drawRect(
                                x + tileSize, // Center door (rooms are 3x3, doors are 1x1 on the side)
                                y,
                                x + tileSize * 2, // Again, centering
                                y + tileSize,
                                tile.color
                        );
                    } else { // Vertical doors
                        Gui.drawRect(
                                x,
                                y + tileSize, // More centering, the same thing but vertically
                                x + tileSize,
                                y + tileSize * 2,
                                tile.color
                        );
                    }
                } else if(tile instanceof Separator) { // Render separators
                    if(colEven) { // Horizontal separators (see note above)
                        Gui.drawRect(
                                x,
                                y,
                                x + tileSize * 3, // 3x1 rectangle
                                y + tileSize,
                                tile.color
                        );
                    } else if(rowEven) { // Vertical separators
                        Gui.drawRect(
                                x,
                                y,
                                x + tileSize,
                                y  + tileSize * 3,  // 1x3 rectangle
                                tile.color
                        );
                    } else { // Small square seperator (fills hole in 2x2 rooms)
                        Gui.drawRect(
                                x,
                                y,
                                x + tileSize,
                                y + tileSize,
                                tile.color
                        );
                    }
                }
            }
        }

        // Render player heads
        if(Config.showMapPlayerHeads > 0) {
            int headSize = 14;
            for(EntityPlayer teammate : DungeonUtils.teammates) {
                if(Config.showMapPlayerHeads == 2 && teammate != Shady.mc.thePlayer || teammate.isDead) continue; // Only render own head

                int playerX = (int) ((teammate.getPosition().getX() - MapScanner.xCorner) / (float) maxMapBlocks * maxMapPx);
                int playerZ = (int) ((teammate.getPosition().getZ() - MapScanner.zCorner) / (float) maxMapBlocks * maxMapPx);
                int playerRotation = (int) (teammate.getRotationYawHead() - 180);

                drawPlayerIcon(teammate, headSize, playerX, playerZ, playerRotation);
            }
        }

        GlStateManager.translate(-xCorner, -yCorner, 0);
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

}