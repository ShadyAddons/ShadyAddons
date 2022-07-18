package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.KeybindUtils;
import net.minecraft.entity.Entity;

public class ChangeCameraAction extends Action {
    int cameraType;

    public ChangeCameraAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        cameraType = data.keyAsInt("camera_type");
    }

    @Override
    public void doAction() {
        Shady.mc.gameSettings.thirdPersonView = cameraType % 3;
        if (Shady.mc.gameSettings.thirdPersonView == 0) Shady.mc.entityRenderer.loadEntityShader(Shady.mc.getRenderViewEntity());
        else if (Shady.mc.gameSettings.thirdPersonView == 1) Shady.mc.entityRenderer.loadEntityShader(null);
        Shady.mc.renderGlobal.setDisplayListEntitiesDirty();
    }

}
