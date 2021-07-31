package hy.shadyaddons.config;

import hy.shadyaddons.ShadyAddons;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ConfigCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "sh";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        ShadyAddons.guiToOpen = new ConfigGui();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}
