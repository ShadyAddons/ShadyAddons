package shady.shady.shady.updates;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import shady.shady.shady.Shady;
import shady.shady.shady.utils.Utils;

import java.awt.*;

public class UpdateGui extends GuiScreen {

    @Override
    public void initGui() {
        buttonList.add(new GuiButton(1, this.width / 2 - 105, this.height / 2, 100, 20, "Ignore"));
        buttonList.add(new GuiButton(0, this.width / 2 + 5, this.height / 2, 100, 20, "Download"));
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(button.id == 0) {
            Utils.openUrl(Updater.update.download);
        }
        Shady.guiToOpen = new GuiMainMenu();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        String title = "ShadyAddons " + (Shady.usingSkyBlockAddons && (!Shady.usingPatcher || Shady.usingSkytils) ? "§z" : "§c") + Updater.update.version + "§f is available!";
        int titleWidth = mc.fontRendererObj.getStringWidth(title);
        mc.fontRendererObj.drawStringWithShadow(
                title,
                (this.width - titleWidth) / 2f,
                this.height / 2f - 20,
                Color.WHITE.getRGB()
        );
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
