package shady.shady.shady.config;

public enum Setting {
    STONKLESS_STONK(
            "Stonkless Stonk",
            "(Sometimes called Ghost Hands) Sneak to highlight nearby chests, levers, and wither essence. Right-click to open, use, or collect them. Chests are white, trapped chests are red, essence is black, and levers are gray."
    ),
    AUTO_SIMON_SAYS(
            "Auto Simon Says",
            "Automatically clicks Simon Says buttons five times."
    ),
    BLOCK_CELLS_ALIGNMENT(
            "Block Cells Alignment",
            "Prevents you from using the Cells Alignment ability on the §5Gyrokinitic§r §5Wand§r."
    ),
    BLOCK_GIANTS_SLAM(
            "Block Giant's Slam",
            "Prevents you from using the Giant's Slam ability on the §6Giant's§r §7Sword§r."
    ),
    CLOSE_SECRET_CHESTS(
            "Close Secret Chests",
            "Automatically closes secret chests in §eDungeons§r."
    ),
    ROYAL_PIGEON_PICKAXE_MACRO(
            "Royal Pigeon Pickaxe Macro",
            "Swaps to the first §aRefined§r item in your hotbar when you open the commissions menu."
    ),
    CLOSE_CRYSTAL_HOLLOWS_CHESTS(
            "Close Crystal Hollows Chests",
            "Automatically closes loot and treasure chests in the §eCrystal§r §eHollows§r."
    ),
    GHOST_BLOCK_KEYBIND(
            "Ghost Block Keybind",
            "Toggles the ghost block keybind, which defaults to G and can be changed in the controls menu."
    ),
    NORMAL_ABILITY_KEYBIND(
            "Normal Ability Keybind",
            "Toggles the normal ability keybind, which defaults to X and can be changed in the controls menu."
    ),
    SPAM_RIGHT_CLICK(
            "Spam Right-Click",
            "Toggles the auto-right-clicker keybind, which rapidly clicks §a25§r times, defaults to Y, and can be changed in the controls menu."
    ),
    BOSS_CORLEONE_FINDER(
            "Boss Corleone Finder",
            "Notifies you with the position of §cBoss§r §cCorleone§r and draws a waypoint at its location."
    ),
    AUTO_GG(
            "Auto GG",
            null
    ),
    AUTO_RENEW_CRYSTAL_HOLLOWS_PASS(
            "Renew Crystal Hollows Pass",
            "Automatically renews your §eCrystal§r §eHollows§r pass when it expires for §610,000 coins§r."
    ),
    DISABLE_BLOCK_ANIMATION(
            "Disable Blocking Animation",
            "Disables the blocking animation on §6Hyperion§r, §6Valkyrie§r, §6Scylla§r, §6Astraea§r, §fRogue Sword§r, and §9Aspect§r §9of§r §9the§r End§r."
    ),
    SHOW_HIDDEN_SHADOW_ASSASSINS(
            "Show Hidden Shadow Assassins",
            null
    ),
    SHOW_HIDDEN_FELS(
            "Show Hidden Fels",
            null
    ),
    SHOW_HIDDEN_GHOSTS(
            "Show Hidden Ghosts",
            "Reveals the invisible charged creepers in the §aDwarven§r §aMines§r."
    ),
    SHOW_STEALTHY_BLOOD_MOBS(
            "Show Stealthy Blood Mobs",
            null
    ),
    CLICK_THROUGH_SUMMONS(
            "Click Through Summons",
            "Click through your summoned mobs. Works for all Phoenix-skin souls and most undead souls from dungeons. Doesn't work in dungeons."
    ),
    HIDE_SUMMONS(
            "Hide Nearby Summons",
            "Hides summons within §a5§r blocks of you. Pairs nicely with §3Click§r §3Through§r §3Summons§r. Doesn't work in dungeons."
    ),
    TELEPORT_WITH_ANYTHING(
            "Teleport w/ Anything",
            "Use the item in your first slot as an §9Aspect§r §9of§r §9the§r §9End§r. Must have an AOTE/AOTV somewhere in your hotbar. Useful for foraging with a §6Treecapitator§r or grinding with a §6Daedelus§r §6Axe§r in a max magic find setup."
    ),
    BLOCK_VALKYRIE_ABILITY(
            "Block Valkyrie Ability",
            "Blocks §6Valkyrie§r's Wither Impact ability while in §eDungeons§r."
    ),
    ICE_SPRAY_HOTKEY(
            "Ice Spray Hotkey",
            "Uses your §9Ice§r §9Spray§r §9Wand§r without having to switch items. Defaults to I, can be changed in the controls menu."
    );

    public String name;
    public String description;
    Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }
}