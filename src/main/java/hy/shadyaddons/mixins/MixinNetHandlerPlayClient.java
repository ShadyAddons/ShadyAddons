package hy.shadyaddons.mixins;

import hy.shadyaddons.events.PacketEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient implements INetHandlerPlayClient {

    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        try {
            if(MinecraftForge.EVENT_BUS.post(new PacketEvent.SendEvent(packet))) callbackInfo.cancel();
        } catch(Exception ignored) {}
    }

}
