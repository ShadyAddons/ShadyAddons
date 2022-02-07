package cheaters.get.banned.mixins;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.remote.CrashReporter;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(value = CrashReport.class, priority = Integer.MIN_VALUE)
public abstract class MixinCrashReport {

    @Shadow public abstract String getCauseStackTraceOrString();

    @Inject(method = "saveToFile", at = @At("RETURN"))
    public void onCrash(File file, CallbackInfoReturnable<Boolean> cir) {
        String reason = getCauseStackTraceOrString().split("\n")[0];
        if(Config.sendCrashReports && file.exists()) CrashReporter.send(file, reason);
    }

}