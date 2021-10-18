package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class AutoReadyUp {

    private static boolean readyUp = false;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Config.autoReadyUp && Utils.inDungeon) {
            if(!readyUp) {
                for(Entity entity : Shady.mc.theWorld.getLoadedEntityList()) {
                    if(entity instanceof EntityArmorStand) {
                        if(entity.hasCustomName() && entity.getCustomNameTag().equals("§bMort")) {
                            List<Entity> possibleEntities = entity.getEntityWorld().getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(0, 3, 0), e -> e instanceof EntityPlayer);
                            if(!possibleEntities.isEmpty()) {
                                Shady.mc.playerController.interactWithEntitySendPacket(Shady.mc.thePlayer, possibleEntities.get(0));
                                readyUp = true;
                            }
                        }
                    }
                }
            }

            String chestName = Utils.getInventoryName();
            if(readyUp && chestName != null) {
                if(chestName.equals("Start Dungeon?")) {
                    Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, 13, 2, 0, Shady.mc.thePlayer);
                    return;
                }

                if(chestName.startsWith("Catacombs - ")) {
                    for(Slot slot : Shady.mc.thePlayer.openContainer.inventorySlots) {
                        if(slot.getStack() != null && slot.getStack().getDisplayName().contains(Shady.mc.thePlayer.getName())) {
                            Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, slot.slotNumber, 2, 0, Shady.mc.thePlayer);
                            Shady.mc.thePlayer.closeScreen();
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onJoinWorld(WorldEvent.Load event) {
        readyUp = false;
    }

}
