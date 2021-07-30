package hy.shadyaddons;

import hy.shadyaddons.config.ConfigCommand;
import hy.shadyaddons.features.BlockAbilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = ShadyAddons.MODID, name = ShadyAddons.MODNAME, version = ShadyAddons.VERSION, clientSideOnly = true)
public class ShadyAddons {

    public static final String MODID = "shadyaddons";
    public static final String MODNAME = "ShadyAddons";
    public static final String VERSION = "@VERSION@";

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static GuiScreen guiToOpen = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new ConfigCommand());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new BlockAbilities());
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(guiToOpen != null) {
            mc.displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }
    }

}
