package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.gui.config.components.ConfigInput;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.ThreadUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class AutoClicker {

    public AutoClicker() {
        KeybindUtils.register("Auto Clicker", Keyboard.KEY_Y);
    }

    private static boolean toggled = false;
    private static boolean burstActive = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeybindUtils.get("Auto Clicker").isPressed()) {
            if(Config.autoClickerMode == 0 && !burstActive) {
                burstActive = true;
                new Thread(() -> {
                    for(int i = 0; i < 25; i++) {
                        if(!burstActive) break;
                        if (Config.autoClickerType == 0) {
                            KeybindUtils.rightClick();
                        } else {
                            KeybindUtils.leftClick();
                        }
                        ThreadUtils.sleep(1000/Config.autoClickerCps);
                    }
                    burstActive = false;
                }, "ShadyAddons-Autoclicker").start();
            } else if(Config.autoClickerMode == 1) {
                toggled = !toggled;
                if(toggled) {
                    new Thread(() -> {
                        while(toggled) {
                            if (Config.autoClickerType == 0) {
                                KeybindUtils.rightClick();
                            } else {
                                KeybindUtils.leftClick();
                            }
                            ThreadUtils.sleep(1000/Config.autoClickerCps);
                        }
                    }, "ShadyAddons-Autoclicker").start();
                }
            }
        }
    }

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
        if(Config.stopAutoClickerInGui) {
            toggled = false;
            burstActive = false;
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if(toggled && Config.autoClickerIndicator && event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            int x = new ScaledResolution(Shady.mc).getScaledWidth()/2 + 10;
            int y = new ScaledResolution(Shady.mc).getScaledHeight()/2 - 2;
            Gui.drawRect(x, y, x+5, y+5, ConfigInput.red.getRGB());
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        toggled = false;
        burstActive = false;
    }

}
