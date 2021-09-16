package cheaters.get.banned.configuration;

public class Config {

    @Property("Stonkless Stonk")
    public static boolean stonklessStonk = false;

        @Property(value = "Always On", parent = "Stonkless Stonk")
        public static boolean alwaysOn = false;


    @Property("Auto Simon-Says")
    public static boolean autoSimonSays = false;


    @Property("Royal Pigeon Pickaxe Macro")
    public static boolean royalPigeonMacro = false;


    @Property("Auto Clicker")
    public static boolean autoClicker = false;

        @Property(value = "Toggle Continous", parent = "Auto Clicker", boundTo = "Trigger Burst")
        public static boolean autoClickerToggle = false;

        @Property(value = "Trigger Burst", parent = "Auto Clicker", boundTo = "Toggle Continous")
        public static boolean autoClickerBurst = false;


    @Property("Corleone Finder")
    public static boolean corleoneFinder = false;


    @Property("Summons Features")
    public static boolean summonsFeatures = false;

        @Property(value = "Hide Summons", parent = "Summons Features")
        public static boolean hideSummons = false;

        @Property(value = "Click Through Summons", parent = "Summons Features")
        public static boolean clickThroughSummons = false;


    @Property("Teleport w/ Anything")
    public static boolean teleportWithAnything = false;


    @Property("Renew Crystal Hollows Pass")
    public static boolean renewCrystalHollowsPass = false;


    @Property("Disable Block Animation")
    public static boolean disableBlockAnimation = false;


    @Property("Keybinds")
    public static boolean keybinds = false;

        @Property(value = "Ghost Blocks", parent = "Keybinds")
        public static boolean ghostBlockKeybind = false;

            @Property(value = "Right-Click w/ Stonk", parent = "Ghost Blocks")
            public static boolean stonkGhostBlock = false;

        @Property(value = "Normal Ability", parent = "Keybinds")
        public static boolean normalAbilityKeybind = false;

        @Property(value = "Ice Spray Hotkey (I)", parent = "Keybinds")
        public static boolean iceSprayHotkey = false;


    @Property("Block Abilities")
    public static boolean blockAbilitis = false;

        @Property(value = "Cells Alignment", parent = "Block Abilities")
        public static boolean blockCellsAlignment = false;

        @Property(value = "Giant's Slam", parent = "Block Abilities")
        public static boolean blockGiantsSlam = false;

        @Property(value = "Valkyrie Wither Impact", parent = "Block Abilities")
        public static boolean blockValkyrie = false;


    @Property("Automatically Close Chests")
    public static boolean closeChests = false;

        @Property(value = "Secret Chests", parent = "Automatically Close Chests")
        public static boolean closeSecretChests = false;

        @Property(value = "Crystal Hollows Chests", parent = "Automatically Close Chests")
        public static boolean closeCrystalHollowsChests = false;


    @Property("Auto GG")
    public static boolean autoGg = false;


    @Property("Show Hidden Mobs")
    public static boolean showHiddenMobs = false;

        @Property(value = "Shadow Assassins", parent = "Show Hidden Mobs")
        public static boolean showHiddenShadowAssassins = false;

        @Property(value = "Fels", parent = "Show Hidden Mobs")
        public static boolean showHiddenFels = false;

        @Property(value = "Ghosts", parent = "Show Hidden Mobs")
        public static boolean showGhosts = false;

        @Property(value = "Stealthy Blood Mobs", parent = "Show Hidden Mobs")
        public static boolean showStealthyBloodMobs = false;

}