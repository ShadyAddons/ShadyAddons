package cheaters.get.banned.config;

public class Config {

    @Boolean("Stonkless Stonk")
    public static boolean stonklessStonk = false;

        @Boolean(value = "Always On", parent = "Stonkless Stonk")
        public static boolean alwaysOn = false;


    @Boolean("Auto Simon-Says")
    public static boolean autoSimonSays = false;


    @Boolean("Royal Pigeon Pickaxe Macro")
    public static boolean royalPigeonMacro = false;


    @Boolean("Auto Clicker")
    public static boolean autoClicker = false;

        @Number(value = "CPS", parent = "Auto Clicker", min = 10, max = 500, suffix = " CPS", step = 10)
        public static int autoClickerCps = 100;

        @Boolean(value = "Toggle Continous", parent = "Auto Clicker", boundTo = "Trigger Burst")
        public static boolean autoClickerToggle = false;

            @Boolean(value = "Show Indicator", parent = "Toggle Continous")
            public static boolean autoClickerIndicator = false;

        @Boolean(value = "Trigger Burst", parent = "Auto Clicker", boundTo = "Toggle Continous")
        public static boolean autoClickerBurst = false;

            @Number(value = "Amount", parent = "Trigger Burst", min = 5, max = 100, step = 5, suffix = " clicks")
            public static int burstAmount = 25;


    @Boolean("Corleone Finder")
    public static boolean corleoneFinder = false;


    @Boolean("Summons Features")
    public static boolean summonsFeatures = false;

        @Boolean(value = "Hide Summons", parent = "Summons Features")
        public static boolean hideSummons = false;

        @Boolean(value = "Click Through Summons", parent = "Summons Features")
        public static boolean clickThroughSummons = false;


    @Boolean("Teleport w/ Anything")
    public static boolean teleportWithAnything = false;


    @Boolean("Renew Crystal Hollows Pass")
    public static boolean renewCrystalHollowsPass = false;


    @Boolean("Disable Block Animation")
    public static boolean disableBlockAnimation = false;


    @Boolean("Keybinds")
    public static boolean keybinds = false;

        @Boolean(value = "Ghost Blocks", parent = "Keybinds")
        public static boolean ghostBlockKeybind = false;

            @Boolean(value = "Right-Click w/ Stonk", parent = "Ghost Blocks")
            public static boolean stonkGhostBlock = false;

        @Boolean(value = "Normal Ability", parent = "Keybinds")
        public static boolean normalAbilityKeybind = false;

        @Boolean(value = "Item Hotkeys", parent = "Keybinds")
        public static boolean itemHotkeys = false;

            @Boolean(value = "Ice Spray", parent = "Item Hotkeys")
            public static boolean iceSprayHotkey = false;

            @Boolean(value = "Rogue Sword", parent = "Item Hotkeys")
            public static boolean rogueSwordHotkey = false;

            @Boolean(value = "Power Orb", parent = "Item Hotkeys")
            public static boolean powerOrbHotkey = false;

            @Boolean(value = "Weird Tuba", parent = "Item Hotkeys")
            public static boolean weirdTubaHotkey = false;

            @Boolean(value = "Gyrokinetic Wand", parent = "Item Hotkeys")
            public static boolean gyrokineticWandHotkey = false;

            @Boolean(value = "Pigman Sword", parent = "Item Hotkeys")
            public static boolean pigmanSwordHotkey = false;

            @Boolean(value = "Healing Wand", parent = "Item Hotkeys")
            public static boolean healingWandHotkey = false;

            @Boolean(value = "Fishing Rod", parent = "Item Hotkeys")
            public static boolean fishingRodHotkey = false;


    @Boolean("Block Abilities")
    public static boolean blockAbilitis = false;

        @Boolean(value = "Cells Alignment", parent = "Block Abilities")
        public static boolean blockCellsAlignment = false;

        @Boolean(value = "Giant's Slam", parent = "Block Abilities")
        public static boolean blockGiantsSlam = false;

        @Boolean(value = "Valkyrie Wither Impact", parent = "Block Abilities")
        public static boolean blockValkyrie = false;


    @Boolean("Automatically Close Chests")
    public static boolean closeChests = false;

        @Boolean(value = "Secret Chests", parent = "Automatically Close Chests")
        public static boolean closeSecretChests = false;

        @Boolean(value = "Crystal Hollows Chests", parent = "Automatically Close Chests")
        public static boolean closeCrystalHollowsChests = false;


    @Boolean("Auto GG")
    public static boolean autoGg = false;


    @Boolean("Show Hidden Mobs")
    public static boolean showHiddenMobs = false;

        @Boolean(value = "Shadow Assassins", parent = "Show Hidden Mobs")
        public static boolean showHiddenShadowAssassins = false;

        @Boolean(value = "Fels", parent = "Show Hidden Mobs")
        public static boolean showHiddenFels = false;

        @Boolean(value = "Ghosts", parent = "Show Hidden Mobs")
        public static boolean showGhosts = false;

        @Boolean(value = "Stealthy Blood Mobs", parent = "Show Hidden Mobs")
        public static boolean showStealthyBloodMobs = false;


    // @Boolean("Highlight Starred Mobs")
    public static boolean highlightStarredMobs = false;


    @Boolean("Gemstone ESP")
    public static boolean gemstoneEsp = false;

        @Number(value = "Scan Radius", parent = "Gemstone ESP", suffix = " blocks", min = 5, max = 30)
        public static int gemstoneRadius = 15;

        @Boolean(value = "Ruby", parent = "Gemstone ESP")
        public static boolean rubyEsp = false;

        @Boolean(value = "Amber", parent = "Gemstone ESP")
        public static boolean amberEsp = false;

        @Boolean(value = "Sapphire", parent = "Gemstone ESP")
        public static boolean sapphireEsp = false;

        @Boolean(value = "Jade", parent = "Gemstone ESP")
        public static boolean jadeEsp = false;

        @Boolean(value = "Amethyst", parent = "Gemstone ESP")
        public static boolean amethystEsp = false;

        @Boolean(value = "Topaz", parent = "Gemstone ESP")
        public static boolean topazEsp = false;

        @Boolean(value = "Jasper", parent = "Gemstone ESP")
        public static boolean jasperEsp = false;


    @Boolean("Auto Melody")
    public static boolean autoMelody = false;

    @Boolean("Auto Terminals")
    public static boolean autoTerminals = false;

        @Boolean(value = "Maze", parent = "Auto Terminals")
        public static boolean autoMaze = false;

        @Boolean(value = "Click in Order", parent = "Auto Terminals")
        public static boolean autoNumbers = false;

        @Boolean(value = "Correct the Panes", parent = "Auto Terminals")
        public static boolean autoCorrectAll = false;

        @Boolean(value = "Name Starts With", parent = "Auto Terminals")
        public static boolean autoLetter = false;

        @Boolean(value = "Color", parent = "Auto Terminals")
        public static boolean autoColor = false;

        @Boolean(value = "Pingless", parent = "Auto Terminals")
        public static boolean terminalPingless = false;

        @Number(value = "Click Delay", parent = "Auto Terminals", step = 10, suffix = "ms", min = 50, max = 5000)
        public static int terminalClickDelay = 100;

}