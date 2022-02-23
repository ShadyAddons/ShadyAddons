package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;
import cheaters.get.banned.gui.config.ConfigLogic;
import cheaters.get.banned.gui.config.settings.BooleanSetting;
import cheaters.get.banned.gui.config.settings.Setting;
import cheaters.get.banned.utils.Utils;

public class ToggleFeatureAction extends Action {

    private Setting setting;
    private boolean sendMessage;

    public ToggleFeatureAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        sendMessage = data.keyAsBool("send_message");
        String name = data.keyAsString("feature_name");
        setting = ConfigLogic.getSettingByName(name, Shady.settings);
        if(!(setting instanceof BooleanSetting)) throw new RoutineRuntimeException("Invalid setting, or setting is not toggleable");
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        Boolean value = setting.get(Boolean.class);
        if(value == null) {
            setting.set(setting.get(Boolean.class));
        } else {
            setting.set(!value);
        }

        if(sendMessage) {
            Utils.sendMessage("&7ShadyAddons Routines > &fTurned \"" + setting.name + "\" &l" + (setting.get(Boolean.class) ? "&aON" : "&cOFF"));
        }

        ConfigLogic.save();
    }

}
