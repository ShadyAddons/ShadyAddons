package cheaters.get.banned.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Mutable @Shadow @Final
    private static ResourceLocation locationMojangPng;

    @Inject(method = "drawSplashScreen", at = @At("HEAD"))
    public void modifyMojangLogo(TextureManager textureManagerInstance, CallbackInfo callbackInfo) {
        locationMojangPng = new ResourceLocation("shadyaddons:splash.png");
    }

}
