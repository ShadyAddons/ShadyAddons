package cheaters.get.banned.verify;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.components.ConfigInput;
import cheaters.get.banned.utils.FontUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class DangerousModGui extends GuiScreen {

    @Override
    public void initGui() {
        buttonList.add(new ClearButton(0, width/2-100, height/2-10+49, "Download Safe Version"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, ConfigInput.red.getRGB());
        FontUtils.drawScaledCenteredString("ShadyAddons has been modified!", 2, width/2, height/2-49, true);
        String message = FontUtils.enforceWidth("Your copy of ShadyAddons may have been tampered with. Click the button below to download the latest authentic version. Change your Discord and Minecraft passwords as a safeguard. If you believe this is a mistake, press escape to close this window.", 250);
        FontUtils.drawCenteredString(message, width/2, height/2);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.id == 0) {
            Utils.openUrl("https://cheatersgetbanned.me/latest");
            File file = Loader.instance().getIndexedModList().get(Shady.MODID).getSource();
            if(!file.isDirectory()) {
                file.delete();
                System.out.println("Deleted "+file.getAbsolutePath());
            }
            Shady.mc.shutdown();
        }
    }

}
