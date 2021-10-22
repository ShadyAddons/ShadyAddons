package cheaters.get.banned.remote;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.verify.ClearButton;
import cheaters.get.banned.utils.FontUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class UpdateGui extends GuiScreen {

    @Override
    public void initGui() {
        buttonList.add(new ClearButton(1, this.width / 2 - 105, this.height / 2+10, 100, 20, "Dismiss"));
        buttonList.add(new ClearButton(0, this.width / 2 + 5, this.height / 2+10, 100, 20, "Download"));
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(button.id == 0) {
            Utils.openUrl("https://cheatersgetbanned.me/latest/");
        }
        Shady.guiToOpen = new GuiMainMenu();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();

        RenderUtils.drawTexture(new ResourceLocation("shadyaddons:background.jpg"), 0, 0, width, height);

        String title = "ShadyAddons " + FontUtils.getRainbowCode('c') + Updater.update.version + "§f is available!";
        FontUtils.drawScaledCenteredString(title, 1.5f, width/2, height/2-15-5, true);

        if(Updater.update.description == null) Updater.update.description = "§7§oNo update description";
        int descriptionWidth = mc.fontRendererObj.getStringWidth(Updater.update.description);
        mc.fontRendererObj.drawStringWithShadow(
                Updater.update.description,
                (this.width - descriptionWidth) / 2f,
                this.height / 2f - 7,
                Color.WHITE.getRGB()
        );

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
