package cheaters.get.banned.gui.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.AutoWardrobe;
import cheaters.get.banned.features.BlockAbilities;
import cheaters.get.banned.features.commandpalette.CommandPalette;
import cheaters.get.banned.features.routines.Routines;
import cheaters.get.banned.stats.RoutinesAPI;
import cheaters.get.banned.utils.Utils;
import jdk.nashorn.internal.ir.Block;

import java.util.Map;
import java.util.Set;

public class Config {

    @Property(
            type = Property.Type.BUTTON,
            name = "Official Discord Server",
            button = "Join"
    )
    public static Runnable joinDiscord = () -> Utils.openUrl("https://shadyaddons.com/discord");

    @Property(
            type = Property.Type.BUTTON,
            name = "ShadyAddons Store",
            note = "Animated capes and more!",
            button = "Visit"
    )
    public static Runnable openShop = () -> Utils.openUrl("https://shadyaddons.com/store");


    @Property(type = Property.Type.SPACER)
    public static Object spacer1;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Stonkless Stonk"
    )
    public static boolean stonklessStonk = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Always On",
                parent = "Stonkless Stonk"
        )
        public static boolean alwaysOn = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Disable in Boss",
                parent = "Stonkless Stonk"
        )
        public static boolean disableInBoss = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Only Enable When Holding Pickaxe",
                parent = "Stonkless Stonk"
        )
        public static boolean onlyEnableWhenHoldingPickaxe = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Routines"
    )
    public static boolean routinesFolder = false;

        @Property(
                type = Property.Type.BUTTON,
                button = "Visit",
                name = "Create & Share Routines",
                parent = "Routines"
        )
        public static Runnable openRoutinesWebsite = RoutinesAPI::openAuthWebsite;

        @Property(
                type = Property.Type.BUTTON,
                button = "Open",
                name = "Open Routines Folder",
                parent = "Routines"
        )
        public static Runnable openRoutinesFolder = () -> {
            Utils.executeCommand("/shady routines");
        };

        @Property(
                type = Property.Type.BUTTON,
                button = "Reload",
                name = "Force Reload Routines",
                parent = "Routines"
        )
        public static Runnable refreshRoutines = () -> {
            Utils.sendModMessage("Reloading routines...");
            Routines.load();
        };


    /*@Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Simon Says"
    )*/
    public static boolean autoSimonSays = false;


    /*@Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Align Arrows"
    )*/
    public static boolean autoArrowAlign = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Royal Pigeon Pickaxe Macro"
    )
    public static boolean royalPigeonMacro = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Auto-Clicker",
            warning = true
    )
    public static boolean autoClicker = false;

        @Property(
                type = Property.Type.NUMBER,
                name = "CPS",
                parent = "Auto-Clicker",
                min = 10,
                max = 500,
                step = 10,
                suffix = " CPS"
        )
        public static int autoClickerCps = 100;

        @Property(
                type = Property.Type.SELECT,
                name = "Mode",
                parent = "Auto-Clicker",
                options = {"Burst", "Continous"}
        )
        public static int autoClickerMode = 0;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Show Indicator",
                parent = "Auto-Clicker"
        )
        public static boolean autoClickerIndicator = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Stop in GUI",
                parent = "Auto-Clicker"
        )
        public static boolean stopAutoClickerInGui = false;


    @Property(
            type = Property.Type.BUTTON,
            button = "Open Palette",
            name = "Command Palette",
            note = "Command/Control + K"
    )
    public static Runnable openCommandPalette = () -> {
        Shady.guiToOpen = new CommandPalette();
        Utils.sendModMessage("You can customize the shortcut in Minecraft controls (which you can open with the Command Palette!)");
    };


    @Property(
            type = Property.Type.BUTTON,
            button = "Open Wardrobe",
            name = "Instant Wardrobe",
            note = "by RoseGold"
    )
    public static Runnable openWardrobe = () -> {
        if(Utils.inSkyBlock) {
            AutoWardrobe.open(1, 0);
            Utils.sendModMessage("Use /sh wardrobe [slot] to equip a specific set!");
        } else {
            Utils.sendModMessage("You must be in SkyBlock to use this!");
        }
    };


    @Property(
            type = Property.Type.FOLDER,
            name = "Summons Features"
    )
    public static boolean summonsFeatures = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Hide Summons",
                parent = "Summons Features"
        )
        public static boolean hideSummons = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Click Through Summons",
                parent = "Summons Features",
                warning = true
        )
        public static boolean clickThroughSummons = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Salvage"
    )
    public static boolean autoSalvage = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Automatically Start Salvaging",
                parent = "Auto-Salvage"
        )
        public static boolean automaticallyStartSalvaging = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Sell"
    )
    public static boolean autoSell = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Enchanted Snow & Clay",
                parent = "Auto-Sell"
        )
        public static boolean autoSellMinionDrops = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Salvageable Items",
                parent = "Auto-Sell"
        )
        public static boolean autoSellSalvageable = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Superboom",
                parent = "Auto-Sell"
        )
        public static boolean autoSellSuperboom = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Dungeons Junk",
                parent = "Auto-Sell"
        )
        public static boolean autoSellDungeonsJunk = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Speed/Weakness Potions",
                parent = "Auto-Sell"
        )
        public static boolean autoSellPotions = false;


    /*@Property(
            type = Property.Type.BOOLEAN,
            name = "Crystal Reach",
            note = "Sneak to activate",
            warning = true
    )*/
    public static boolean crystalReach = false;


    /*@Property(
            type = Property.Type.BOOLEAN,
            name = "Crystal Etherwarp",
            note = "Automatically teleports to the crystal"
    )*/
    public static boolean crystalEtherwarp = false;

        /*@Property(
                type = Property.Type.SELECT,
                name = "Crystal Side",
                options = {"Left", "Right"},
                parent = "Crystal Etherwarp"
        )*/
        public static int crystalSide = 0;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Renew Crystal Hollows Pass"
    )
    public static boolean renewCrystalHollowsPass = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Disable Block Animation"
    )
    public static boolean disableBlockAnimation = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Keybinds"
    )
    public static boolean keybinds = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Ghost Blocks",
                parent = "Keybinds"
        )
        public static boolean ghostBlockKeybind = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Right-Click w/ Stonk",
                    parent = "Ghost Blocks"
            )
            public static boolean stonkGhostBlock = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Normal Ability",
                parent = "Keybinds"
        )
        public static boolean normalAbilityKeybind = false;

        @Property(
                type = Property.Type.FOLDER,
                name = "Item Macros",
                parent = "Keybinds",
                warning = true
        )
        public static boolean itemHotkeys = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Ice Spray",
                    parent = "Item Macros"
            )
            public static boolean iceSprayHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Rogue Sword",
                    parent = "Item Macros"
            )
            public static boolean rogueSwordHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Power Orb",
                    parent = "Item Macros"
            )
            public static boolean powerOrbHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Weird Tuba",
                    parent = "Item Macros"
            )
            public static boolean weirdTubaHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Gyrokinetic Wand",
                    parent = "Item Macros",
                    warning = true
            )
            public static boolean gyrokineticWandHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Pigman Sword",
                    parent = "Item Macros"
            )
            public static boolean pigmanSwordHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Healing Wand",
                    parent = "Item Macros"
            )
            public static boolean healingWandHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Fishing Rod",
                    parent = "Item Macros"
            )
            public static boolean fishingRodHotkey = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Teleport w/ Anything",
                    parent = "Item Macros"
            )
            public static boolean teleportWithAnything = false;

            @Property(
                    type = Property.Type.FOLDER,
                    name = "Use Weapon w/ Anything",
                    parent = "Item Macros"
            )
            public static boolean useItemsWithAnything;

                @Property(
                        type = Property.Type.BOOLEAN,
                        name = "Disable Outside of Dungeons",
                        parent = "Use Weapon w/ Anything"
                )
                public static boolean disableOutsideDungeons = false;

                @Property(
                        type = Property.Type.CHECKBOX,
                        name = "Soul Whip w/ Anything",
                        note = "Whip whip nae nae",
                        parent = "Use Weapon w/ Anything"
                )
                public static boolean soulWhipWithAnything = false;

                @Property(
                        type = Property.Type.CHECKBOX,
                        name = "Terminator w/ Anything",
                        note = "Also works with Juju Shortbow",
                        parent = "Use Weapon w/ Anything"
                )
                public static boolean termWithAnything = false;

                @Property(
                        type = Property.Type.CHECKBOX,
                        name = "AOTS w/ Anything",
                        parent = "Use Weapon w/ Anything"
                )
                public static boolean aotsWithAnything = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Ready Up",
            warning = true
    )
    public static boolean autoReadyUp = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Block Abilities"
    )
    public static boolean blockAbilities = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Cells Alignment",
                parent = "Block Abilities"
        )
        public static boolean blockCellsAlignment = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Giant's Slam",
                parent = "Block Abilities"
        )
        public static boolean blockGiantsSlam = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Valkyrie Wither Impact",
                parent = "Block Abilities"
        )
        public static boolean blockValkyrie = false;

        @Property(
                type = Property.Type.FOLDER,
                name = "Custom Block Abilities",
                parent = "Block Abilities"
        )
        public static boolean customBlock = false;

            @Property(
                    type = Property.Type.CHECKBOX,
                    name = "Block Custom Abilities",
                    parent = "Custom Block Abilities"
            )
            public static boolean blockCustomAbilities = false;

            @Property(
                    type = Property.Type.BUTTON,
                    name = "Show Blocked Abilities",
                    parent = "Custom Block Abilities"
            )
            public static Runnable showAbilities = () -> {
                Utils.sendModMessage("Blocked Abilities:");
                if(BlockAbilities.blockedAbilities.isEmpty()){
                    Utils.sendMessage("§aEmpty");
                    return;
                }
                for(Map.Entry<String, Integer> s: BlockAbilities.blockedAbilities.entrySet()){
                    Utils.sendMessage("§f- "+s.getKey() + " " + BlockAbilities.click.get(s.getValue()));
                }
            };

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Dungeon Map"
    )
    public static boolean dungeonMap = false;

        /*@Property(
                type = Property.Type.BOOLEAN,
                name = "Darken Unexplored",
                parent = "Dungeon Map"
        )*/
        public static boolean darkenUnexplored = false;

        @Property(
                type = Property.Type.SELECT,
                name = "Show Room Names",
                parent = "Dungeon Map",
                options = {"None", "Important", "All"}
        )
        public static int showRoomNames = 0;


        @Property(
                type = Property.Type.BOOLEAN,
                name = "Show Run Information",
                parent = "Dungeon Map"
        )
        public static boolean showDungeonInfo = false;

        @Property(
                type = Property.Type.SELECT,
                name = "Show Player Heads",
                parent = "Dungeon Map",
                options = {"None", "All", "Own"}
        )
        public static int showMapPlayerHeads = 1;

        @Property(
            type = Property.Type.SELECT,
            name = "Map Border",
            parent = "Dungeon Map",
            options = {"None", "Chroma", "Black", "White"}
        )
        public static int mapBorder = 0;

        @Property(
                type = Property.Type.NUMBER,
                name = "Horizontal Offset",
                parent = "Dungeon Map",
                suffix = "px",
                step = 10
        )
        public static int mapXOffset = 10;

        @Property(
                type = Property.Type.NUMBER,
                name = "Vertical Offset",
                parent = "Dungeon Map",
                suffix = "px",
                step = 10
        )
        public static int mapYOffset = 10;

        @Property(
                type = Property.Type.NUMBER,
                name = "Scale",
                parent = "Dungeon Map",
                suffix = "%",
                step = 10,
                min = 50,
                max = 150
        )
        public static int mapScale = 80;

        @Property(
                type = Property.Type.NUMBER,
                name = "Background Opacity",
                parent = "Dungeon Map",
                max = 100,
                step = 10,
                suffix = "%"
        )
        public static int mapBackgroundOpacity = 30;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Quick Math(s) Solver"
    )
    public static boolean socialQuickMathsSolver = false;

        @Property(
                type = Property.Type.NUMBER,
                name = "Answer Delay",
                parent = "Quick Math(s) Solver",
                suffix = "ms",
                min = 50,
                max = 3000,
                step = 50
        )
        public static int quickMathsAnswerDelay = 100;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Enable Outside of SkyBlock",
                parent = "Quick Math(s) Solver"
        )
        public static boolean enableMathsOutsideSkyBlock = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Connect Four Helper",
            beta = true
    )
    public static boolean connectFourAI = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Auto-Close Chests"
    )
    public static boolean closeChests = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Secret Chests",
                parent = "Auto-Close Chests"
        )
        public static boolean closeSecretChests = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Crystal Hollows Chests",
                parent = "Auto-Close Chests"
        )
        public static boolean closeCrystalHollowsChests = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-GG"
    )
    public static boolean autoGg = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Show Hidden Mobs"
    )
    public static boolean showHiddenMobs = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Shadow Assassins",
                parent = "Show Hidden Mobs"
        )
        public static boolean showHiddenShadowAssassins = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Fels",
                parent = "Show Hidden Mobs"
        )
        public static boolean showHiddenFels = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Ghosts",
                parent = "Show Hidden Mobs"
        )
        public static boolean showGhosts = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Stealthy Blood Mobs",
                parent = "Show Hidden Mobs"
        )
        public static boolean showStealthyBloodMobs = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Sneaky Creepers",
                parent = "Show Hidden Mobs"
        )
        public static boolean showSneakyCreepers = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Mob ESP",
            note = "Disable Entity Culling in Patcher"
    )
    public static boolean mobEsp = false;

        @Property(
                type = Property.Type.NUMBER,
                name = "Outline Thickness",
                parent = "Mob ESP",
                min = 3,
                max = 10,
                suffix = "px"
        )
        public static int espThickness = 5;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Sludges",
                parent = "Mob ESP"
        )
        public static boolean sludgeEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Yogs",
                parent = "Mob ESP"
        )
        public static boolean yogEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Corleone",
                parent = "Mob ESP"
        )
        public static boolean corleoneEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Starred Mobs",
                parent = "Mob ESP"
        )
        public static boolean starredMobEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Secret Bats",
                parent = "Mob ESP"
        )
        public static boolean secretBatEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Minibosses",
                parent = "Mob ESP"
        )
        public static boolean minibossEsp = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Gemstone ESP"
    )
    public static boolean gemstoneEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Include Glass Panes",
                parent = "Gemstone ESP"
        )
        public static boolean includeGlassPanes = false;

        @Property(
                type = Property.Type.SELECT,
                name = "Highlight Mode",
                parent = "Gemstone ESP",
                options = {"Outlined", "Filled"}
        )
        public static int highlightMode = 0;

        @Property(
                type = Property.Type.NUMBER,
            name = "Scan Radius",
                parent = "Gemstone ESP",
                suffix = " blocks",
                min = 5,
                max = 30
        )
        public static int gemstoneRadius = 15;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Ruby",
                parent = "Gemstone ESP"
        )
        public static boolean rubyEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Amber",
                parent = "Gemstone ESP"
        )
        public static boolean amberEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Sapphire",
                parent = "Gemstone ESP"
        )
        public static boolean sapphireEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Jade",
                parent = "Gemstone ESP"
        )
        public static boolean jadeEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Amethyst",
                parent = "Gemstone ESP"
        )
        public static boolean amethystEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Topaz",
                parent = "Gemstone ESP"
        )
        public static boolean topazEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Jasper",
                parent = "Gemstone ESP"
        )
        public static boolean jasperEsp = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Melody"
    )
    public static boolean autoMelody = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "No Teleport Rotation",
            warning = true,
            note = "Only in SkyBlock"
    )
    public static boolean noRotate = false;

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Anti-Knockback",
            warning = true,
            note = "Only in SkyBlock"
    )
    public static boolean antiKb = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Terminals"
    )
    public static boolean autoTerminals = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Half-Trip",
                note = "Attempts to send clicks before window is updated",
                parent = "Auto-Terminals",
                warning = true
        )
        public static boolean terminalHalfTrip = false;

        @Property(
                type = Property.Type.NUMBER,
                name = "Click Delay",
                note = "Adjust this to compensate for your ping",
                parent = "Auto-Terminals",
                step =  10,
                suffix = "ms",
                min = 50,
                max = 1000
        )
        public static int terminalClickDelay = 100;

    @Property(
            type = Property.Type.FOLDER,
            name = "Blaze Slayer"
    )
    public static boolean blaze = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Attunement Swapper",
                parent = "Blaze Slayer"
        )
        public static boolean autoBlazeDagger = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Auto Cloak",
                parent = "Blaze Slayer"
        )
        public static boolean autoCloak = false;

    @Property(
            type = Property.Type.FOLDER,
            name = "Dojo"
    )
    public static boolean dojo = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Auto Switch Sword",
                parent = "Dojo"
        )
        public static boolean dojoDiscipline = false;

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Use Clear Buttons",
            note = "Not a cheat, just cosmetic"
    )
    public static boolean useCleanButtons = false;


}