package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.RayMarchUtils;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TerminalReach {

    private BlockPos selectedTerminal = null;
    private EntityArmorStand selectedTerminalStand = null;

    private static List<BlockPos> terminals = Arrays.asList(
            new BlockPos(310, 113, 272),
            new BlockPos(310, 119, 278),
            new BlockPos(288, 112, 291),
            new BlockPos(288, 122, 300),

            new BlockPos(267, 109, 320),
            new BlockPos(258, 120, 321),
            new BlockPos(246, 109, 320),
            new BlockPos(238, 108, 342),

            new BlockPos(196, 109, 311),
            new BlockPos(196, 119, 292),
            new BlockPos(218, 123, 292),
            new BlockPos(196, 109, 276),

            new BlockPos(240, 109, 228),
            new BlockPos(243, 121, 228),
            new BlockPos(266, 109, 228),
            new BlockPos(271, 115, 247)
    );
    private static final int[][][] sections = new int[][][]{
            { // Section 1
                    {310, 251}, // NE
                    {288, 319} // SW
            },
            { // Section 2
                    {287, 320}, // NE
                    {219, 342} // SW
            },
            { // Section 3
                    {218, 251}, // NE
                    {196, 319} // SW
            },
            { // Section 4
                    {287, 228}, // NE
                    {219, 250} // SW
            }
    };

    private boolean isEnabled() {
        return Config.terminalReach &&
                // Utils.inDungeon &&
                Shady.mc.thePlayer != null &&
                Shady.mc.thePlayer.isSneaking() &&
                Shady.mc.thePlayer.getPosition().getY() < 150 &&
                Shady.mc.thePlayer.getPosition().getY() > 100 /*&&
                DungeonUtils.dungeonRun != null &&
                DungeonUtils.dungeonRun.inBoss &&
                DungeonUtils.inFloor(DungeonUtils.Floor.FLOOR_7)*/;
    }

    @SubscribeEvent
    public void onRightClick(ClickEvent.Right event) {
        if(isEnabled() && selectedTerminalStand != null && selectedTerminal != null) {
            Shady.mc.playerController.interactWithEntitySendPacket(Shady.mc.thePlayer, selectedTerminalStand);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if(isEnabled()) {
            for(BlockPos terminal : terminals) {
                if(Config.outsideTerms || isInSection(terminal)) {
                    RenderUtils.highlightBlock(terminal, terminal.equals(selectedTerminal) ? Color.MAGENTA : Color.WHITE, event.partialTicks);
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(isEnabled()) {
            List<EntityArmorStand> armorStands = RayMarchUtils.getFacedEntityOfType(EntityArmorStand.class, Config.terminalReachRange);
            if(armorStands != null) {
                for(EntityArmorStand armorStand : armorStands) {
                    if(armorStand.getCustomNameTag().equals("§cInactive Terminal")) {
                        if(!Config.outsideTerms && !isInSection(armorStand.getPosition())) break;
                        terminals.sort(Comparator.comparingDouble(armorStand::getDistanceSq));
                        selectedTerminal = terminals.get(0);
                        selectedTerminalStand = armorStand;
                    }
                }
            }
        }

        selectedTerminal = null;
    }

    private static boolean isInSection(BlockPos blockPos) {
        boolean inSection = false;

        int x = blockPos.getX();
        int z = blockPos.getZ();

        for(int[][] s : sections) {
            if(x < s[0][0] && z > s[0][1] && x > s[1][0] && z < s[1][1]) {
                inSection = true;
                break;
            }
        }

        x = Shady.mc.thePlayer.getPosition().getX();
        z = Shady.mc.thePlayer.getPosition().getZ();

        for(int[][] s : sections) {
            if(x > s[0][0] || z < s[0][1] || x < s[1][0] || z > s[1][1]) {
                inSection = false;
                break;
            }
        }

        return inSection;
    }

}
