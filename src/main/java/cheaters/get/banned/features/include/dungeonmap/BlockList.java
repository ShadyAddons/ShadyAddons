package cheaters.get.banned.features.include.dungeonmap;

import cheaters.get.banned.Shady;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

import java.util.HashMap;

public class BlockList {

    public HashMap<Block, Integer> blocks;

    public BlockList(BlockPos position) {
        this.blocks = getBlockFrequencyList(position.getX(), position.getY(), position.getZ());
    }

    public BlockList(int x, int y, int z) {
        this.blocks = getBlockFrequencyList(x, y, z);
    }

    public HashMap<Block, Integer> getBlocks() {
        return blocks;
    }

    public static HashMap<Block, Integer> getBlockFrequencyList(int xPosition, int yPosition, int zPosition) {
        HashMap<Block, Integer> blockFrequency = new HashMap<>();
        for(int x = xPosition-16; x < xPosition+16; x++) {
            for(int z = zPosition-16; z < zPosition+16; z++) {
                Block block = Shady.mc.theWorld.getBlockState(new BlockPos(x, yPosition, z)).getBlock();
                blockFrequency.put(block, blockFrequency.containsKey(block) ? blockFrequency.get(block)+1 : 1);
            }
        }
        return blockFrequency;
    }

}
