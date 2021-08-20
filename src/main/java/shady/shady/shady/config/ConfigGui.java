package shady.shady.shady.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import shady.shady.shady.Shady;

public class ConfigGui extends GuiScreen {

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        String titleString = (Shady.usingSkyBlockAddons && (!Shady.usingPatcher || Shady.usingSkytils) ? "§z" : "§c") + "ShadyAddons §7" + Shady.VERSION;
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.5, 1.5, 1.5);
        mc.fontRendererObj.drawString(titleString, 13.333f, 13.333f, -1, true);
        GlStateManager.popMatrix();
    }

    @Override
    public void initGui() {
        super.initGui();
        addButtons();
    }

    private void addButtons() {
        this.buttonList.clear();

        Setting[] values = Setting.values();
        for(int i = 0; i < values.length; i++) {
            Setting setting = values[i];

            int buttonsPerColumn = (int) ((height-55)/25f);
            if(buttonsPerColumn < 1) buttonsPerColumn = 1;
            int currentColumn = (int) Math.floor(i/buttonsPerColumn);

            int yPosition = 40+25*(i%buttonsPerColumn);
            int xPosition = 20+currentColumn*205;

            ConfigButton button = new ConfigButton(setting, xPosition, yPosition);
            this.buttonList.add(button);
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(button instanceof ConfigButton) {
            Config.toggleEnabled(((ConfigButton) button).setting);
            addButtons();
        }
    }

    @Override
    public void onGuiClosed() {
        Config.save();
    }

}