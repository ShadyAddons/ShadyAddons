package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;

public class MithrilMacro {
    private static int currentDamage;
    private static BlockPos blockToMine;
    private static boolean isMining = false;

    private static boolean isEnabled() {
        return Config.macroMode != 0 &&
                Shady.mc.thePlayer != null /*&&
                Utils.inSkyBlock &&
                LocationUtils.onIsland(LocationUtils.Island.DWARVEN_MINES)*/;
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(!isEnabled()) {
            currentDamage = 0;
            return;
        }

        if(blockToMine == null) {
            blockToMine = closestMithril();
        }

        IBlockState blockState = Shady.mc.theWorld.getBlockState(blockToMine);
        if(blockState.getBlock() == Blocks.bedrock || blockState.getBlock() == Blocks.air) {
            currentDamage = 0;
        }

        if(!isMining && blockToMine != null) {
            /*MovingObjectPosition fake = Shady.mc.objectMouseOver;
            fake.hitVec = new Vec3(blockToMine);
            EnumFacing enumFacing = fake.sideHit;*/

            EnumFacing facing = EnumFacing.getFacingFromVector(blockToMine.getX(), blockToMine.getY(), blockToMine.getZ());

            if(currentDamage == 0 && facing != null) {
                isMining = true;
                RotationUtils.smartLook(
                        RotationUtils.getRotationToBlock(blockToMine),
                        Config.headMovementTime/50,
                        () -> {
                            NetworkUtils.sendPacket(new C07PacketPlayerDigging(
                                    C07PacketPlayerDigging.Action.START_DESTROY_BLOCK,
                                    blockToMine,
                                    facing
                            ));
                            isMining = false;
                        }
                );
            }

            if(!isMining) swingItem();

            currentDamage++;
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if(isEnabled() && blockToMine != null) {
            IBlockState blockState = Shady.mc.theWorld.getBlockState(blockToMine);
            if(blockState.getBlock() == Blocks.stone) {
                RenderUtils.outlineBlock(blockToMine, Color.WHITE, event.partialTicks);
            } else {
                RenderUtils.outlineBlock(blockToMine, Color.BLUE, event.partialTicks);
            }
        }
    }

    private static BlockPos closestMithril() {
        float radius = Shady.mc.playerController.getBlockReachDistance();
        BlockPos playerPos = Shady.mc.thePlayer.getPosition().up(1);
        Vec3 playerVec = Shady.mc.thePlayer.getPositionVector();
        Vec3i radiusVector = new Vec3i(radius, radius, radius);

        ArrayList<Vec3> blocks = new ArrayList<>();

        for(BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(radiusVector), playerPos.subtract(radiusVector))) {
            IBlockState blockState = Shady.mc.theWorld.getBlockState(blockPos);
            if(shouldMine(blockState)) {
                blocks.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
            }
        }

        double smallest = 9999;
        Vec3 closest = null;

        for(Vec3 block : blocks) {
            double dist = block.distanceTo(playerVec);
            if(dist < smallest) {
                smallest = dist;
                closest = block;
            }
        }

        if(closest != null && smallest < 5) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }

        return null;
    }

    private static void swingItem() {
        if(Shady.mc.objectMouseOver != null && Shady.mc.objectMouseOver.entityHit == null) {
            Shady.mc.thePlayer.swingItem();
        }
    }

    private static boolean shouldMine(IBlockState blockState) {
        Block block = blockState.getBlock();
        if(Config.macroMode == 1) {
            return block == Blocks.prismarine ||
                    block == Blocks.wool ||
                    block == Blocks.stained_hardened_clay ||
                    (block == Blocks.stone && blockState.getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE_SMOOTH);
        } else if(Config.macroMode == 0) {
            return block == Blocks.gold_block;
        }
        return false;
    }

}
