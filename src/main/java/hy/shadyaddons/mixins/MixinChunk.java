package hy.shadyaddons.mixins;

import hy.shadyaddons.events.BlockChangeEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chunk.class)
public abstract class MixinChunk {

    @Shadow public abstract IBlockState getBlockState(BlockPos pos);

    @Inject(method = "setBlockState", at = @At("HEAD"))
    private void onBlockChange(BlockPos position, IBlockState newBlock, CallbackInfoReturnable<IBlockState> callbackInfoReturnable) {
        IBlockState oldBlock = this.getBlockState(position);

        if(oldBlock != newBlock) {
            try {
                MinecraftForge.EVENT_BUS.post(new BlockChangeEvent(position, oldBlock, newBlock));
            } catch(Throwable ignored) {}
        }
    }

}
