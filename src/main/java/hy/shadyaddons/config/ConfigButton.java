package hy.shadyaddons.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

/**
 * Modified from ChatShortcuts under MIT License
 * https://github.com/P0keDev/ChatShortcuts/blob/master/LICENSE
 */
public class ConfigButton extends GuiButton {

    public Config.Setting setting;

    public ConfigButton(Config.Setting setting, int x, int y) {
        super(0, x, y, (Config.isEnabled(setting) ? "§a" : "§f") + setting.name + ": " + (Config.isEnabled(setting) ? "ON" : "OFF"));
        this.setting = setting;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            drawRect(xPosition, yPosition, xPosition + width, yPosition + height, (hovered ? new Color(255, 255, 255, 80).getRGB() : new Color(0, 0, 0, 80).getRGB()));
            this.mouseDragged(mc, mouseX, mouseY);

            int j = 14737632;
            if(packedFGColour != 0) {
                j = packedFGColour;
            } else if(!this.enabled) {
                j = 10526880;
            } else if(this.hovered) {
                j = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }

}
