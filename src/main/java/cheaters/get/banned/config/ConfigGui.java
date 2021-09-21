package cheaters.get.banned.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.components.ConfigButton;
import cheaters.get.banned.config.components.Scrollbar;
import cheaters.get.banned.config.components.SwitchButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Calendar;

public class ConfigGui extends GuiScreen {

    private int columnWidth = 300;
    public static ArrayList<Setting> settings = filterSettings();

    private int prevMouseY;
    private int scrollOffset = 0;
    private boolean scrolling = false;

    private Integer prevWidth = null;
    private Integer prevHeight = null;

    private final boolean usePrideLogo = Calendar.getInstance().get(Calendar.MONTH) == Calendar.OCTOBER;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Dark Background
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        mouseMoved(mouseY);

        // Logo + Version
        GlStateManager.color(255, 255, 255);
        Shady.mc.getTextureManager().bindTexture(new ResourceLocation(usePrideLogo ? "shadyaddons:pride-logo.png" : "shadyaddons:logo.png"));
        drawModalRectWithCustomSizedTexture(width / 2 - 143, 24-scrollOffset, 0, 0, 286, 40, 286, 40);
        drawCenteredString(Shady.mc.fontRendererObj, (Shady.PRIVATE ? "Insider ✦ " : "") + Shady.VERSION, width / 2, 67-scrollOffset, -1);

        // Settings
        for(int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);

            int x = getOffset();
            int y = (columnWidth / 3) + (i * 15) - scrollOffset;

            // Nested Setting
            if(setting.parent != null) {
                x += 10;
                Setting parentSetting = ConfigLogic.getSetting(setting.parent);
                if(parentSetting != null && parentSetting.parent != null) {
                    x += 10;
                }
            } else if(i > 0) {
                // Setting Border
                drawRect(x, y-3, getOffset() + columnWidth, y-2, ConfigButton.transparent.getRGB());
            }

            // Setting Text
            Shady.mc.fontRendererObj.drawString((setting.enabled() ? "§a" : "§f") + setting.name, x, y+1, -1);
        }

        if(prevHeight != null && prevWidth != null && (prevWidth != width || prevHeight != height)) {
            Shady.mc.displayGuiScreen(new ConfigGui());
        }

        prevWidth = width;
        prevHeight = height;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        for(int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);

            int x = getOffset() + columnWidth - 25;
            int y = (columnWidth / 3) + (i * 15) - scrollOffset;

            if(setting.type == Setting.Type.SWITCH) {
                buttonList.add(new SwitchButton(setting, x, y));
            }
        }

        int viewport = height - 100 - 10;
        int contentHeight = settings.size() * 15;
        int scrollbarX = getOffset() + columnWidth + 10;
        buttonList.add(new Scrollbar(viewport, contentHeight, scrollOffset, scrollbarX, scrolling));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button instanceof Scrollbar) {
            scrolling = true;
        } else {
            settings.clear();
            settings = filterSettings();
        }
        initGui();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        scrolling = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    private void mouseMoved(int mouseY) {
        if(scrolling) {
            int viewport = height - 100 - 10;
            int contentHeight = settings.size() * 15;

            scrollOffset += mouseY-prevMouseY;
            scrollOffset = MathHelper.clamp_int(scrollOffset, 0, contentHeight-viewport);
            initGui();
        }
        prevMouseY = mouseY;
    }

    private static ArrayList<Setting> filterSettings() {
        ArrayList<Setting> newSettings = new ArrayList<>();

        for(Setting setting : Shady.settings) {
            if(setting.parent == null) {
                newSettings.add(setting);
                continue;
            }

            for(Setting subSetting : Shady.settings) {
                if(subSetting.name.equals(setting.parent) && subSetting.enabled()) {
                    newSettings.add(setting);
                }
            }
        }

        return newSettings;
    }

    @Override
    public void onGuiClosed() {
        ConfigLogic.save();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private int getOffset() {
        return (width - columnWidth) / 2;
    }

}
