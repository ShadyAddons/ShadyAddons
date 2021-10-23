package cheaters.get.banned.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.AutoTerminals;
import cheaters.get.banned.features.dungeonmap.DungeonMap;
import cheaters.get.banned.features.dungeonmap.Room;
import cheaters.get.banned.features.dungeonmap.RoomLoader;
import cheaters.get.banned.features.jokes.CatGirls;
import cheaters.get.banned.remote.MayorAPI;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.MathUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.RandomStringUtils;

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
            Utils.sendMessageAsPlayer("/"+RandomStringUtils.random(10, true, false));
            return;
        }

        if(args.length > 0) {
            switch(args[0]) {
                case "force_dungeon":
                    Utils.forceDungeon = !Utils.forceDungeon;
                    Utils.sendModMessage("Toggled forcing dungeon to "+Utils.forceDungeon);
                    break;

                case "force_paul":
                    MayorAPI.forcePaul();
                    Utils.sendModMessage("Paul has committed a coup d'état and is now mayor of SkyBlock");
                    break;

                case "force_skyblock":
                    Utils.forceSkyBlock = !Utils.forceSkyBlock;
                    Utils.sendModMessage("Toggled forcing SkyBlock to "+Utils.forceSkyBlock);
                    break;

                case "debug":
                    if(args.length > 1) {
                        switch(args[1]) {
                            case "dungeon":
                                if(Utils.inDungeon) DungeonUtils.debug();
                                break;

                            case "catperson":
                            case "cat":
                                CatGirls.addRandomCatPerson(MathUtils.random(0, 3));
                                Utils.sendModMessage("rawr");
                                break;

                            case "terminals":
                            case "terms":
                                AutoTerminals.testing = !AutoTerminals.testing;
                                Utils.sendModMessage("Toggled testing terminals to "+AutoTerminals.testing);
                                if(!Utils.forceDungeon) Utils.sendMessageAsPlayer("/shady force_dungeon");
                                break;

                            case "rooms":
                                Utils.sendModMessage("There are currently "+RoomLoader.rooms.size()+" rooms loaded");
                                for(Room room : RoomLoader.rooms) {
                                    Utils.sendModMessage(room.name);
                                }
                                break;

                            case "scan":
                                DungeonMap.debug = !DungeonMap.debug;
                                if(DungeonMap.debug) {
                                    if(DungeonMap.activeDungeonLayout != null) {
                                        Utils.sendModMessage("Rooms detected: "+DungeonMap.activeDungeonLayout.roomTiles.size());
                                        Utils.sendModMessage("Connectors detected: "+DungeonMap.activeDungeonLayout.connectorTiles.size());
                                        Utils.sendModMessage("Trap room type: "+DungeonMap.activeDungeonLayout.trapType);
                                        Utils.sendModMessage("Total secrets: "+DungeonMap.activeDungeonLayout.totalSecrets);
                                        Utils.sendModMessage("Total crypts: "+DungeonMap.activeDungeonLayout.totalCrypts);
                                    } else {
                                        Utils.sendModMessage("No scan exists");
                                    }
                                }
                                break;
                        }
                    }
                    break;

                case "force_dungeon_floor":
                    if(args.length > 1) {
                        for(DungeonUtils.Floor floor : DungeonUtils.Floor.values()) {
                            if(floor.name.replaceAll("[()]", "").equalsIgnoreCase(args[1])) {
                                if(!Utils.forceDungeon) Utils.sendMessageAsPlayer("/shady force_dungeon");
                                DungeonUtils.dungeonRun.floor = floor;
                                Utils.sendModMessage("Set floor to "+DungeonUtils.dungeonRun.floor);
                                return;
                            }
                        }
                        Utils.sendModMessage("Unable to match \""+args[1]+"\" to a dungeon floor");
                    }
                    break;

                case "disable":
                    Shady.disable();
                    break;

                default:
                    Utils.sendModMessage("Unrecognized command");
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
