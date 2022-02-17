package cheaters.get.banned.gui.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.AutoTerminals;
import cheaters.get.banned.features.include.AutoWardrobe;
import cheaters.get.banned.features.include.commandpalette.CommandPalette;
import cheaters.get.banned.features.include.dungeonmap.DungeonMap;
import cheaters.get.banned.features.include.dungeonmap.Room;
import cheaters.get.banned.features.include.dungeonmap.RoomLoader;
import cheaters.get.banned.features.include.jokes.CatPeople;
import cheaters.get.banned.features.include.routines.Routine;
import cheaters.get.banned.features.include.routines.Routines;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.MathUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainCommand extends CommandBase {

    private static final String UNKNOWN_COMMAND = "Unrecognized command!";
    private static final String INVALID_ARGUMENTS = "Invalid arguments!";

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
            Utils.sendMessageAsPlayer("/" + RandomStringUtils.random(10, true, false));
            return;
        }

        if(args.length > 0) {
            switch(args[0]) {
                case "routines":
                    // Shady.guiToOpen = new RoutinesGui();
                    try {
                        Desktop.getDesktop().open(Routines.routinesDir);
                        Utils.sendModMessage("There is no GUI yet :)");
                    } catch(IOException e) {
                        Utils.sendModMessage("Unable to open directory");
                        Utils.sendModMessage("Find it manually at .../minecraft/config/shady/routines");
                    }
                    break;

                case "wardrobe":
                    if(!Utils.inSkyBlock) {
                        Utils.sendModMessage("You must be in SkyBlock to use this command!");
                        return;
                    }

                    if(args.length == 1) {
                        AutoWardrobe.open(1, 0);
                        return;
                    }

                    if(!MathUtils.isNumber(args[1])) {
                        Utils.sendModMessage(INVALID_ARGUMENTS);
                        return;
                    }

                    if(args.length == 2) {
                        int slot = Integer.parseInt(args[1]);

                        if(slot > 0 && slot <= 9) {
                            AutoWardrobe.open(1, slot);
                            return;
                        } else if(slot < 18) {
                            AutoWardrobe.open(2, slot % 9);
                            return;
                        } else if(slot == 18) {
                            AutoWardrobe.open(2, 9);
                            return;
                        }

                        Utils.sendModMessage(INVALID_ARGUMENTS);
                        return;
                    }

                    if(args.length == 3) {
                        if(!MathUtils.isNumber(args[2])) {
                            Utils.sendModMessage(INVALID_ARGUMENTS);
                            return;
                        }

                        int page = Integer.parseInt(args[1]);
                        int slot = Integer.parseInt(args[2]);

                        if(page > 2) page = 2;

                        AutoWardrobe.open(page, slot % 9);
                    }
                    break;

                case "force_dungeon":
                    Utils.forceDungeon = !Utils.forceDungeon;
                    Utils.sendModMessage("Toggled forcing dungeon to "+Utils.forceDungeon);
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

                            case "routines":
                                for(Routine routine : Routines.routines.values()) {
                                    Utils.sendModMessage(StringUtils.rightPad(routine.name + ' ', 40, '-'));
                                    Utils.sendModMessage("Author: " + routine.author);
                                    Utils.sendModMessage("Concurrent: " + (routine.allowConcurrent ? "true" : "false"));
                                    Utils.sendModMessage("Trigger: " + routine.trigger.getClass().getSimpleName());
                                    Utils.sendModMessage("Actions: " + routine.actions.size());
                                }
                                break;

                            case "palette":
                                Shady.guiToOpen = new CommandPalette();
                                break;

                            case "crash":
                                Shady.shouldCrash = true;
                                break;

                            case "copy_look":
                                if(Shady.mc.objectMouseOver != null) {
                                    Utils.copyToClipboard(Shady.mc.objectMouseOver.getBlockPos().getX() + ", " + Shady.mc.objectMouseOver.getBlockPos().getY() + ", " + Shady.mc.objectMouseOver.getBlockPos().getZ());
                                }
                                break;

                            case "catperson":
                            case "cat":
                                CatPeople.addRandomCatPerson(MathUtils.random(0, 3));
                                Utils.sendModMessage("rawr");
                                break;

                            case "terminals":
                            case "terms":
                                AutoTerminals.testing = !AutoTerminals.testing;
                                Utils.sendModMessage("Toggled testing terminals to "+AutoTerminals.testing);
                                if(!Utils.forceDungeon) Utils.executeCommand("/sh force_dungeon");
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
                                if(!Utils.forceDungeon) Utils.executeCommand("/sh force_dungeon");
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
                    Utils.sendModMessage(UNKNOWN_COMMAND);
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
