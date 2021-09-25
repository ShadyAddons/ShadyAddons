package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.BlockChangeEvent;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GemstoneESP {

    private HashMap<BlockPos, Gemstone> gemstones = new HashMap<>();
    private HashSet<BlockPos> checked = new HashSet<>();
    private BlockPos lastChecked = null;
    public static int radius = 15;

    enum Gemstone {
        RUBY(Color.RED, EnumDyeColor.RED),
        AMETHYST(Color.PINK, EnumDyeColor.PURPLE),
        JADE(Color.GREEN, EnumDyeColor.LIME),
        SAPPHIRE(Color.BLUE, EnumDyeColor.BLUE),
        AMBER(Color.ORANGE, EnumDyeColor.ORANGE),
        TOPAZ(Color.YELLOW, EnumDyeColor.YELLOW),
        JASPER(Color.MAGENTA, EnumDyeColor.MAGENTA);

        Color color;
        EnumDyeColor dyeColor;
        Gemstone(Color color, EnumDyeColor dyeColor) {
            this.color = color;
            this.dyeColor = dyeColor;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(isEnabled() && (lastChecked == null || !lastChecked.equals(Shady.mc.thePlayer.playerLocation))) {
            new Thread(()->{

                BlockPos playerPosition = Shady.mc.thePlayer.playerLocation;
                lastChecked = playerPosition;

                for(int x = playerPosition.getX()-radius; x < playerPosition.getX()+radius; x++) {
                    for(int y = playerPosition.getY()-radius; y < playerPosition.getY()+radius; y++) {
                        for(int z = playerPosition.getZ()-radius; z < playerPosition.getZ()+radius; z++) {

                            BlockPos position = new BlockPos(x, y, z);

                            if(!checked.contains(position) && !Shady.mc.theWorld.isAirBlock(position)) {
                                gemstones.put(position, getGemstone(Shady.mc.theWorld.getBlockState(position)));
                            }
                            checked.add(position);

                        }
                    }
                }

            }, "ShadyAddons-GemstoneScanner").start();
        }
    }

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if(isEnabled()) System.out.println("Change detected "+event.position.distanceSq(Shady.mc.thePlayer.playerLocation)+" blocks away");
        if(isEnabled() && event.position.distanceSq(Shady.mc.thePlayer.playerLocation) < 15*15 && event.oldBlock.getBlock() == Blocks.stained_glass && event.newBlock.getBlock() == Blocks.air) {
            System.out.println("Gemstone removed from list");
            gemstones.remove(event.position);
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(isEnabled()) {
            for(Map.Entry<BlockPos, Gemstone> gemstone : gemstones.entrySet()) {
                RenderUtils.highlightBlock(gemstone.getKey(), gemstone.getValue().color, event.partialTicks);
            }
        }
    }

    private static boolean isEnabled() {
        return Shady.mc.thePlayer != null && Shady.mc.theWorld != null && Config.gemstoneEsp && Utils.inSkyBlock;
    }

    private static Gemstone getGemstone(IBlockState block) {
        if(block.getBlock() != Blocks.stained_glass) return null;

        EnumDyeColor color = block.getValue(BlockStainedGlass.COLOR);

        if(color == Gemstone.RUBY.dyeColor && Config.rubyEsp) return Gemstone.RUBY;
        if(color == Gemstone.AMETHYST.dyeColor && Config.amethystEsp) return Gemstone.AMETHYST;
        if(color == Gemstone.JADE.dyeColor && Config.jadeEsp) return Gemstone.JADE;
        if(color == Gemstone.SAPPHIRE.dyeColor && Config.sapphireEsp) return Gemstone.SAPPHIRE;
        if(color == Gemstone.AMBER.dyeColor && Config.amberEsp) return Gemstone.AMBER;
        if(color == Gemstone.TOPAZ.dyeColor && Config.topazEsp) return Gemstone.TOPAZ;
        if(color == Gemstone.JASPER.dyeColor && Config.jasperEsp) return Gemstone.JASPER;

        return null;
    }

}
