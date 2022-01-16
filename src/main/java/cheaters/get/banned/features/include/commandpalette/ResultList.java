package cheaters.get.banned.features.include.commandpalette;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.features.include.commandpalette.actions.CommandAction;
import cheaters.get.banned.features.include.commandpalette.actions.RunnableAction;
import cheaters.get.banned.features.include.commandpalette.icons.ImageIcon;
import cheaters.get.banned.features.include.commandpalette.icons.ItemIcon;
import cheaters.get.banned.utils.ExpressionParser;
import cheaters.get.banned.utils.ItemUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultList {

    private static LinkedHashMap<String, Result> results = new LinkedHashMap<>();

    public static void generateList() {
        results.clear();

        /*if(Updater.update != null && !Updater.update.version.equals(Shady.VERSION)) {
            results.put("update shady cheatersgetbanned.me",
                    new Result(
                            "Update ShadyAddons to " + Updater.update.version,
                            new ImageIcon("chester.png"),
                            new RunnableAction(() -> Utils.openUrl("https://cheatersgetbanned.me/?update=true"))
                    )
            );
        }*/

        if(!Utils.inSkyBlock) {
            results.put("join skyblock sb",
                    new Result(
                            "Join SkyBlock",
                            new ItemIcon(ItemUtils.getSkullItemStack("f26a2360-0158-4f76-8a34-f487883f2b04", "c9c8881e42915a9d29bb61a16fb26d059913204d265df5b439b3d792acd56")),
                            new CommandAction("/skyblock")
                    )
            );
        }

        results.put("drip shady merch store shop",
                new Result(
                        "Buy ShadyAddons Merch",
                        new ImageIcon("drip.png"),
                        new RunnableAction(() -> Utils.openUrl("https://cheatersgetbanned.me/store")),
                        "via Teespring"
                )
        );

        results.put("shadyaddons bug report",
                new Result(
                        "Report a Bug",
                        new ImageIcon("chester.png"),
                        new RunnableAction(() -> Utils.openUrl("https://cheatersgetbanned.me/bug-report/?version=" + Shady.VERSION))
                )
        );

        results.put("controls keybinds",
                new Result(
                        "Open Minecraft Controls",
                        new ItemIcon(new ItemStack(Blocks.grass)),
                        new RunnableAction(() -> Shady.guiToOpen = new GuiControls(null, Shady.mc.gameSettings))
                )
        );

        if(Utils.inSkyBlock) {
            results.put("/ah auction house claim auctions flip cookie",
                    new Result(
                            "Auction House",
                            new ItemIcon(ItemUtils.getSkullItemStack("04049c90-d3e9-4621-9caf-00000aaa3038", "452dca68c8f8af533fb737faeeacbe717b968767fc18824dc2d37ac789fc77")),
                            new CommandAction("/ah")
                    )
            );

            results.put("/bz bazaar flip cookie",
                    new Result(
                            "Bazaar",
                            new ItemIcon(ItemUtils.getSkullItemStack("41d3abc2-d749-400c-9090-d5434d03831b", "c232e3820897429157619b0ee099fec0628f602fff12b695de54aef11d923ad7")),
                            new CommandAction("/bz")
                    )
            );

            results.put("anvil /av cookie",
                    new Result(
                            "Anvil",
                            new ItemIcon(new ItemStack(Blocks.anvil)),
                            new CommandAction("/av")
                    )
            );

            results.put("enchanting table enchantment /et cookie",
                    new Result(
                            "Enchanting Table",
                            new ItemIcon(new ItemStack(Blocks.enchanting_table)),
                            new CommandAction("/et")
                    )
            );

            results.put("island home warp",
                    new Result(
                            "Warp: Island",
                            new ItemIcon(ItemUtils.getSkullByName(Shady.mc.getSession().getUsername())),
                            new CommandAction("/warp home")
                    )
            );

            results.put("hub warp griffin burrow",
                    new Result(
                            "Warp: Hub",
                            new ItemIcon(ItemUtils.getSkullItemStack("f26a2360-0158-4f76-8a34-f487883f2b04", "c9c8881e42915a9d29bb61a16fb26d059913204d265df5b439b3d792acd56")),
                            new CommandAction("/warp hub")
                    )
            );

            results.put("dungeon hub dh warp",
                    new Result(
                            "Warp: Dungeon Hub",
                            new ItemIcon(ItemUtils.getSkullItemStack("3549f087-6655-4e1b-9b71-ecc1c59e59b7", "e27372809b5694646f44d7a837d4fe66e5ef62ae72701171651b3a780cb1f9c")),
                            new CommandAction("/warp dungeon_hub")
                    )
            );

            results.put("mines dwarven warp",
                    new Result(
                            "Warp: Dwarven Mines",
                            new ItemIcon(ItemUtils.getSkullItemStack("61ebeb8a-d9a9-4b5e-ac3e-cbc9eeee7d46", "172850906b7f0d952c0e508073cc439fd3374ccf5b889c06f7e8d90cc0cc255c")),
                            new CommandAction("/warp mines")
                    )
            );

            results.put("crystal hollows mines warp gemstone",
                    new Result(
                            "Warp: Crystal Hollows",
                            new ItemIcon(ItemUtils.getSkullItemStack("2b00f544-854e-48c1-86ab-210e03e34edd", "21dbe30b027acbceb612563bd877cd7ebb719ea6ed1399027dcee58bb9049d4a")),
                            new CommandAction("/warp crystals")
                    )
            );

            results.put("da dark auction warp griffin burrow",
                    new Result(
                            "Warp: Dark Auction",
                            new ItemIcon(ItemUtils.getSkullItemStack("59ae04a3-7995-440e-b16f-10e4d87cf63d", "7ab83858ebc8ee85c3e54ab13aabfcc1ef2ad446d6a900e471c3f33b78906a5b")),
                            new CommandAction("/warp da")
                    )
            );

            results.put("museum high level higher level warp griffin burrow",
                    new Result(
                            "Warp: Museum",
                            new ItemIcon(new ItemStack(Items.painting)),
                            new CommandAction("/warp museum")
                    )
            );

            results.put("forge dwarven mines warp",
                    new Result(
                            "Warp: Dwarven Forge",
                            new ItemIcon(new ItemStack(Items.lava_bucket)),
                            new CommandAction("/warp mines")
                    )
            );

            results.put("park trees foraging forest warp",
                    new Result(
                            "Warp: The Park",
                            new ItemIcon(new ItemStack(Blocks.log)),
                            new CommandAction("/warp park")
                    )
            );

            results.put("sven howling caves slayer park foraging pond warp",
                    new Result(
                            "Warp: Howling Cave",
                            new ItemIcon(ItemUtils.getSkullItemStack("a3585839-876f-4c6d-bd25-b5a4750d428b", "adc6429cfabacf211dd3db26c5ca7b5942dd82599fbb1d537cf72e4952e2c7b")),
                            new CommandAction("/warp howl")
                    )
            );

            results.put("crypt zombie slayer hub warp",
                    new Result(
                            "Warp: Hub Crypts",
                            new ItemIcon(new ItemStack(Items.rotten_flesh)),
                            new CommandAction("/warp crypt")
                    )
            );

            results.put("blazing fortress nether magma boss warp",
                    new Result(
                            "Warp: Blazing Fortress",
                            new ItemIcon(new ItemStack(Blocks.netherrack)),
                            new CommandAction("/warp fortress")
                    )
            );

            results.put("magma fields blazing fortress nether magma boss warp",
                    new Result(
                            "Warp: Magma Fields",
                            new ItemIcon(new ItemStack(Items.magma_cream)),
                            new CommandAction("/warp magma")
                    )
            );

            results.put("deep caverns mine warp",
                    new Result(
                            "Warp: Deep Caverns",
                            new ItemIcon(new ItemStack(Blocks.diamond_ore)),
                            new CommandAction("/warp deep")
                    )
            );

            results.put("rusty gold mine warp",
                    new Result(
                            "Warp: Gold Mine",
                            new ItemIcon(new ItemStack(Items.gold_ingot)),
                            new CommandAction("/warp gold")
                    )
            );

            results.put("spider's den spiders den spider den spider slayer pond fishing warp",
                    new Result(
                            "Warp: Spider's Den",
                            new ItemIcon(new ItemStack(Items.string)),
                            new CommandAction("/warp spider")
                    )
            );

            results.put("top of nest spider's den spiders den spider den spider slayer warp",
                    new Result(
                            "Warp: Top of Nest",
                            new ItemIcon(new ItemStack(Items.spider_eye)),
                            new CommandAction("/warp nest")
                    )
            );

            results.put("barn farming islands fishing warp",
                    new Result(
                            "Warp: The Barn",
                            new ItemIcon(new ItemStack(Items.carrot)),
                            new CommandAction("/warp barn")
                    )
            );

            results.put("mushroom desert trapper jake farming islands warp",
                    new Result(
                            "Warp: Mushroom Desert",
                            new ItemIcon(new ItemStack(Blocks.red_mushroom)),
                            new CommandAction("/warp desert")
                    )
            );

            results.put("castle hub warp griffin burrows philosopher lonely",
                    new Result(
                            "Warp: Hub Castle",
                            new ItemIcon(ItemUtils.getSkullItemStack("bcccae77-0ac7-4cd0-8126-c900727c2223", "49c1832e4ef5c4ad9c519d194b1985030d257914334aaf2745c9dfd611d6d61d")),
                            new CommandAction("/warp castle")
                    )
            );

            results.put("jerry fishing winter island yeti christmas workshop warp",
                    new Result(
                            "Warp: Jerry's Workshop",
                            new ItemIcon(ItemUtils.getSkullItemStack("0f7e2dc4-3319-41d6-85dc-cc7a9bae89bb", "ab126814fc3fa846dad934c349628a7a1de5b415021a03ef4211d62514d5")),
                            new CommandAction("/savethejerrys")
                    )
            );

            results.put("dragon end warp",
                    new Result(
                            "Warp: Dragon's Nest",
                            new ItemIcon(ItemUtils.getSkullItemStack("36122cdc-6c97-4b97-990a-ef4df57db922", "daa8fc8de6417b48d48c80b443cf5326e3d9da4dbe9b25fcd49549d96168fc0")),
                            new CommandAction("/warp drag"),
                            "Rawr! Fight dragons!"
                    )
            );

            results.put("enderman sepulture void slayer end warp",
                    new Result(
                            "Warp: Void Sepulture",
                            new ItemIcon(ItemUtils.getSkullItemStack("d9c0c394-598f-49fe-a9cf-e15e779da667", "eb07594e2df273921a77c101d0bfdfa1115abed5b9b2029eb496ceba9bdbb4b3")),
                            new CommandAction("/warp void")
                    )
            );

            results.put("the end enderman dragon pearl warp",
                    new Result(
                            "Warp: The End",
                            new ItemIcon(new ItemStack(Items.ender_eye)),
                            new CommandAction("/warp end")
                    )
            );

            results.put("jungle island romero romeo juliette quest the park warp",
                    new Result(
                            "Warp: Jungle Island",
                            new ItemIcon(ItemUtils.getSkullItemStack("41d3abc2-d749-400c-9090-d5434d03831b", "b3351f22f63b43f40fa8e28def66b73b17ba265773fe9980971da2f1515032d9")),
                            new CommandAction("/warp jungle")
                    )
            );

            results.put("f7 floor 7 dungeon join",
                    new Result(
                            "Join Dungeon: F7",
                            new ImageIcon("f7.png"),
                            new CommandAction("/joindungeon catacombs 7")
                    )
            );

            results.put("m3 master floor 3 dungeon join",
                    new Result(
                            "Join Dungeon: M3",
                            new ImageIcon("m3.png"),
                            new CommandAction("/joindungeon master_catacombs 3")
                    )
            );

            results.put("m4 master floor 4 dungeon join",
                    new Result(
                            "Join Dungeon: M4",
                            new ImageIcon("m4.png"),
                            new CommandAction("/joindungeon master_catacombs 4")
                    )
            );

            results.put("m5 master floor 5 dungeon join",
                    new Result(
                            "Join Dungeon: M5",
                            new ImageIcon("m5.png"),
                            new CommandAction("/joindungeon master_catacombs 5")
                    )
            );

            results.put("m6 master floor 6 dungeon join",
                    new Result(
                            "Join Dungeon: M6",
                            new ImageIcon("m6.png"),
                            new CommandAction("/joindungeon master_catacombs 6")
                    )
            );

            results.put("pets",
                    new Result(
                            "Open Menu: Pets",
                            new ItemIcon(new ItemStack(Items.bone)),
                            new CommandAction("/pets")
                    )
            );

            results.put("sacks",
                    new Result(
                            "Open Menu: Sacks",
                            new ItemIcon(new ItemStack(Items.bone)),
                            new CommandAction("/sacks")
                    )
            );

            results.put("profile switcher manager",
                    new Result(
                            "Open Menu: Profile Switcher",
                            new ItemIcon(new ItemStack(Items.name_tag)),
                            new CommandAction("/profiles")
                    )
            );
        }

        results.put("toggle enable disable aots with anything axe of the shredded",
                new Result(
                        (Config.aotsWithAnything ? "Disable" : "Enable") + ": AOTS w/ Anything",
                        new ItemIcon(new ItemStack(Items.diamond_axe)),
                        new RunnableAction(() -> Config.aotsWithAnything = !Config.aotsWithAnything)
                )
        );

        results.put("toggle enable disable teleport with anything aspect of the end aspect of the end aotv aote",
                new Result(
                        (Config.teleportWithAnything ? "Disable" : "Enable") + ": Teleport w/ Anything",
                        new ItemIcon(new ItemStack(Items.diamond_sword)),
                        new RunnableAction(() -> Config.teleportWithAnything = !Config.teleportWithAnything)
                )
        );

        results.put("toggle enable disable soul whip with anything",
                new Result(
                        (Config.soulWhipWithAnything ? "Disable" : "Enable") + ": Soul Whip w/ Anything",
                        new ItemIcon(new ItemStack(Items.fishing_rod)),
                        new RunnableAction(() -> Config.soulWhipWithAnything = !Config.soulWhipWithAnything)
                )
        );

        results.put("toggle enable disable terminator with anything",
                new Result(
                        (Config.termWithAnything ? "Disable" : "Enable") + ": Terinator w/ Anything",
                        new ItemIcon(new ItemStack(Items.bow)),
                        new RunnableAction(() -> Config.termWithAnything = !Config.termWithAnything)
                )
        );

        results.put("shadyaddons settings config options",
                new Result(
                        "Open ShadyAddons Settings",
                        new ImageIcon("chester.png"),
                        new CommandAction("/sh")
                )
        );

        if(Shady.USING_SBA) {
            results.put("skyblockaddons sba settings config options",
                    new Result(
                            "Open SkyBlockAddons Settings",
                            new ItemIcon(new ItemStack(Items.cookie)),
                            new CommandAction("/sba")
                    )
            );
        }

        if(Shady.USING_SBE) {
            results.put("skyblockextras sbe settings config options",
                    new Result(
                            "Open SkyBlockExtras Settings",
                            new ImageIcon("sbe.png"),
                            new CommandAction("/sbe")
                    )
            );
        }

        if(Shady.USING_SKYTILS) {
            results.put("skytils st config settings options",
                    new Result(
                            "Open Skytils Settings",
                            new ImageIcon("skytils.png"),
                            new CommandAction("/st config")
                    )
            );

            results.put("griffin refresh burrows skytils st",
                    new Result(
                            "Refresh Griffin Burrows",
                            new ItemIcon(ItemUtils.getSkullItemStack("9197f09e-5cbb-464f-9b8f-7f374d531504", "4c27e3cb52a64968e60c861ef1ab84e0a0cb5f07be103ac78da67761731f00c8")),
                            new CommandAction("/st griffin refresh")
                    )
            );
        }

        if(Shady.USING_SKYTILS || Shady.USING_SBE) {
            results.put("reparty rp",
                    new Result(
                            "Reparty",
                            new ImageIcon("reparty.png"),
                            new CommandAction("/rp")
                    )
            );
        }

    }

    // TODO: Improve performance if it becomes an issue
    public static ArrayList<Result> getResults(String filter) {
        ArrayList<Result> filtered = new ArrayList<>();
        filter = filter.toLowerCase();

        if(filter.equals("")) {
            filtered = results.values().stream().limit(CommandPalette.MAX_RESULTS).collect(Collectors.toCollection(ArrayList::new));
        } else {
            for(Map.Entry<String, Result> result : results.entrySet()) {
                if(result.getKey().toLowerCase().contains(filter) || result.getValue().name.toLowerCase().contains(filter)) {
                    filtered.add(result.getValue());
                }

                if(filtered.size() == CommandPalette.MAX_RESULTS) break;
            }
        }

        if(filtered.isEmpty()) {
            Double result = null;

            try {
                result = ExpressionParser.eval(filter);
            } catch(Exception ignored) {}

            if(result != null) {
                DecimalFormat formattter = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                formattter.setMaximumFractionDigits(340);
                String resultString = formattter.format(result);

                filtered.add(new Result(
                        filter + " = " + resultString,
                        new ImageIcon("calc.png"),
                        new RunnableAction(() -> {
                            Utils.copyToClipboard(resultString);
                            Utils.sendModMessage("Copied §e" + resultString + "§f to your clipboard!");
                        }),
                        "Press enter to copy"
                ));
            } else {
                try {
                    String encodedSearch = URLEncoder.encode(filter.trim(), StandardCharsets.UTF_8.toString());

                    filtered.add(new Result(
                            '"' + filter + '"',
                            new ImageIcon("google.png"),
                            new RunnableAction(() -> {
                                Utils.openUrl("https://www.google.com/search?q=" + encodedSearch);
                            }),
                            "Search on Google"
                    ));

                    filtered.add(new Result(
                            "“" + filter + "”",
                            new ItemIcon(ItemUtils.getSkullItemStack("f26a2360-0158-4f76-8a34-f487883f2b04", "c9c8881e42915a9d29bb61a16fb26d059913204d265df5b439b3d792acd56")),
                            new RunnableAction(() -> {
                                Utils.openUrl("https://hypixel-skyblock.fandom.com/wiki/Special:Search?search=" + encodedSearch);
                            }),
                            "Search on the SkyBlock Wiki"
                    ));

                    filtered.add(new Result(
                            "“" + filter + "”",
                            new ImageIcon("hypixel.png"),
                            new RunnableAction(() -> {
                                Utils.openUrl("https://hypixel.net/search/7211960/?q=" + encodedSearch);
                            }),
                            "Search on the Hypixel Forums"
                    ));
                } catch(Exception ignored) {}
            }
        }

        return filtered;
    }

}
