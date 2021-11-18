package cheaters.get.banned;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.ConfigLogic;
import cheaters.get.banned.config.MainCommand;
import cheaters.get.banned.config.settings.BooleanSetting;
import cheaters.get.banned.config.settings.SelectSetting;
import cheaters.get.banned.config.settings.Setting;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.features.*;
import cheaters.get.banned.features.dungeonmap.DungeonMap;
import cheaters.get.banned.features.dungeonmap.DungeonScanner;
import cheaters.get.banned.features.dungeonmap.RoomLoader;
import cheaters.get.banned.features.jokes.CatPeople;
import cheaters.get.banned.features.jokes.FakeBan;
import cheaters.get.banned.features.jokes.MissingItem;
import cheaters.get.banned.remote.Analytics;
import cheaters.get.banned.remote.MayorAPI;
import cheaters.get.banned.remote.UpdateGui;
import cheaters.get.banned.remote.Updater;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod(modid = Shady.MODID, name = Shady.MODNAME, version = "4.1.3", clientSideOnly = true)
public class Shady {

    public static final String MODNAME = "ShadyAddons";
    public static final String MODID = "autogg";
    public static final String VERSION = "@VERSION@";
    public static final boolean BETA = VERSION.contains("-pre") || VERSION.equals("@VER"+"SION@");

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean usingSkyBlockAddons = false;
    public static boolean usingPatcher = false;
    public static boolean usingSkytils = false;

    public static GuiScreen guiToOpen = null;
    public static boolean enabled = true;
    private static boolean sentPlayTimeCommand = false;
    private static boolean sentPlayTimeData = false;
    private static Pattern playTimePattern = Pattern.compile("You have (\\d*) hours and \\d* minutes playtime!");

    public static ArrayList<Setting> settings = ConfigLogic.collect(Config.class);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new MainCommand());
        ConfigLogic.load();
        RoomLoader.load();
        Updater.check();
        MayorAPI.fetch();
        Analytics.collect("version", VERSION);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new TickEndEvent());
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Utils());
        MinecraftForge.EVENT_BUS.register(new LocationUtils());
        MinecraftForge.EVENT_BUS.register(new DungeonUtils());
        MinecraftForge.EVENT_BUS.register(new DungeonScanner());

        MinecraftForge.EVENT_BUS.register(new BlockAbilities());
        MinecraftForge.EVENT_BUS.register(new StonklessStonk());
        MinecraftForge.EVENT_BUS.register(new GhostBlocks());
        MinecraftForge.EVENT_BUS.register(new AutoCloseChest());
        MinecraftForge.EVENT_BUS.register(new RoyalPigeonMacro());
        MinecraftForge.EVENT_BUS.register(new AutoGG());
        MinecraftForge.EVENT_BUS.register(new AutoSimonSays());
        MinecraftForge.EVENT_BUS.register(new AbilityKeybind());
        MinecraftForge.EVENT_BUS.register(new AutoClicker());
        MinecraftForge.EVENT_BUS.register(new AutoRenewCrystalHollows());
        MinecraftForge.EVENT_BUS.register(new DisableSwordAnimation());
        MinecraftForge.EVENT_BUS.register(new ShowHiddenEntities());
        MinecraftForge.EVENT_BUS.register(new HideSummons());
        MinecraftForge.EVENT_BUS.register(new TeleportWithAnything());
        MinecraftForge.EVENT_BUS.register(new ItemMacro());
        MinecraftForge.EVENT_BUS.register(new MobESP());
        MinecraftForge.EVENT_BUS.register(new GemstoneESP());
        MinecraftForge.EVENT_BUS.register(new AutoTerminals());
        MinecraftForge.EVENT_BUS.register(new AutoMelody());
        MinecraftForge.EVENT_BUS.register(new DungeonMap());
        MinecraftForge.EVENT_BUS.register(new CatPeople());
        MinecraftForge.EVENT_BUS.register(new FakeBan());
        MinecraftForge.EVENT_BUS.register(new MissingItem());
        MinecraftForge.EVENT_BUS.register(new AutoReadyUp());
        MinecraftForge.EVENT_BUS.register(new CrystalReach());
        MinecraftForge.EVENT_BUS.register(new AutoSalvage());
        MinecraftForge.EVENT_BUS.register(new TerminalReach());
        MinecraftForge.EVENT_BUS.register(new AutoSell());
        // By RoseGold: MinecraftForge.EVENT_BUS.register(new AutoArrowAlign());
        MinecraftForge.EVENT_BUS.register(new AutoAlignArrows());

        for(KeyBinding keyBinding : KeybindUtils.keyBindings.values()) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        usingSkyBlockAddons = Loader.isModLoaded("skyblockaddons");
        usingPatcher = Loader.isModLoaded("patcher");
        usingSkytils = Loader.isModLoaded("skytils");
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(guiToOpen != null) {
            mc.displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }

        if(Utils.inSkyBlock && !sentPlayTimeCommand) {
            Utils.sendMessageAsPlayer("/playtime");
            sentPlayTimeCommand = true;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if(Utils.inSkyBlock && sentPlayTimeCommand && !sentPlayTimeData && event.message.getUnformattedText().contains("minutes playtime!")) {
            Matcher matcher = playTimePattern.matcher(event.message.getUnformattedText());
            if(matcher.matches()) {
                Analytics.collect("playtime", matcher.group(1));
                event.setCanceled(true);
                sentPlayTimeData = true;
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(Updater.shouldUpdate && event.gui instanceof GuiMainMenu) {
            guiToOpen = new UpdateGui();
            Updater.shouldUpdate = false;
        }
    }

    public static void disable() {
        enabled = false;
        for(Setting setting : settings) {
            if(setting instanceof BooleanSetting) setting.set(false);
            if(setting instanceof SelectSetting) setting.set(0);
        }
    }

}
