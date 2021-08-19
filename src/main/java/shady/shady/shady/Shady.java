package shady.shady.shady;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.MainCommand;
import shady.shady.shady.features.*;
import shady.shady.shady.features.dungeonscanner.DungeonScanner;
import shady.shady.shady.utils.KeybindUtils;
import shady.shady.shady.utils.Utils;

@Mod(modid = Shady.MODID, name = Shady.MODNAME, version = Shady.VERSION, clientSideOnly = true)
public class Shady {

    public static final String MODID = "autogg"; // Disguised as AutoGG for Forge Handshake purposes
    public static final String MODNAME = "Shady";
    public static final String VERSION = "@VERSION@";

    public static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean usingSkyBlockAddons = false;
    public static boolean usingPatcher = false;

    public static GuiScreen guiToOpen = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new MainCommand());
        Config.load();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Utils());

        MinecraftForge.EVENT_BUS.register(new BlockAbilities());
        MinecraftForge.EVENT_BUS.register(new StonklessStonk());
        MinecraftForge.EVENT_BUS.register(new GhostBlockKeybind());
        MinecraftForge.EVENT_BUS.register(new AutoCloseChest());
        MinecraftForge.EVENT_BUS.register(new BossCorleoneFinder());
        MinecraftForge.EVENT_BUS.register(new RoyalPigeonMacro());
        MinecraftForge.EVENT_BUS.register(new AutoGG());
        MinecraftForge.EVENT_BUS.register(new AutoSimonSays());
        MinecraftForge.EVENT_BUS.register(new AbilityKeybind());
        MinecraftForge.EVENT_BUS.register(new SpamRightClick());
        MinecraftForge.EVENT_BUS.register(new AutoRenewCrystalHollows());
        MinecraftForge.EVENT_BUS.register(new DisableSwordAnimation());
        MinecraftForge.EVENT_BUS.register(new ShowHiddenEntities());
        MinecraftForge.EVENT_BUS.register(new HideSummonedMobs());
        MinecraftForge.EVENT_BUS.register(new FakeBan());
        MinecraftForge.EVENT_BUS.register(new TeleportWithAnything());
        MinecraftForge.EVENT_BUS.register(new IceSprayHotkey());
        MinecraftForge.EVENT_BUS.register(new DungeonScanner());

        for(KeyBinding keyBinding : KeybindUtils.keyBindings.values()) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        usingSkyBlockAddons = Loader.isModLoaded("skyblockaddons");
        usingPatcher = Loader.isModLoaded("patcher");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(guiToOpen != null) {
            mc.displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }
    }

}
