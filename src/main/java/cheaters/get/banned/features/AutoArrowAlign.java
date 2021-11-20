package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.events.TickEndEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AutoArrowAlign {
    private static final ArrayList<Entity> itemFrames = new ArrayList<>();
    private static final ArrayList<Entity> arrowItemFrames = new ArrayList<>();
    private static final ArrayList<Entity> doneClicking = new ArrayList<>();
    private static final Map<BlockPos, Integer> clicksPerFrame = new HashMap<>();
    private static final Map<BlockPos, Integer> trackClicks = new HashMap<>();
    private static final Map<BlockPos, Integer> toClickMap = new HashMap<>();
    private static boolean start = false;
    private static boolean init = false;
    private static boolean accurate = false;
    private static final BlockPos topLeft = new BlockPos(196, 125, 278);
    private Thread thread;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (!Config.autoArrowAlign) return;
        if (!isInSection3()) return;
        MovingObjectPosition objectMouseOver = Shady.mc.objectMouseOver;
        if (objectMouseOver != null && objectMouseOver.entityHit != null) {
            Entity entity = objectMouseOver.entityHit;
            BlockPos BP = new BlockPos(topLeft.getX(), entity.getPosition().getY(), entity.getPosition().getZ());
            if (doneClicking.contains(entity)) return;
            if (toClickMap.containsKey(BP)) {
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(() -> {
                        try {
                            rightClick();
                            Thread.sleep(Config.autoArrowAlignDelay);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, "Auto Arrow Aligner");
                    thread.start();
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender2(RenderWorldLastEvent event) {
        if (!Config.autoArrowAlign && !Config.blockArrowAlign) return;
        if(start) {
            initFrame();
            if (init && !accurate) {
                arrowItemFrames.forEach(itemFrame -> {
                    BlockPos BP = new BlockPos(topLeft.getX(), itemFrame.getPosition().getY(), itemFrame.getPosition().getZ());
                    int endRotationAmount = 0;
                    if (clicksPerFrame.containsKey(BP)) {
                        endRotationAmount = clicksPerFrame.get(BP);
                    }
                    int currRotationAmount = ((EntityItemFrame) itemFrame).getRotation();
                    int toClick = 0;
                    if (currRotationAmount < endRotationAmount) {
                        toClick = endRotationAmount - currRotationAmount;
                    } else if (currRotationAmount > endRotationAmount) {
                        currRotationAmount = currRotationAmount - 8;
                        toClick = endRotationAmount - currRotationAmount;
                    }
                    toClickMap.put(BP, toClick);
                });
                accurate = true;
            }
        }
    }

    /*@SubscribeEvent
    public void debugging(RenderWorldLastEvent event) {
        if (!Config.autoArrowAlign && !Config.blockArrowAlign) return;
        if (init) {
            for (int y = 126; y > 119; y--) {
                String line = "";
                for (int z = 278; z < 283; z++) {
                    BlockPos BP = new BlockPos(topLeft.getX(), y, z);
                    int toClick = -1;
                    if (toClickMap.containsKey(BP)) {
                        toClick = toClickMap.get(BP);
                    }
                    line += (toClick + " ");
                }
                Utils.sendMessage(y + ": " + line);
            }
        }
    }*/


    @SubscribeEvent
    public void onInteract(ClickEvent.Right event) {
        if (!Config.autoArrowAlign && !Config.blockArrowAlign) return;
        MovingObjectPosition objectMouseOver = Shady.mc.objectMouseOver;
        if (objectMouseOver != null && objectMouseOver.entityHit != null) {
            Entity entity = objectMouseOver.entityHit;
            BlockPos BP = new BlockPos(topLeft.getX(), entity.getPosition().getY(), entity.getPosition().getZ());
            if (doneClicking.contains(entity)) {
                if (Config.blockArrowAlign) {
                    event.setCanceled(true);
                    return;
                }
            }
            if (toClickMap.containsKey(BP)) {
                if (trackClicks.containsKey(BP)) {
                    trackClicks.put(BP, trackClicks.get(BP) + 1);
                } else {
                    trackClicks.put(BP, 1);
                }
                if ((trackClicks.get(BP)) > toClickMap.get(BP)) {
                    if (Config.blockArrowAlign) {
                        event.setCanceled(true);
                        trackClicks.put(BP, trackClicks.get(BP) - 1);
                        doneClicking.add(entity);
                        //accurate = false; keyed out because it caused errors but might be implementable
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (!Config.autoArrowAlign && !Config.blockArrowAlign) return;
        if(!start) {
            MovingObjectPosition objectMouseOver = Shady.mc.objectMouseOver;
            if (objectMouseOver != null && objectMouseOver.entityHit != null) {
                Entity entity = objectMouseOver.entityHit;
                if (entity instanceof EntityItemFrame) {
                    start = true;
                }
            }
        }
        itemFrames.clear();
        for (Entity entity1 : (Shady.mc.theWorld.loadedEntityList)) {
            if (entity1 instanceof EntityItemFrame) {
                itemFrames.add(entity1);
            }
        }
        arrowItemFrames.clear();
        itemFrames.forEach(itemFrame -> {
            ItemStack itemStack = ((EntityItemFrame) itemFrame).getDisplayedItem();
            if (itemStack != null) {
                if(itemStack.getItem() == Items.arrow) {
                    arrowItemFrames.add(itemFrame);
                }
            }
        });
    }

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload event) {
        init = false;
        accurate = false;
        start = false;
        trackClicks.clear();
        toClickMap.clear();
        clicksPerFrame.clear();
    }

    private static String findPattern() {
        ArrayList<Entity> redWools = new ArrayList<>();
        ArrayList<Entity> greenWools = new ArrayList<>();

        itemFrames.forEach(itemFrame -> {
            ItemStack itemStack = ((EntityItemFrame) itemFrame).getDisplayedItem();
            if (itemStack != null) {
                if(itemStack.getItem() instanceof ItemCloth && itemStack.getItemDamage() == 14) {
                    redWools.add(itemFrame);
                }
                if(itemStack.getItem() instanceof ItemCloth && itemStack.getItemDamage() == 5) {
                    greenWools.add(itemFrame);
                }
            }
        });
        if (redWools.size() != 0 && greenWools.size() != 0) {
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
        }
        return "Unrecognized";
    }

    private static void initFrame() {
        if (init) return;
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
                init = true;
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
                init = true;
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
                init = true;
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
                init = true;
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
                init = true;
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
                init = true;
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
                init = true;
                break;
        }

    }

    public static void rightClick() {
        try {
            Method rightClickMouse;
            try {
                rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse");
            } catch (NoSuchMethodException e) {
                rightClickMouse = Minecraft.class.getDeclaredMethod("func_147121_ag");
            }
            rightClickMouse.setAccessible(true);
            rightClickMouse.invoke(Shady.mc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isInSection3() {
        int x = Shady.mc.thePlayer.getPosition().getX();
        int z = Shady.mc.thePlayer.getPosition().getZ();
        return x < 218 && z > 251 && x > 196 && z < 319;
    }
}
