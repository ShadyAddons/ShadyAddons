package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class FontUtils {

    public static String enforceWidth(String text, int width) {
        String[] splitText = text.split(" ");
        int lineWidth = 0;
        StringBuilder result = new StringBuilder();
        for(String word : splitText) {
            int wordWidth = getStringWidth(word);
            if(wordWidth + lineWidth > width) {
                result.append("\n");
                lineWidth = 0;
            } else {
                result.append(word).append(" ");
                lineWidth += wordWidth + getStringWidth(" ");
            }
        }
        return result.toString();
    }

    public static void drawCenteredString(String text, int x, int y) {
        y -= getStringHeight(text)/2;
        String[] lines = text.split("\n");
        for(String line : lines) {
            drawString(line, x-getStringWidth(line)/2, y);
            y += getLineHeight() + 1;
        }
    }

    public static void drawString(String text, int x, int y) {
        String[] lines = text.split("\n");
        for(String line : lines) {
            Shady.mc.fontRendererObj.drawStringWithShadow(line, x, y, Color.WHITE.getRGB());
            y += getLineHeight() + 1;
        }
    }

    public static int getStringHeight(String text) {
        int lines = text.split("\n").length;
        return lines > 1 ? lines * (getLineHeight() + 1) - 1 : getLineHeight();
    }

    public static int getStringWidth(String text) {
        String[] lines = text.split("\n");
        int longestLine = 0;
        for(String line : lines) {
            int lineWidth = Shady.mc.fontRendererObj.getStringWidth(line);
            if(lineWidth > longestLine) longestLine = lineWidth;
        }
        return longestLine;
    }

    public static int getLineHeight() {
        return Shady.mc.fontRendererObj.FONT_HEIGHT;
    }

    public static void drawScaledString(String string, float scale, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        Shady.mc.fontRendererObj.drawString(string, (int)(x/scale), (int)(y/scale), -1);
        GlStateManager.popMatrix();
    }

}
