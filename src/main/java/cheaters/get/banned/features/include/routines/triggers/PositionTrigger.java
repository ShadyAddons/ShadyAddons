package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PositionTrigger extends Trigger {

    private BlockPos position;
    private static BlockPos prevPosition;

    public PositionTrigger(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        position = new BlockPos(
                data.keyAsInt("x"),
                data.keyAsInt("y"),
                data.keyAsInt("z")
        );
    }

    @Override
    public boolean shouldTrigger(Event event) {
        boolean flag = event instanceof TickEndEvent &&
                Shady.mc.thePlayer != null &&
                !Shady.mc.thePlayer.getPosition().equals(prevPosition) &&
                Shady.mc.thePlayer.getPosition().equals(position);

        if(flag) prevPosition = position;
        return flag;
    }

}
