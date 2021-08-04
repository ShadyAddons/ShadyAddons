package shady.shady.shady.features;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.KeybindUtils;
import shady.shady.shady.utils.Utils;

public class GhostBlockKeybind {

    public GhostBlockKeybind() {
        KeybindUtils.register("Create Ghost Block", Keyboard.KEY_G);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(Config.isEnabled(Setting.GHOST_BLOCK_KEYBIND) && KeybindUtils.get("Create Ghost Block").isKeyDown()) {
            BlockPos lookingAtPos = Shady.mc.thePlayer.rayTrace(Shady.mc.playerController.getBlockReachDistance(), 1).getBlockPos();
            if(lookingAtPos != null) {
                Block lookingAtblock = Shady.mc.theWorld.getBlockState(lookingAtPos).getBlock();
                if(!Utils.isInteractable(lookingAtblock)) {
                    Shady.mc.theWorld.setBlockToAir(lookingAtPos);
                }
            }
        }
    }

}
