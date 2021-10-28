package cheaters.get.banned.config.components;

import cheaters.get.banned.utils.FontUtils;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class SearchComponent extends GuiButton {

    public String text;

    public SearchComponent(int x, int y, int width, String initText) {
        super(0, x, y, width, 18, "");
        text = initText;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        // Magnifying Glass Icon
        RenderUtils.drawTexture(new ResourceLocation("shadyaddons:search.png"), xPosition, yPosition + 2, 14, 14);

        // Background
        drawRect(
                xPosition + 20,
                yPosition,
                xPosition + width,
                yPosition + height,
                ConfigInput.transparent.getRGB()
        );

        // Search Text
        FontUtils.drawScaledString(text, 1.25f, xPosition + 24, yPosition + 4, true);

        int textWidth = (int) (FontUtils.getStringWidth(text) * 1.25f);

        // Blinking Cursor
        if(System.currentTimeMillis()/500 % 2 == 0) {
            drawRect(
                    xPosition+ textWidth +20+1,
                    yPosition + 2,
                    xPosition+ textWidth +20+2,
                    yPosition+height - 2,
                    ConfigInput.white.getRGB()
            );
        }
    }

    public void onKeyTyped(char letter, int code) {
        if(code == Keyboard.KEY_BACK) {
            text = "";
        } else if(ChatAllowedCharacters.isAllowedCharacter(letter)) {
            if(FontUtils.getStringWidth(text + letter) * 1.25 <= width - 2) {
                text += Character.toLowerCase(letter);
            }
        }
    }

}
