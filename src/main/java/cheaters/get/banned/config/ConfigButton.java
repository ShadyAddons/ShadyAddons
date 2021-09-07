package cheaters.get.banned.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import cheaters.get.banned.Shady;

import java.awt.*;
import java.util.ArrayList;

public class ConfigButton extends GuiButton {

    public Setting setting;
    private static final int LINE_HEIGHT = Shady.mc.fontRendererObj.FONT_HEIGHT + 2;

    public ConfigButton(Setting setting, int x, int y) {
        super(0, x, y, setting.name);
        this.setting = setting;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(visible) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

            Color color = new Color(0, 0, 0, 80);
            if(Config.isEnabled(setting)) color = new Color(106, 210, 106, 80);
            if(hovered) color = new Color(255, 255, 255, 80);
            if(hovered && Config.isEnabled(setting)) color = new Color(127, 255, 127, 80);

            drawRect(xPosition, yPosition, xPosition + width, yPosition + height, color.getRGB());
            mouseDragged(mc, mouseX, mouseY);

            drawCenteredString(Shady.mc.fontRendererObj, this.displayString, this.xPosition+this.width/2, this.yPosition+(this.height-8)/2, Color.WHITE.getRGB());

            if(hovered && setting.description != null) {
                drawTooltip(wrapStringToArray(setting.description, 200), mouseX, mouseY);
            }
        }
    }

    public void drawTooltip(ArrayList<String> tooltip, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(5, 0, 1);

        int tooltipWidth = getTooltipWidth(tooltip) + 10;
        int tooltipHeight = getTooltipHeight(tooltip) + 10;
        int tooltipX = x + tooltipWidth;
        int tooltipY = y + tooltipHeight;

        drawRect(x, y, tooltipX, tooltipY, Color.BLACK.getRGB());

        int lineCount = 0;
        for(String line : tooltip) {
            Shady.mc.fontRendererObj.drawString(line, x+5, y+5+LINE_HEIGHT*lineCount, Color.WHITE.getRGB());
            lineCount++;
        }

        GlStateManager.popMatrix();
    }

    private int getTooltipHeight(ArrayList<String> tooltip) {
        return tooltip.size() * LINE_HEIGHT - 2;
    }

    private int getTooltipWidth(ArrayList<String> tooltip) {
        int longestWidth = 0;
        for(String line : tooltip) {
            int lineWidth = Shady.mc.fontRendererObj.getStringWidth(line);
            if(lineWidth > longestWidth) longestWidth = lineWidth;
        }
        return longestWidth;
    }

    public ArrayList<String> wrapStringToArray(String text, int approxWidth) {
        ArrayList<String> output = new ArrayList<>();
        String tooltip = "";

        for(String word : text.split(" ")) {
            int widthWithNextWord = Shady.mc.fontRendererObj.getStringWidth(tooltip + word);
            if(widthWithNextWord > approxWidth) {
                output.add(tooltip.trim()); // Line is longer than the width, add it to the list
                tooltip = word + " ";
            } else {
                tooltip += word + " "; // Line isn't long enough yet, add another word
            }
        }
        output.add(tooltip.trim());

        return output;
    }

}
