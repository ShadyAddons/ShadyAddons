package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.components.ConfigButton;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.ThreadUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class AutoClicker {

    public AutoClicker() {
        KeybindUtils.register("Autoclicker", Keyboard.KEY_Y);
    }

    private static boolean toggled = false;
    private static boolean burstActive = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeybindUtils.get("Autoclicker").isPressed()) {
            if(Config.autoClickerBurst && !burstActive) {
                burstActive = true;
                new Thread(() -> {
                    for(int i = 0; i < Config.burstAmount; i++) {
                        KeybindUtils.rightClick();
                        ThreadUtils.sleep(1000/Config.autoClickerCps);
                    }
                    burstActive = false;
                }, "ShadyAddons-Autoclicker").start();
            } else if(Config.autoClickerToggle) {
                toggled = !toggled;
                if(toggled) {
                    new Thread(() -> {
                        while(toggled) {
                            KeybindUtils.rightClick();
                            ThreadUtils.sleep(1000/Config.autoClickerCps);
                        }
                    }, "ShadyAddons-Autoclicker").start();
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if(toggled && Config.autoClickerIndicator && event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            int x = new ScaledResolution(Shady.mc).getScaledWidth()/2 + 10;
            int y = new ScaledResolution(Shady.mc).getScaledHeight()/2 - 2;
            Gui.drawRect(x, y, x+5, y+5, ConfigButton.red.getRGB());
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        toggled = false;
    }

}
