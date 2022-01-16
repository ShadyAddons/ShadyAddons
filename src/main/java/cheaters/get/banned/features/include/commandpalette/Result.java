package cheaters.get.banned.features.include.commandpalette;

import cheaters.get.banned.features.include.commandpalette.actions.CommandAction;
import cheaters.get.banned.features.include.commandpalette.actions.IAction;
import cheaters.get.banned.features.include.commandpalette.icons.IIcon;

public class Result {

    public String name;
    public IIcon icon;
    public IAction action;
    public String description = null;
    public boolean pin = false;

    public Result(String name, IIcon icon, IAction action) {
        this.name = name;
        this.icon = icon;
        this.action = action;

        if(action instanceof CommandAction) {
            description = ((CommandAction) action).getCommand();
        }
    }

    public Result(String name, IIcon icon, IAction action, String description) {
        this.name = name;
        this.icon = icon;
        this.action = action;
        this.description = description;
    }

}
