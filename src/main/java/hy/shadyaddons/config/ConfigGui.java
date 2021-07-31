package hy.shadyaddons.config;

import hy.shadyaddons.ShadyAddons;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ConfigGui extends GuiScreen {

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        String titleString = (ShadyAddons.usingSkyBlockAddons ? "§z" : "§3") + "ShadyAddons §7" + ShadyAddons.VERSION;
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

        Config.Setting[] values = Config.Setting.values();
        for(int i = 0; i < values.length; i++) {
            Config.Setting setting = values[i];

            ScaledResolution scaledResolution = new ScaledResolution(mc);

            int buttonsPerColumn = (int) ((scaledResolution.getScaledHeight()-55)/25f);
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
