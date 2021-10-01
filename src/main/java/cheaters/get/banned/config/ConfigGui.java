package cheaters.get.banned.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.components.ConfigInput;
import cheaters.get.banned.config.components.Scrollbar;
import cheaters.get.banned.config.settings.BooleanSetting;
import cheaters.get.banned.config.settings.Setting;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class ConfigGui extends GuiScreen {

    private int columnWidth = 300;
    public static ArrayList<Setting> settings = filterSettings();

    private int prevMouseY;
    private int scrollOffset = 0;
    private boolean scrolling = false;
    private ResourceLocation logo;
    private Scrollbar scrollbar;

    private Integer prevWidth = null;
    private Integer prevHeight = null;

    public ConfigGui() {
        this(new ResourceLocation("shadyaddons:logo.png"));
    }

    public ConfigGui(ResourceLocation logo) {
        this.logo = logo;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Dark Background
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        mouseMoved(mouseY);

        // Logo + Version
        GlStateManager.color(255, 255, 255);
        Shady.mc.getTextureManager().bindTexture(logo);
        drawModalRectWithCustomSizedTexture(width / 2 - 143, 24-scrollOffset, 0, 0, 286, 40, 286, 40);
        drawCenteredString(Shady.mc.fontRendererObj, (Shady.BETA ? "Beta ✦ " : "Stable ✦ ") + Shady.VERSION, width / 2, 67-scrollOffset, -1);

        // Settings
        for(int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);

            int x = getOffset();
            int y = (columnWidth / 3) + (i * 15) - scrollOffset;

            // Nested Setting
            if(setting.parent != null) {
                x += 10;
                Setting parentSetting = setting.parent.parent;
                if(parentSetting != null && parentSetting.parent != null) {
                    x += 10;
                }
            } else if(i > 0) {
                // Setting Border
                drawRect(x, y-3, getOffset() + columnWidth, y-2, ConfigInput.transparent.getRGB());
            }

            // Setting Text
            Shady.mc.fontRendererObj.drawString(((setting instanceof BooleanSetting && (boolean)setting.get()) ? "§a" : "§f") + setting.name, x, y+1, -1);
            if(setting.credit != null) {
                int settingNameWidth = Shady.mc.fontRendererObj.getStringWidth(setting.name+" ");
                GlStateManager.translate(0, 1.8, 0);
                RenderUtils.drawScaledString("§7"+setting.credit, 0.8f, x+settingNameWidth, y+1);
                GlStateManager.translate(0, -1.8, 0);
            }
        }

        if(prevHeight != null && prevWidth != null && (prevWidth != width || prevHeight != height)) {
            Shady.mc.displayGuiScreen(new ConfigGui(logo));
        }

        prevWidth = width;
        prevHeight = height;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        for(int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);

            int x = getOffset() + columnWidth;
            int y = (columnWidth / 3) + (i * 15) - scrollOffset;

            buttonList.add(ConfigInput.createButtonForSetting(setting, x, y));
        }

        int viewport = height - 100 - 10;
        int contentHeight = settings.size() * 15;
        int scrollbarX = getOffset() + columnWidth + 10;

        scrollbar = new Scrollbar(viewport, contentHeight, scrollOffset, scrollbarX, scrolling);
        buttonList.add(scrollbar);
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

            /*
            increase by a percentage of the scrollbar height depending on the percentage of the viewport that was scrolled
            TODO: This doesn't work since the scroll amount is like 1 or 2 pixels every time
            */

            // 1) percentage of viewport scrolled
            // 2) answer * height of scrollbar
            // 3) scrollOffset += answer + amount scrolled

            int dragAmount = mouseY-prevMouseY;
            int scrollbarCompensation = dragAmount/viewport*scrollbar.height;

            scrollOffset += dragAmount + scrollbarCompensation;
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

            if((boolean)setting.parent.get()) {
                newSettings.add(setting);
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
