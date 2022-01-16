package cheaters.get.banned.features.include;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.settings.FolderSetting;
import cheaters.get.banned.events.BlockChangeEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GemstoneESP {

    private ConcurrentHashMap<BlockPos, Gemstone> gemstones = new ConcurrentHashMap<>();
    private HashSet<BlockPos> checked = new HashSet<>();
    private BlockPos lastChecked = null;
    private boolean isScanning = false;

    enum Gemstone {
        RUBY(new Color(188, 3, 29), EnumDyeColor.RED),
        AMETHYST(new Color(137, 0, 201), EnumDyeColor.PURPLE),
        JADE(new Color(157, 249, 32), EnumDyeColor.LIME),
        SAPPHIRE(new Color(60, 121, 224), EnumDyeColor.LIGHT_BLUE),
        AMBER(new Color(237, 139, 35), EnumDyeColor.ORANGE),
        TOPAZ(new Color(249, 215, 36), EnumDyeColor.YELLOW),
        JASPER(new Color(214, 15, 150), EnumDyeColor.MAGENTA),

        RUBY_SHARD(RUBY),
        AMETHYST_SHARD(AMETHYST),
        JADE_SHARD(JADE),
        SAPPHIRE_SHARD(SAPPHIRE),
        AMBER_SHARD(AMBER),
        TOPAZ_SHARD(TOPAZ),
        JASPER_SHARD(JASPER);

        public Color color;
        public EnumDyeColor dyeColor;

        Gemstone(Color color, EnumDyeColor dyeColor) {
            this.color = color;
            this.dyeColor = dyeColor;
        }

        Gemstone(Gemstone gemstone) {
            this.color = gemstone.color;
            this.dyeColor = gemstone.dyeColor;
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(isEnabled() && !isScanning && (lastChecked == null || !lastChecked.equals(Shady.mc.thePlayer.playerLocation))) {
            isScanning = true;
            new Thread(()->{

                BlockPos playerPosition = Shady.mc.thePlayer.getPosition();
                lastChecked = playerPosition;

                for(int x = playerPosition.getX()-Config.gemstoneRadius; x < playerPosition.getX()+Config.gemstoneRadius; x++) {
                    for(int y = playerPosition.getY()-Config.gemstoneRadius; y < playerPosition.getY()+Config.gemstoneRadius; y++) {
                        for(int z = playerPosition.getZ()-Config.gemstoneRadius; z < playerPosition.getZ()+Config.gemstoneRadius; z++) {

                            BlockPos position = new BlockPos(x, y, z);

                            if(!checked.contains(position) && !Shady.mc.theWorld.isAirBlock(position)) {
                                Gemstone gemstone = getGemstone(Shady.mc.theWorld.getBlockState(position));
                                if(gemstone != null) gemstones.put(position, gemstone);
                            }
                            checked.add(position);

                        }
                    }
                }

                isScanning = false;

            }, "ShadyAddons-GemstoneScanner").start();
        }
    }

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if(event.newBlock.getBlock() == Blocks.air) {
            gemstones.remove(event.position);
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(isEnabled()) {
            for(Map.Entry<BlockPos, Gemstone> gemstone : gemstones.entrySet()) {
                if(!isGemstoneEnabled(gemstone.getValue())) continue;
                double distanceSq = gemstone.getKey().distanceSq(Shady.mc.thePlayer.posX, Shady.mc.thePlayer.posY, Shady.mc.thePlayer.posZ);
                if(distanceSq > Math.pow(Config.gemstoneRadius + 2, 2)) continue;

                if(Config.highlightMode == 0) { // Outlined
                    RenderUtils.outlineBlock(gemstone.getKey(), gemstone.getValue().color, event.partialTicks);
                } else { // Filled
                    RenderUtils.highlightBlock(gemstone.getKey(), gemstone.getValue().color, event.partialTicks);
                }
            }
        }
    }

    private static boolean isEnabled() {
        return Shady.mc.thePlayer != null &&
                Shady.mc.theWorld != null &&
                FolderSetting.isEnabled("Gemstone ESP") &&
                Utils.inSkyBlock &&
                LocationUtils.onIsland(LocationUtils.Island.CRYSTAL_HOLLOWS);
    }

    private static Gemstone getGemstone(IBlockState block) {
        if(block.getBlock() != Blocks.stained_glass && block.getBlock() != Blocks.stained_glass_pane) return null;

        EnumDyeColor color = Utils.firstNotNull(block.getValue(BlockStainedGlass.COLOR), block.getValue(BlockStainedGlassPane.COLOR));

        if(color == Gemstone.RUBY.dyeColor) return Gemstone.RUBY;
        if(color == Gemstone.AMETHYST.dyeColor) return Gemstone.AMETHYST;
        if(color == Gemstone.JADE.dyeColor) return Gemstone.JADE;
        if(color == Gemstone.SAPPHIRE.dyeColor) return Gemstone.SAPPHIRE;
        if(color == Gemstone.AMBER.dyeColor) return Gemstone.AMBER;
        if(color == Gemstone.TOPAZ.dyeColor) return Gemstone.TOPAZ;
        if(color == Gemstone.JASPER.dyeColor) return Gemstone.JASPER;

        return null;
    }

    private static boolean isGemstoneEnabled(Gemstone gemstone) {
        if(Config.includeGlassPanes) {
            switch(gemstone) {
                case RUBY_SHARD:
                    return Config.rubyEsp;
                case AMETHYST_SHARD:
                    return Config.amethystEsp;
                case JADE_SHARD:
                    return Config.jadeEsp;
                case SAPPHIRE_SHARD:
                    return Config.sapphireEsp;
                case AMBER_SHARD:
                    return Config.amberEsp;
                case TOPAZ_SHARD:
                    return Config.topazEsp;
                case JASPER_SHARD:
                    return Config.jasperEsp;
            }
        }

        switch(gemstone) {
            case RUBY:
                return Config.rubyEsp;
            case AMETHYST:
                return Config.amethystEsp;
            case JADE:
                return Config.jadeEsp;
            case SAPPHIRE:
                return Config.sapphireEsp;
            case AMBER:
                return Config.amberEsp;
            case TOPAZ:
                return Config.topazEsp;
            case JASPER:
                return Config.jasperEsp;
            default:
                return false;
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        gemstones.clear();
        checked.clear();
        lastChecked = null;
    }

}
