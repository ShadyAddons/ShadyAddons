package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.KeybindUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Nuker {

    public Nuker() {
        KeybindUtils.register("Nuker", Keyboard.KEY_NONE);
    }

    ArrayList<BlockPos> toBreak = new ArrayList<>();

    BlockPos bp = null;

    private final Map<Integer, Boolean> glCapMap = new HashMap<>();

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (KeybindUtils.get("Nuker").isPressed()) {
            Config.nuker = !Config.nuker;
        }
        if (Config.nuker && Shady.mc.thePlayer != null && Shady.mc.theWorld != null && toBreak.size() != 0) {
            for (int i = 0; i <= Config.nukerBPS; i++) {
                if (toBreak.size() != 0) {
                    BlockPos blockPos = toBreak.get(0);
                    BlockPos playerPos = Shady.mc.thePlayer.getPosition();
                    Vec3i vec3i = new Vec3i(3, 1, 3);
                    ArrayList<BlockPos> blocks = new ArrayList<>();
                    for (BlockPos bp : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i))) {
                        blocks.add(bp);
                    }

                    if (!blocks.contains(new BlockPos(blockPos.getX() - 0.5, blockPos.getY(), blockPos.getZ() - 0.5)) || !blocks.contains(new BlockPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5))) {
                        toBreak.remove(blockPos);
                    }
                    if (toBreak.size() != 0) {
                        bp = new BlockPos(toBreak.get(0));
                        Shady.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(toBreak.get(0)), EnumFacing.DOWN));
                        toBreak.remove(0);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (Config.nuker && Shady.mc.thePlayer != null && Shady.mc.theWorld != null && bp != null && toBreak.size() != 0) {
            try {
                BlockPos blockPos = bp;
                Color color = new Color(255, 255, 255);
                int width = 5;
                float partialTicks = event.partialTicks;

                RenderManager renderManager = Shady.mc.getRenderManager();
                double x = (double) blockPos.getX() - renderManager.viewerPosX;
                double y = (double) blockPos.getY() - renderManager.viewerPosY;
                double z = (double) blockPos.getZ() - renderManager.viewerPosZ;
                AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
                Block block = Shady.mc.theWorld.getBlockState(blockPos).getBlock();
                if (block != null) {
                    EntityPlayerSP player = Shady.mc.thePlayer;
                    double posX = player.lastTickPosX + ((player).posX - player.lastTickPosX) * (double) partialTicks;
                    double posY = player.lastTickPosY + ((player).posY - player.lastTickPosY) * (double) partialTicks;
                    double posZ = player.lastTickPosZ + ((player).posZ - player.lastTickPosZ) * (double) partialTicks;
                    block.setBlockBoundsBasedOnState(Shady.mc.theWorld, blockPos);
                    axisAlignedBB = block.getCollisionBoundingBox(Shady.mc.theWorld, blockPos, Shady.mc.theWorld.getBlockState(blockPos)).expand(0.002f, 0.002f, 0.002f).offset(-posX, -posY, -posZ);
                }
                GL11.glBlendFunc(770, 771);

                glCapMap.put(3042, GL11.glGetBoolean(3042));
                GL11.glEnable(3042);

                glCapMap.put(3553, GL11.glGetBoolean(3553));
                GL11.glDisable(3553);
                glCapMap.put(2929, GL11.glGetBoolean(2929));
                GL11.glDisable(2929);

                GL11.glDepthMask(false);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() != 255 ? color.getAlpha() / 255.0f : 26 / 255.0f);
                GL11.glLineWidth(width);
                glCapMap.put(2848, GL11.glGetBoolean(2848));
                GL11.glEnable(2848);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() != 255 ? color.getAlpha() : 26);

                AxisAlignedBB boundingBox = axisAlignedBB;
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                worldrenderer.begin(3, DefaultVertexFormats.POSITION);
                worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
                worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
                worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
                worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
                worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
                worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
                worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
                worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
                tessellator.draw();

                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDepthMask(true);
                glCapMap.forEach(Nuker::setGlState);
            } catch (NullPointerException ignored) {}
        }
    }

    public static void setGlState(int cap, boolean state) {
        if (state) {
            GL11.glEnable(cap);
        } else {
            GL11.glDisable(cap);
        }
    }

    @SubscribeEvent
    public void pickBlocks(RenderWorldLastEvent event) {
        if (Config.nuker && Shady.mc.thePlayer != null && Shady.mc.theWorld != null) {
            BlockPos playerPos = Shady.mc.thePlayer.getPosition();
            Vec3i vec3i = new Vec3i(3, 1, 3);
            ArrayList<Vec3> blocks = new ArrayList<>();
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i))) {
                IBlockState blockState = Shady.mc.theWorld.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.air) {
                    continue;
                }
                blocks.add(new Vec3((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5));
            }

            for (Vec3 block : blocks) {
                Block b = Shady.mc.theWorld.getBlockState(new BlockPos(block)).getBlock();
                if (Config.nukerBlock == 0) {
                    if (b == Blocks.mycelium && !toBreak.contains(new BlockPos(block))) {
                        toBreak.add(new BlockPos(block));
                    }
                } if (Config.nukerBlock == 1) {
                    if (b == Blocks.sand && !toBreak.contains(new BlockPos(block)) && Shady.mc.theWorld.getBlockState(new BlockPos(block)).getBlock().getMetaFromState(Shady.mc.theWorld.getBlockState(new BlockPos(block))) == 1) {
                        toBreak.add(new BlockPos(block));
                    }
                }
            }
        }
    }
}
