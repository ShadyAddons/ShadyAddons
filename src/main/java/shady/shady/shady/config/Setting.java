package shady.shady.shady.config;

public enum Setting {
    OPEN_CHESTS_THROUGH_WALLS(
            "Open Chests Through Walls",
            "Sneak to highlight nearby chests, right-click to open them"
    ),
    AUTO_SIMON_SAYS(
            "Auto Simon Says",
            "Automatically clicks Simon Says buttons five times"
    ),
    BLOCK_CELLS_ALIGNMENT(
            "Block Cells Alignment",
            "Prevents you from accidentally using the Cells Alignment ability"
    ),
    CLOSE_SECRET_CHESTS(
            "Close Secret Chests",
            "Automatically closes secret chests in Dungeons"
    ),
    ROYAL_PIGEON_PICKAXE_MACRO(
            "Royal Pigeon Pickaxe Macro",
            "Swaps to the first Refined item in your hotbar when you open the commissions menu"
    ),
    CLOSE_CRYSTAL_HOLLOWS_CHESTS(
            "Close Crystal Hollows Chests",
            "Automatically closes loot and treasure chests in the Crystal Hollows"
    ),
    GHOST_BLOCK_KEYBIND(
            "Ghost Block Keybind",
            "Toggles the ghost block keybind, which defaults to G and can be changed in the controls menu"
    ),
    NORMAL_ABILITY_KEYBIND(
            "Normal Ability Keybind",
            "Toggles the normal ability keybind, which defaults to X and can be changed in the controls menu"
    ),
    SPAM_RIGHT_CLICK(
            "Spam Right-Click",
            "Toggles the auto-right-clicker keybind, which rapidly clicks 25 times, defaults to Y, and can be changed in the controls menu"
    ),
    BOSS_CORLEONE_FINDER(
            "Boss Corleone Finder",
            "Notifies you with the position of Boss Corleone and draws a waypoint at its location"
    ),
    AUTO_GG(
            "Auto GG",
            null
    ),
    AUTO_RENEW_CRYSTAL_HOLLOWS_PASS(
            "Renew Crystal Hollows Pass",
            "Automatically renews your Crystal Hollows pass for 10,000 coins"
    ),
    DISABLE_BLOCK_ANIMATION(
            "Disable Blocking Animation",
            "Disables the blocking animation on Hyperion, Valkyrie, Scylla, Astraea, Rogue Sword, and Aspect of the End"
    );

    public String name;
    public String description;
    Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }
}