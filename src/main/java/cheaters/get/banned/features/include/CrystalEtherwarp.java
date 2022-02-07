package cheaters.get.banned.features.include;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.*;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CrystalEtherwarp {

    private static boolean teleported = false;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Config.crystalEtherwarp && !teleported && Utils.inDungeon && DungeonUtils.inFloor(DungeonUtils.Floor.FLOOR_7)) {
            if(Shady.mc.thePlayer.posX == 272.5 && Shady.mc.thePlayer.posZ == 213.5) {
                RotationUtils.Rotation rotation = RotationUtils.getRotationToBlock(new BlockPos(
                        Config.crystalSide == 0 ? 280 : 264, // Left (x = 280) & Right (x = 264)
                        237,
                        248
                ));

                RotationUtils.look(rotation);

                Shady.mc.thePlayer.movementInput.sneak = true;
                NetworkUtils.sendPacket(new C0BPacketEntityAction(Shady.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));

                KeybindUtils.rightClick();

                Shady.mc.thePlayer.movementInput.sneak = false;
                NetworkUtils.sendPacket(new C0BPacketEntityAction(Shady.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));

                teleported = true;
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        teleported = false;
    }

}
