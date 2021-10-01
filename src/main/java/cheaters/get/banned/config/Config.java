package cheaters.get.banned.config;

import cheaters.get.banned.config.properties.Property;

public class Config {

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
            name = "Auto Simon-Says"
    )
    public static boolean autoSimonSays = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Royal Pigeon Pickaxe Macro"
    )
    public static boolean royalPigeonMacro = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto Clicker"
    )
    public static boolean autoClicker = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "CPS",
                parent = "Auto Clicker",
                min = 10,
                max = 500,
                step = 10,
                suffix = " CPS"
        )
        public static int autoClickerCps = 100;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Toggle Continous",
                parent = "Auto Clicker",
                boundTo = "Trigger Burst"
        )
        public static boolean autoClickerToggle = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Show Indicator",
                    parent = "Toggle Continous"
            )
            public static boolean autoClickerIndicator = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Stop in GUI",
                    parent = "Toggle Continous"
            )
            public static boolean stopAutoClickerInGui = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Trigger Burst",
                parent = "Auto Clicker",
                boundTo = "Toggle Continous"
        )
        public static boolean autoClickerBurst = false;

            @Property(
                    type = Property.Type.NUMBER,
                    name = "Amount",
                    parent = "Trigger Burst",
                    min = 5,
                    max = 100,
                    step = 5,
                    suffix = " clicks"
            )
            public static int burstAmount = 25;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Corleone Finder"
    )
    public static boolean corleoneFinder = false;


    @Property(
            type = Property.Type.BOOLEAN,
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
                parent = "Summons Features"
        )
        public static boolean clickThroughSummons = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Teleport w/ Anything"
    )
    public static boolean teleportWithAnything = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto Renew Crystal Hollows Pass"
    )
    public static boolean renewCrystalHollowsPass = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Disable Block Animation"
    )
    public static boolean disableBlockAnimation = false;



    @Property(
            type = Property.Type.BOOLEAN,
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
                type = Property.Type.BOOLEAN,
                name = "Item Hotkeys",
                parent = "Keybinds"
        )
        public static boolean itemHotkeys = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Ice Spray",
                    parent = "Item Hotkeys"
            )
            public static boolean iceSprayHotkey = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Rogue Sword",
                    parent = "Item Hotkeys"
            )
            public static boolean rogueSwordHotkey = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Power Orb",
                    parent = "Item Hotkeys"
            )
            public static boolean powerOrbHotkey = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Weird Tuba",
                    parent = "Item Hotkeys"
            )
            public static boolean weirdTubaHotkey = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Gyrokinetic Wand",
                    parent = "Item Hotkeys"
            )
            public static boolean gyrokineticWandHotkey = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Pigman Sword",
                    parent = "Item Hotkeys"
            )
            public static boolean pigmanSwordHotkey = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Healing Wand",
                    parent = "Item Hotkeys"
            )
            public static boolean healingWandHotkey = false;

            @Property(
                    type = Property.Type.BOOLEAN,
                    name = "Fishing Rod",
                    parent = "Item Hotkeys"
            )
            public static boolean fishingRodHotkey = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Block Abilities"
    )
    public static boolean blockAbilitis = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Cells Alignment",
                parent = "Block Abilities"
        )
        public static boolean blockCellsAlignment = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Giant's Slam",
                parent = "Block Abilities"
        )
        public static boolean blockGiantsSlam = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Valkyrie Wither Impact",
                parent = "Block Abilities"
        )
        public static boolean blockValkyrie = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Dungeon Map",
            credit = "ported from IllegalMap by UnclaimedBloom6"
    )
    public static boolean dungeonMap = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Darken Unexplored",
                parent = "Dungeon Map"
        )
        public static boolean darkenUnexplored = false;

        @Property(
                type = Property.Type.NUMBER,
                name = "Background Opacity",
                parent = "Dungeon Map",
                max = 100,
                step = 10,
                suffix = "%"
        )
        public static int mapBackgroundOpacity = 50;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "RBG Border",
                parent = "Dungeon Map"
        )
        public static boolean rbgMapBorder = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Show Player Heads",
                parent = "Dungeon Map"
        )
        public static boolean showPlayerHeads = false;

        @Property(
                type = Property.Type.SELECT,
                name = "Checkmark Style",
                parent = "Dungeon Map",
                options = {"None", "Vanilla", "Fancy"}
        )
        public static int mapCheckmarkStyle = 0;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto Close Chests"
    )
    public static boolean closeChests = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Secret Chests",
                parent = "Auto Close Chests"
        )
        public static boolean closeSecretChests = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Crystal Hollows Chests",
                parent = "Auto Close Chests"
        )
        public static boolean closeCrystalHollowsChests = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto GG"
    )
    public static boolean autoGg = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Show Hidden Mobs"
    )
    public static boolean showHiddenMobs = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Shadow Assassins",
                parent = "Show Hidden Mobs"
        )
        public static boolean showHiddenShadowAssassins = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Fels",
                parent = "Show Hidden Mobs"
        )
        public static boolean showHiddenFels = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Ghosts",
                parent = "Show Hidden Mobs"
        )
        public static boolean showGhosts = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Stealthy Blood Mobs",
                parent = "Show Hidden Mobs"
        )
        public static boolean showStealthyBloodMobs = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Highlight Starred Mobs & Minibosses"
    )
    public static boolean highlightStarredMobs = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Gemstone ESP"
    )
    public static boolean gemstoneEsp = false;

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
                type = Property.Type.BOOLEAN,
                name = "Ruby",
                parent = "Gemstone ESP"
        )
        public static boolean rubyEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Amber",
                parent = "Gemstone ESP"
        )
        public static boolean amberEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Sapphire",
                parent = "Gemstone ESP"
        )
        public static boolean sapphireEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Jade",
                parent = "Gemstone ESP"
        )
        public static boolean jadeEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Amethyst",
                parent = "Gemstone ESP"
        )
        public static boolean amethystEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Topaz",
                parent = "Gemstone ESP"
        )
        public static boolean topazEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Jasper",
                parent = "Gemstone ESP"
        )
        public static boolean jasperEsp = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto Melody"
    )
    public static boolean autoMelody = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto Terminals",
            credit = "by 0Kelvin_"
    )
    public static boolean autoTerminals = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Maze",
                parent = "Auto Terminals"
        )
        public static boolean autoMaze = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Click in Order",
                parent = "Auto Terminals"
        )
        public static boolean autoNumbers = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Correct the Panes",
                parent = "Auto Terminals"
        )
        public static boolean autoCorrectAll = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Name Starts With",
                parent = "Auto Terminals"
        )
        public static boolean autoLetter = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Color",
                parent = "Auto Terminals"
        )
        public static boolean autoColor = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Pingless",
                parent = "Auto Terminals"
        )
        public static boolean terminalPingless = false;

        @Property(
                type = Property.Type.NUMBER,
                name = "Click Delay",
                parent = "Auto Terminals",
                step =  10,
                suffix = "ms",
                min = 50,
                max = 1000
        )
        public static int terminalClickDelay = 100;

}