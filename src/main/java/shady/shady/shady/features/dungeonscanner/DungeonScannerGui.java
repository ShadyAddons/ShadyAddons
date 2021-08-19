package shady.shady.shady.features.dungeonscanner;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import shady.shady.shady.utils.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class DungeonScannerGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        HashMap<BlockPos, DungeonScanner.Tile> tiles = DungeonScanner.scan();
        for(Map.Entry<BlockPos, DungeonScanner.Tile> tile : tiles.entrySet()) {
            BlockPos pos = tile.getKey();
            DungeonScanner.Tile corner = tile.getValue();

            int color = corner.type.color.getRGB();

            int topLeftX = pos.getX();
            if(corner.gapWest) topLeftX += 1;

            int topLeftY = pos.getZ();
            if(corner.gapNorth) topLeftY -= 1;

            int bottomRightX = pos.getX() + 32;

            int bottomRightY = pos.getZ() - 32;

            GlStateManager.pushMatrix();
            GlStateManager.translate(100, 100, 0);
            drawRect(topLeftX, topLeftY, bottomRightX, bottomRightY, color);
            RenderUtils.drawString("shadyaddons scuffed dungeon scanner 1.0", 300, -10);
            RenderUtils.drawString("§nPuzzles:§r\n"+DungeonScanner.getPuzzlesText(), 300, 0);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void initGui() {
        DungeonScanner.scan();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
