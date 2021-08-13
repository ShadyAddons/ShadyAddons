package shady.shady.shady.config;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import shady.shady.shady.Shady;
import shady.shady.shady.features.Jokes;
import shady.shady.shady.utils.Utils;

import java.util.List;

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
                case "fake_ban":
                    Jokes.fakeBan();
                    break;
            }
        } else {
            Shady.guiToOpen = new ConfigGui();
        }
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if(args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "force_dungeon", "force_skyblock");
        }
        return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}
