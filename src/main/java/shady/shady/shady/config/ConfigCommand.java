package shady.shady.shady.config;

import shady.shady.shady.ShadyAddons;
import shady.shady.shady.utils.Utils;
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
        if(args.length > 0) {
            switch(args[0]) {
                case "force_dungeon":
                    Utils.forceDungeon = !Utils.forceDungeon;
                    Utils.sendModMessage("Toggled Forcing Dungeon");
                    break;
                case "force_skyblock":
                    Utils.forceSkyBlock = !Utils.forceSkyBlock;
                    Utils.sendModMessage("Toggled Forcing SkyBlock");
                    break;
            }
        } else {
            ShadyAddons.guiToOpen = new ConfigGui();
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}
