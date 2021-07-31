package hy.shadyaddons;

import hy.shadyaddons.config.Config;
import hy.shadyaddons.config.ConfigCommand;
import hy.shadyaddons.features.*;
import hy.shadyaddons.utils.KeybindUtils;
import hy.shadyaddons.utils.Utils;
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

@Mod(modid = ShadyAddons.MODID, name = ShadyAddons.MODNAME, version = ShadyAddons.VERSION, clientSideOnly = true)
public class ShadyAddons {

    public static final String MODID = "shadyaddons"; // Disguised as AutoGG for Forge Handshake purposes
    public static final String MODNAME = "ShadyAddons";
    public static final String VERSION = "@VERSION@";

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean usingSkyBlockAddons = false;
    public static GuiScreen guiToOpen = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new ConfigCommand());
        Config.load();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Utils());

        MinecraftForge.EVENT_BUS.register(new BlockAbilities());
        MinecraftForge.EVENT_BUS.register(new ChestGhostHand());
        MinecraftForge.EVENT_BUS.register(new GhostBlockKeybind());
        MinecraftForge.EVENT_BUS.register(new AutoCloseChest());
        MinecraftForge.EVENT_BUS.register(new BossCorleoneFinder());
        MinecraftForge.EVENT_BUS.register(new RoyalPigeonMacro());

        for(KeyBinding keyBinding : KeybindUtils.keyBindings.values()) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        usingSkyBlockAddons = Loader.isModLoaded("skyblockaddons");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(guiToOpen != null) {
            mc.displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }
    }

}
