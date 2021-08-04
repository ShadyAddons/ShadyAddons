package shady.shady.shady.features;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;

public class InventoryWalk {

    private static final KeyBinding[] keys = {
            Shady.mc.gameSettings.keyBindForward,
            Shady.mc.gameSettings.keyBindBack,
            Shady.mc.gameSettings.keyBindLeft,
            Shady.mc.gameSettings.keyBindRight,
            Shady.mc.gameSettings.keyBindJump,
            Shady.mc.gameSettings.keyBindSprint
    };

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if(event.entityLiving == Shady.mc.thePlayer && Config.isEnabled(Setting.INVENTORY_WALK)) {
            if(allowMovement(Shady.mc.currentScreen)) {
                for(KeyBinding key : keys) {
                    KeyBinding.setKeyBindState(key.getKeyCode(), GameSettings.isKeyDown(key));
                }
            }
        }
    }

    private static boolean allowMovement(GuiScreen screen) {
        if(screen == null) return false;
        return screen instanceof GuiChest || screen instanceof GuiInventory;
    }

}
