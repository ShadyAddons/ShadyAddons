package shady.shady.shady.config;

public enum Setting {
    OPEN_CHESTS_THROUGH_WALLS(
            "Open Chests Through Walls",
            "Sneak to highlight nearby chests, right-click to open them",
            false
    ),
    AUTO_SIMON_SAYS(
            "Auto Simon Says",
            "Automatically clicks Simon Says buttons five times",
            false
    ),
    BLOCK_CELLS_ALIGNMENT(
            "Block Cells Alignment",
            "Prevents you from accidentally using the Cells Alignment ability",
            false
    ),
    CLOSE_SECRET_CHESTS(
            "Close Secret Chests",
            "Automatically closes secret chests in Dungeons",
            false
    ),
    ROYAL_PIGEON_PICKAXE_MACRO(
            "Royal Pigeon Pickaxe Macro",
            "Swaps to the first Refined item in your hotbar when you open the commissions menu",
            false
    ),
    CLOSE_CRYSTAL_HOLLOWS_CHESTS(
            "Close Crystal Hollows Chests",
            "Automatically closes loot and treasure chests in the Crystal Hollows",
            false
    ),
    GHOST_BLOCK_KEYBIND(
            "Ghost Block Keybind",
            "Toggles the ghost block keybind, which defaults to G and can be changed in the controls menu",
            false
    ),
    NORMAL_ABILITY_KEYBIND(
            "Normal Ability Keybind",
            "Toggles the normal ability keybind, which defaults to X and can be changed in the controls menu",
            false
    ),
    SPAM_RIGHT_CLICK(
            "Spam Right-Click",
            "Toggles the auto-right-clicker keybind, which rapidly clicks 25 times, defaults to Y, and can be changed in the controls menu",
            false
    ),
    BOSS_CORLEONE_FINDER(
            "Boss Corleone Finder",
            "Notifies you with the position of Boss Corleone and draws a waypoint at its location",
            false
    ),
    AUTO_GG(
            "Auto GG",
            null,
            false
    ),
    DISABLE_BLOCK_ANIMATION(
            "Disable Sword Block Animation",
            "Disables the blocking animation on Hyperion, Valkyrie, Scylla, Astraea, Rogue Sword, and Aspect of the End",
            false
    );

    public String name;
    public boolean defaultValue;
    public String description;
    Setting(String name, String description, boolean defaultValue) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }
}