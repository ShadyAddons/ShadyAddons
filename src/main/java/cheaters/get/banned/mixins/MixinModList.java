package cheaters.get.banned.mixins;

import cheaters.get.banned.Shady;
import net.minecraftforge.fml.common.ModContainer;

import java.util.List;

// @Mixin(FMLHandshakeMessage.ModList.class)
public abstract class MixinModList {

    // @ModifyArg(method = "<init>(Ljava/util/List;)V", at = @At("INVOKE"))
    private static List<ModContainer> modifyModList(List<ModContainer> modList) {
        System.out.println("Before: "+modList);
        modList.removeIf(mod -> mod.getName().equals(Shady.MODNAME));
        System.out.println("After: "+modList);
        return modList;
    }

}
