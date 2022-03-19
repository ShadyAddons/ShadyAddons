package cheaters.get.banned.gui.config.components;

import cheaters.get.banned.gui.config.settings.SpacerSetting;
import net.minecraft.client.Minecraft;

public class SpacerComponent extends ConfigInput {

    public SpacerComponent(SpacerSetting setting, int x, int y) {
        super(setting, x, y);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    }

}
