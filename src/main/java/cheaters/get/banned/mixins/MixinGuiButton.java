package cheaters.get.banned.mixins;

import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiButton.class)
public abstract class MixinGuiButton {

    @Shadow public int id;
    @Shadow public int xPosition;
    @Shadow public int yPosition;
    @Shadow public String displayString;
    @Shadow public int width;
    @Shadow public int height;
    @Shadow public boolean visible;
    @Shadow protected boolean hovered;

    @Inject(method = "drawButton", at = @At("HEAD"), cancellable = true)
    public void drawCleanButton(Minecraft mc, int mouseX, int mouseY, CallbackInfo callbackInfo) {
        if(Config.useCleanButtons) {
            if(visible) {
                hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition+width && mouseY < yPosition+height;
                Color color = hovered ? new Color(30, 30, 30, 64) : new Color(0, 0, 0, 64);
                Gui.drawRect(xPosition, yPosition, xPosition+width, yPosition+height, color.getRGB());
                FontUtils.drawCenteredString(displayString, xPosition+width/2, yPosition+height/2);
            }
            callbackInfo.cancel();
        }
    }

}
