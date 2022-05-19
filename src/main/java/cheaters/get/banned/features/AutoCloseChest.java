package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoCloseChest {

    @SubscribeEvent
    public void onGuiBackgroundRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(event.gui instanceof GuiChest && Utils.inSkyBlock) {
            if(Utils.inDungeon && Config.closeSecretChests && Utils.getGuiName(event.gui).equals("Chest")) {
                Shady.mc.thePlayer.closeScreen();
                MiscStats.add(MiscStats.Metric.CHESTS_CLOSED);
            }
        }
    }
}
