package cheaters.get.banned.mixins;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.CrystalReach;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(ModelEnderCrystal.class)
public abstract class MixinModelEnderCrystal {

    @Inject(method = "render", at = @At("HEAD"))
    public void preRender(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale, CallbackInfo ci) {
        if(CrystalReach.isEnabled()) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();

            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderUtils.bindColor(entityIn.equals(CrystalReach.crystal) && Shady.mc.thePlayer.isSneaking() ? Color.MAGENTA : Color.WHITE);
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void postRender(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale, CallbackInfo ci) {
        if(CrystalReach.isEnabled()) {
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

}
