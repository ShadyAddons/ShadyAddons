package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.KeybindUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class AutoAlignArrows {

    private static HashMap<BlockPos, ItemFrame> itemFrames = new HashMap<>();
    private static boolean calculatedPath = false;

    private static class ItemFrame {
        public BlockPos position;
        private Rotation finalRotation = null;
        private EntityItemFrame entity;
        public Type type;

        public ItemFrame(EntityItemFrame entity) {
            this.entity = entity;

            if(entity.getDisplayedItem().getItem() == Items.arrow) type = Type.NORMAL;

            if(entity.getDisplayedItem().getItem() == Item.getItemFromBlock(Blocks.wool)) {
                if(entity.getDisplayedItem().getItemDamage() == 5) type = Type.START; // Lime Green Wool
                if(entity.getDisplayedItem().getItemDamage() == 14) type = Type.END; // Red Wool
            }
        }

        public boolean shouldClick() {
            if(finalRotation == null || type != Type.NORMAL) return false;
            return entity.getRotation() != finalRotation.number;
        }

        enum Rotation {
            NORTH(7, EnumFacing.UP), SOUTH(3, EnumFacing.DOWN), EAST(1, EnumFacing.NORTH), WEST(5, EnumFacing.SOUTH);

            public int number;
            public EnumFacing facing;
            Rotation(int number, EnumFacing facing) {
                this.number = number;
                this.facing = facing;
            }
        }

        enum Type { NORMAL, START, END }
    }

    private int counter = 0;
    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(counter % 5 == 0 && isEnabled()) {
            counter = 0;
            if(itemFrames.isEmpty()) {
                addItemFrames();
            } else {
                if(calculatedPath) {
                    if(Shady.mc.objectMouseOver.entityHit instanceof EntityItemFrame && itemFrames.containsKey(Shady.mc.objectMouseOver.entityHit.getPosition())) {
                        if(itemFrames.get(Shady.mc.objectMouseOver.entityHit.getPosition()).shouldClick()) {
                            KeybindUtils.rightClick();
                        }
                    }
                } else {
                    calculatePath();
                }
            }
        }
        counter++;
    }

    private static void calculatePath() {
        for(Map.Entry<BlockPos, ItemFrame> frame : itemFrames.entrySet()) {
            BlockPos position = frame.getKey();
            ItemFrame itemFrame = frame.getValue();

            if(itemFrame.finalRotation == null) {
                for(ItemFrame.Rotation rotation : ItemFrame.Rotation.values()) {
                    if(itemFrames.containsKey(position.offset(rotation.facing))) {
                        itemFrame.finalRotation = rotation;
                        break;
                    }
                }
            }
        }
        calculatedPath = true;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        itemFrames.clear();
        calculatedPath = false;
    }

    private static boolean isEnabled() {
        return Config.autoArrowAlign &&
                // Utils.inDungeon &&
                Shady.mc.thePlayer != null &&
                Shady.mc.thePlayer.getPosition().getY() > 100 &&
                Shady.mc.thePlayer.getPosition().getY() < 150 &&
                isInSection3(Shady.mc.thePlayer.getPosition()) /*&&
                DungeonUtils.dungeonRun != null &&
                DungeonUtils.dungeonRun.inBoss &&
                DungeonUtils.inFloor(DungeonUtils.Floor.FLOOR_7)*/;
    }

    private static void addItemFrames() {
        for(Entity entity : Shady.mc.theWorld.loadedEntityList) {
            if(entity instanceof EntityItemFrame) {
                if(isInSection3(entity.getPosition())) {
                    itemFrames.put(entity.getPosition(), new ItemFrame((EntityItemFrame) entity));
                }
            }
        }
    }

    private static boolean isInSection3(BlockPos blockPos) {
        int x = Shady.mc.thePlayer.getPosition().getX();
        int z = Shady.mc.thePlayer.getPosition().getZ();
        return x < 218 && z > 251 && x > 196 && z < 319;
    }

}
