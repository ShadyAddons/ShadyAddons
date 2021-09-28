package cheaters.get.banned.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.AutoTerminals;
import cheaters.get.banned.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "sh";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<String>(){{
            add("shady");
            add("shadyaddons");
        }};
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(!Shady.enabled) {
            Shady.mc.thePlayer.sendChatMessage("/U2t5dGlscw");
            return;
        }

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

                case "test_terminals":
                    AutoTerminals.testing = !AutoTerminals.testing;
                    Utils.sendModMessage("Toggled Testing Terminals");
                    if(!Utils.forceDungeon) Utils.useCommand("shady force_dungeon");
                    break;

                case "disable":
                    Shady.disable();
                    break;
            }
        } else {
            Shady.guiToOpen = new ConfigGui(new ResourceLocation("shadyaddons:"+Utils.getLogo()+".png"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}
