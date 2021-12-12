package cheaters.get.banned.features;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;

public class MithrilMacro {
    private static int currentDamage;
    private static byte blockHitDelay = 0;
    private static BlockPos blockPos;

    private static boolean working = false;

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (!Config.mithrilMacro) {
            currentDamage = 0;
            return;
        }
        if (event.phase == TickEvent.Phase.END) {
            if(blockPos != null) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.bedrock || blockState.getBlock() == Blocks.air) {
                    currentDamage = 0;
                }
            }
            if(currentDamage == 0) {
                blockPos = closestMithril();
            }
            if (blockPos != null) {
                if (blockHitDelay > 0) {
                    blockHitDelay--;
                    return;
                }
                MovingObjectPosition fake = Minecraft.getMinecraft().objectMouseOver;
                fake.hitVec = new Vec3(blockPos);
                EnumFacing enumFacing = fake.sideHit;
                if (currentDamage == 0 && enumFacing != null) {
                    Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
                    if(!Config.dontLookAtMithrilBlock) {
                        facePos(new Vec3(blockPos.getX() + 0.5, blockPos.getY() - 1, blockPos.getZ() + 0.5));
                    }
                }
                swingItem();

                currentDamage += 1; //maybe will be used in the future
            }
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if (!Config.mithrilMacro) return;
        if (blockPos != null) {
            IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
            if(blockState.getBlock() == Blocks.stone) {
                RenderUtils.highlightBlock(blockPos, Color.WHITE, event.partialTicks);
            } else {
                RenderUtils.highlightBlock(blockPos, Color.BLUE, event.partialTicks);
            }
        }
    }

    private BlockPos closestMithril() {
        int r = 6;
        if (Minecraft.getMinecraft().thePlayer == null) return null;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos = playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        ArrayList<Vec3> blocks = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                if (isMithril(blockState)) {
                    blocks.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (int i = 0; i < blocks.size(); i++) {
            double dist = blocks.get(i).distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = blocks.get(i);
            }
        }
        if (closest != null && smallest < 5) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }

    //THIS NEEDS TO BE A UTIL
    private void facePos(Vec3 vector) {
        if(Minecraft.getMinecraft().currentScreen != null) {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiIngameMenu || Minecraft.getMinecraft().currentScreen instanceof GuiChat) {}
            else {
                return;
            }
        }
        if(working) return;
        new Thread(() -> {
            try {
                working = true;
                double diffX = vector.xCoord - (Minecraft.getMinecraft()).thePlayer.posX;
                double diffY = vector.yCoord - (Minecraft.getMinecraft()).thePlayer.posY;
                double diffZ = vector.zCoord - (Minecraft.getMinecraft()).thePlayer.posZ;
                double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

                float pitch = (float) -Math.atan2(dist, diffY);
                float yaw = (float) Math.atan2(diffZ, diffX);
                pitch = (float) wrapAngleTo180((pitch * 180F / Math.PI + 90)*-1 - Minecraft.getMinecraft().thePlayer.rotationPitch);
                yaw = (float) wrapAngleTo180((yaw * 180 / Math.PI) - 90 - Minecraft.getMinecraft().thePlayer.rotationYaw);

                for(int i = 0; i < Config.mithrilHeadMovement; i++) {
                    Minecraft.getMinecraft().thePlayer.rotationYaw += yaw/Config.mithrilHeadMovement;
                    Minecraft.getMinecraft().thePlayer.rotationPitch += pitch/Config.mithrilHeadMovement;
                    Thread.sleep(1);
                }
                working = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //MORE SHITTY RGA CODE
    private static double wrapAngleTo180(double angle) {
        angle %= 360;
        while (angle >= 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    private void swingItem() {
        MovingObjectPosition movingObjectPosition = Minecraft.getMinecraft().objectMouseOver;
        if (movingObjectPosition != null && movingObjectPosition.entityHit == null) {
            Minecraft.getMinecraft().thePlayer.swingItem();
        }
    }

    private boolean isMithril(IBlockState blockState) {
        return blockState.getBlock() == Blocks.prismarine || blockState.getBlock() == Blocks.wool || blockState.getBlock() == Blocks.stained_hardened_clay || (blockState.getBlock() == Blocks.stone && blockState.getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE_SMOOTH) || blockState.getBlock() == Blocks.gold_block;
    }
}
