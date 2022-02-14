package cheaters.get.banned.mixins;

import cheaters.get.banned.events.SendChatMessageEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, boolean addToChat, CallbackInfo ci) {
        try {
            SendChatMessageEvent event = new SendChatMessageEvent(message, addToChat);
            if(MinecraftForge.EVENT_BUS.post(event)) {
                ci.cancel();
            }
        } catch (Exception ignored) {}

    }

}
