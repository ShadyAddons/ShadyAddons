package cheaters.get.banned.features.commandpalette;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CommandPalette extends GuiScreen {

    public String search = "";
    private ArrayList<Result> results;
    private int selected = 0;

    private static final int ROW_WIDTH = 300;
    private static final int HALF_ROW_WIDTH = ROW_WIDTH / 2;
    private static final int ROW_HEIGHT = 20;
    private static final int ROW_COLOR = new Color(54, 57, 63).getRGB();

    public CommandPalette() {
        ResultList.generateList();
        results = ResultList.getResults(search);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        // Draw Search Bar
        int searchY = getBoxY() + (ROW_HEIGHT - FontUtils.LINE_HEIGHT) / 2;
        FontUtils.drawString(search.length() == 0 ? "§7Start typing..." : search, getBoxX() + 4 + 3, searchY, false);

        // Draw Cursor
        if((System.currentTimeMillis() / 500) % 2 == 0) {
            int cursorX = getBoxX() + 4 + FontUtils.getStringWidth(search) + 1;
            drawRect(cursorX, searchY - 1, cursorX + 1, searchY + FontUtils.LINE_HEIGHT - 1, -1);
        }

        // Draw Each Result
        if(results.size() > 0) {
            for(int i = 0; i < results.size(); i++) {
                Result result = results.get(i);

                int y = getBoxY() + (ROW_HEIGHT + 5) * (i + 1);
                int x = getBoxX();

                drawRect(x, y - 4, x + ROW_WIDTH, y + ROW_HEIGHT, ROW_COLOR);
                result.icon.render(getBoxX() + 4, y);

                y += (ROW_HEIGHT - FontUtils.LINE_HEIGHT) / 2;
                x += 16 + 4 + 4;

                if(result.description != null) {
                    y -= ((0.75f * FontUtils.LINE_HEIGHT) + 2) / 2;

                    FontUtils.drawScaledString(
                            "§7" + result.description,
                            0.75f,
                            x,
                            y + FontUtils.LINE_HEIGHT,
                            false
                    );
                }

                FontUtils.drawString((selected == i ? "§" + FontUtils.getRainbowCode('e') : "") + result.name, x, y, false);
            }
        } else {
            FontUtils.drawCenteredString("No results", getBoxX() + HALF_ROW_WIDTH, (int) (getBoxY() + ROW_HEIGHT * 2.5), false);
        }
    }

    private int getBoxX() {
        return width / 2 - HALF_ROW_WIDTH;
    }

    private int getBoxY() {
        return height / 2 - ROW_HEIGHT * ((Config.maxResultCount + 1) / 2);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        switch(keyCode) {
            case Keyboard.KEY_ESCAPE:
                if(search == null || search.equals("") || results.size() == 0) {
                    super.keyTyped(typedChar, keyCode);
                    return;
                } else {
                    search = "";
                }
                break;

            case Keyboard.KEY_UP:
                selected--;
                if(selected < 0) selected = results.size() - 1;
                return;

            case Keyboard.KEY_DOWN:
                selected++;
                if(selected + 1 > results.size()) selected = 0;
                return;

            case Keyboard.KEY_RETURN:
                if(results.size() > 0) {
                    results.get(selected).action.execute();
                    closeGui();
                }
                break;
        }

        selected = 0;

        if(keyCode == Keyboard.KEY_BACK) {
            if(Keyboard.isKeyDown(Keyboard.KEY_LMETA)) {
                search = "";
            } else {
                search = StringUtils.chop(search);
            }
        }

        if(ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
            search += typedChar;
        }

        if(search.equals(" ")) search = "";
        if(search.endsWith("  ")) search = search.substring(0, search.length() - 1);

        results = ResultList.getResults(search);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void closeGui() throws IOException {
        super.keyTyped(' ', Keyboard.KEY_ESCAPE);
    }

}
