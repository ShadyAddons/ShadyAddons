package cheaters.get.banned.mixins;

import cheaters.get.banned.Shady;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(value = FMLHandshakeMessage.ModList.class, remap = false)
public abstract class MixinModList {

    @Shadow private Map<String, String> modTags;

    @Inject(method = "<init>(Ljava/util/List;)V", at = @At("RETURN"), remap = false)
    private void hideModId(List<ModContainer> modList, CallbackInfo ci) {
        if(!Shady.mc.isIntegratedServerRunning()) {
            modTags.remove(Shady.MOD_ID);
        }
    }

}
