package cheaters.get.banned.features.include.commandpalette.actions;

import cheaters.get.banned.utils.Utils;

public class CommandAction implements IAction {

    private String command;

    public CommandAction(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        Utils.executeCommand(command);
    }

    public String getCommand() {
        return command;
    }

}
