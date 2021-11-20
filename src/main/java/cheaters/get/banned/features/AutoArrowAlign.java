package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.KeybindUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AutoArrowAlign {

    private static final ArrayList<Entity> itemFrames = new ArrayList<>();
    private static final Map<BlockPos, Integer> clicksPerFrame = new HashMap<>();
    private Thread thread;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (!Config.autoArrowAlign) return;
        if(!isInSection3(Shady.mc.thePlayer.getPosition())) return;
        if(thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                try {
                    initFrame();
                    MovingObjectPosition objectMouseOver = Shady.mc.objectMouseOver;
                    if (objectMouseOver != null && objectMouseOver.entityHit != null) {
                        Entity entity = objectMouseOver.entityHit;
                        if (entity instanceof EntityItemFrame) {
                            ItemStack itemStack = ((EntityItemFrame) entity).getDisplayedItem();
                            if (itemStack != null) {
                                String itemString = itemStack.toString();
                                if (itemString.contains("arrow@0")) {
                                    int endRotationAmount = 0;
                                    if(clicksPerFrame.containsKey(new BlockPos(196, entity.getPosition().getY(), entity.getPosition().getZ()))) {
                                        endRotationAmount = clicksPerFrame.get(new BlockPos(196, entity.getPosition().getY(), entity.getPosition().getZ()));
                                    }
                                    int currRotationAmount = ((EntityItemFrame) entity).getRotation();
                                    int toClick = 0;
                                    if (currRotationAmount < endRotationAmount) {
                                        toClick = endRotationAmount - currRotationAmount;
                                    } else if (currRotationAmount > endRotationAmount) {
                                        currRotationAmount = currRotationAmount - 8;
                                        toClick = endRotationAmount - currRotationAmount;
                                    }
                                    for (int i = 0; i < toClick; i++) {
                                        KeybindUtils.rightClick();
                                        Thread.sleep(Config.autoArrowAlignDelay);
                                    }
                                    Thread.sleep(200);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "ShadyAddons-Alignment");
            thread.start();
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        itemFrames.clear();
        for (Entity entity1 : (Shady.mc.theWorld.loadedEntityList)) {
            if (entity1 instanceof EntityItemFrame) {
                itemFrames.add(entity1);
            }
        }
    }

    private static String findPattern() {
        BlockPos topLeft = new BlockPos(196, 124, 278);
        ArrayList<Entity> redWools = new ArrayList<>();
        ArrayList<Entity> greenWools = new ArrayList<>();

        itemFrames.forEach(itemFrame -> {
            ItemStack itemStack = ((EntityItemFrame) itemFrame).getDisplayedItem();
            if (itemStack != null) {
                String itemString = itemStack.toString();
                if (itemString.contains("cloth@14")) {
                    redWools.add(itemFrame);
                } else if (itemString.contains("cloth@5")) {
                    greenWools.add(itemFrame);
                }
            }
        });
        int relativeR1 = topLeft.getY() - (((Entity) redWools.toArray()[0]).getPosition()).getY();
        switch (redWools.size()) {
            case 1:
                switch (greenWools.size()) {
                    case 1:
                        if (relativeR1 == 4) {
                            return "legs";
                        }
                        if (relativeR1 == 0) {
                            return "N";
                        }
                        return "spiral";
                    case 2:
                        if (relativeR1 == 2) {
                            return "W";
                        }
                        return "bottleneck";
                }
                break;
            case 2:
                if (greenWools.size() > 1) return "zigzag";
                return "S";
            case 3:
                return "lines";
        }
        return "Unrecognized";
    }

    private static void initFrame() {
        BlockPos topLeft = new BlockPos(196, 124, 278);
        String pattern = findPattern();
        switch (pattern) {
            case "legs":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 1), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 2), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 1), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 3), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 1), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 3), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 1), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 3), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 3), 7);
                break;
            case "N":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 2), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 4), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 2), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 2), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 2), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 2), 5);

                break;
            case "spiral":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ()), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 2), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 4), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ()), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ()), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ()), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 2), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ()), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 1), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 2), 7);

                break;
            case "W":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ()), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 4), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ()), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 4), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ()), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 2), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 4), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ()), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 1), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 2), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 4), 5);

                break;
            case "bottleneck":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ()), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 1), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 4), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 3), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 4), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 1), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 3), 7);

                break;
            case "S":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 1), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 2), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 3), 1);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 4), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 2), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 4), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 2), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ()), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 2), 5);
                break;
            case "lines":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 2), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 2), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 2), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 3), 5);
                break;
            case "zigzag":
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 2), 3);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY(), topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 1, topLeft.getZ() + 2), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 2), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 2, topLeft.getZ() + 3), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 1), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 3, topLeft.getZ() + 2), 5);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 2), 7);
                clicksPerFrame.put(new BlockPos(topLeft.getX(), topLeft.getY() - 4, topLeft.getZ() + 3), 5);
                break;
        }

    }

    private static boolean isInSection3(BlockPos blockPos) {
        int x = Shady.mc.thePlayer.getPosition().getX();
        int z = Shady.mc.thePlayer.getPosition().getZ();
        return x < 218 && z > 251 && x > 196 && z < 319;
    }

}
