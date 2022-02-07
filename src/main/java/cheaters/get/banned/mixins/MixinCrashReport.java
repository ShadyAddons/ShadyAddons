package cheaters.get.banned.mixins;

import cheaters.get.banned.remote.CrashReporter;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(value = CrashReport.class, priority = Integer.MAX_VALUE)
public abstract class MixinCrashReport {

    @Inject(method = "saveToFile", at = @At("RETURN"))
    public void onCrash(File file, CallbackInfoReturnable<Boolean> cir) {
        if(file.exists()) CrashReporter.douse(file);
    }

}