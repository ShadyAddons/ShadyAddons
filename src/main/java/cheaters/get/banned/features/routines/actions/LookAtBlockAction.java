package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.RotationUtils;
import net.minecraft.util.BlockPos;

/**
 * {
 *     "name": "LookAtBlockAction",
 *     "x": int,
 *     "y": int,
 *     "z": int,
 *     "ticks_per_180": int
 * }
 */

public class LookAtBlockAction extends Action {

    private int x;
    private int y;
    private int z;
    private int ticks;

    public LookAtBlockAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);

        x = data.keyAsInt("x");
        y = data.keyAsInt("y");
        z = data.keyAsInt("z");

        ticks = data.keyAsInt("ticks_per_180");
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        RotationUtils.Rotation rotation = RotationUtils.getRotationToBlock(new BlockPos(x, y, z));
        RotationUtils.smartLook(rotation, ticks, null);
    }

}
