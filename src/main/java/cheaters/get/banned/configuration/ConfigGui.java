package cheaters.get.banned.configuration;

import cheaters.get.banned.Shady;
import cheaters.get.banned.configuration.components.SwitchButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ConfigGui extends GuiScreen {

    private int columnWidth = 300;
    private ArrayList<Setting> settings = filterSettings();

    private final boolean usePrideLogo = Calendar.getInstance().get(Calendar.MONTH) == Calendar.OCTOBER;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        // y = 24+40+3+9+24
        GlStateManager.color(255, 255, 255);
        Shady.mc.getTextureManager().bindTexture(new ResourceLocation(usePrideLogo ? "shadyaddons:pride-logo.png" : "shadyaddons:logo.png"));
        drawModalRectWithCustomSizedTexture(width / 2 - 143, 24, 0, 0, 286, 40, 286, 40);
        drawCenteredString(Shady.mc.fontRendererObj, (Shady.PRIVATE ? "Insider ✦ " : "") + Shady.VERSION, width / 2, 67, -1);

        for(int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);

            int x = getOffset();
            int y = (columnWidth / 3) + (i * 15);

            if(setting.parent != null) {
                x += 10;
                Setting parentSetting = ConfigLogic.getSetting(setting.parent);
                if(parentSetting != null && parentSetting.parent != null) {
                    x += 10;
                }
            }

            Shady.mc.fontRendererObj.drawString((setting.enabled() ? "§a" : "§f") + setting.name, x, y, -1);
        }
    }

    @Override
    public void initGui() {
        buttonList.clear();
        for(int i = 0; i < settings.size(); i++) {
            Setting setting = settings.get(i);

            int x = getOffset() + columnWidth - 25;
            int y = (columnWidth / 3) + (i * 15);

            if(setting.parent != null) {
                x -= 10;
                Setting parentSetting = ConfigLogic.getSetting(setting.parent);
                if(parentSetting != null && parentSetting.parent != null) {
                    x -= 10;
                }
            }

            if(setting.type == Setting.Type.SWITCH) {
                buttonList.add(new SwitchButton(setting, x, y));
            }
        }
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        settings.clear();
        settings = filterSettings();
        initGui();
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
