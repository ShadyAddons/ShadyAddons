package cheaters.get.banned.mixins;

import cheaters.get.banned.Shady;
import cheaters.get.banned.remote.Updater;
import cheaters.get.banned.utils.FontUtils;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu {

    @Shadow private String splashText;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void initMainMenu(CallbackInfo callbackInfo) {
        if(Updater.update != null && !Updater.update.version.equals(Shady.VERSION)) {
            splashText = "Update to §" + FontUtils.getRainbowCode('e') + Updater.update.version + "§e!";
        }
    }

}
