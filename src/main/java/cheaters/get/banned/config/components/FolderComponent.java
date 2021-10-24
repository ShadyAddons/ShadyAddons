package cheaters.get.banned.config.components;

import cheaters.get.banned.config.settings.FolderSetting;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class FolderComponent extends ConfigInput {

    public FolderSetting setting;

    public FolderComponent(FolderSetting setting, int x, int y) {
        super(setting, x, y);
        this.setting = setting;
        super.width = 300;
        super.height = 9;
        super.xPosition -= width;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        RenderUtils.drawRotatedTexture(
                new ResourceLocation("shadyaddons:chevron.png"),
                xPosition + width - height,
                yPosition,
                height,
                height,
                setting.get(Boolean.class) ? 180 : 0
        );
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(hovered) {
            setting.set(!setting.get(Boolean.class));
            return true;
        }
        return false;
    }

}
