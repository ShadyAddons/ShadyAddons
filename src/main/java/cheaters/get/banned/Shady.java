package cheaters.get.banned;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.ConfigLogic;
import cheaters.get.banned.config.MainCommand;
import cheaters.get.banned.config.Setting;
import cheaters.get.banned.features.*;
import cheaters.get.banned.remote.MayorAPI;
import cheaters.get.banned.remote.UpdateGui;
import cheaters.get.banned.remote.Updater;
import cheaters.get.banned.remote.Whitelist;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

@Mod(modid = "autogg", name = Shady.MODNAME, version = "4.1.0", clientSideOnly = true)
public class Shady {

    public static final String MODNAME = "ShadyAddons";
    public static final String VERSION = "@VERSION@";
    public static final boolean PRIVATE = VERSION.contains("-pre") || VERSION.equals("@VER"+"SION@");

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean usingSkyBlockAddons = false;
    public static boolean usingPatcher = false;
    public static boolean usingSkytils = false;

    public static GuiScreen guiToOpen = null;
    public static boolean enabled = true;

    public static ArrayList<Setting> settings = ConfigLogic.collect(Config.class);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Whitelist.check();
        ClientCommandHandler.instance.registerCommand(new MainCommand());
        ConfigLogic.load();
        Updater.check();
        MayorAPI.fetch();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Utils());
        MinecraftForge.EVENT_BUS.register(new LocationUtils());

        MinecraftForge.EVENT_BUS.register(new BlockAbilities());
        MinecraftForge.EVENT_BUS.register(new StonklessStonk());
        MinecraftForge.EVENT_BUS.register(new GhostBlocks());
        MinecraftForge.EVENT_BUS.register(new AutoCloseChest());
        MinecraftForge.EVENT_BUS.register(new BossCorleoneFinder());
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
        MinecraftForge.EVENT_BUS.register(new ItemKeybind());
        MinecraftForge.EVENT_BUS.register(new MobESP());
        MinecraftForge.EVENT_BUS.register(new GemstoneESP());
        MinecraftForge.EVENT_BUS.register(new AutoTerminals());
        MinecraftForge.EVENT_BUS.register(new AutoHarp());

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
    public void onTick(TickEvent.ClientTickEvent event) {
        if(guiToOpen != null) {
            mc.displayGuiScreen(guiToOpen);
            guiToOpen = null;
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
            setting.update(false, true);
        }
    }

}
