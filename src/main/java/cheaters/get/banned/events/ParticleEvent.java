package cheaters.get.banned.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ParticleEvent extends Event {

    public EnumParticleTypes particleType;
    public double xCoord;
    public double yCoord;
    public double zCoord;
    public float xOffset;
    public float yOffset;
    public float zOffset;

    public ParticleEvent(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, float xOffset, float yOffset, float zOffset) {
        this.particleType = particleType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }
}
