package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.components.ClearButton;
import cheaters.get.banned.events.DrawSlotEvent;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class AutoSalvage {

    private static final ClearButton button = new ClearButton(
            0,
            0,
            0,
            71,
            20,
            "Start"
    );
    private boolean inSalvageGui = false;
    private boolean salvaging = false;

    @SubscribeEvent
    public void onBackgroundRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        String chestName = Utils.getGuiName(event.gui);
        inSalvageGui = chestName.equals("Salvage Dungeon Item");
        if(Config.autoSalvage || inSalvageGui) {
            ScaledResolution scaledResolution = new ScaledResolution(Shady.mc);
            button.xPosition = (scaledResolution.getScaledWidth() - button.width) / 2;
            button.yPosition = scaledResolution.getScaledHeight() / 2 - 145;
            button.drawButton(event.gui.mc, event.getMouseX(), event.getMouseY());
        }
    }

    @SubscribeEvent
    public void onDrawSlot(DrawSlotEvent event) {
        if(Config.autoSalvage && inSalvageGui && button.isMouseOver() && event.slot.getStack() != null) {
            NBTTagCompound attributes = event.slot.getStack().getSubCompound("ExtraAttributes", false);
            if(attributes == null) return;
            if(!attributes.hasKey("baseStatBoostPercentage") || attributes.hasKey("dungeon_item_level")) return;

            int x = event.slot.xDisplayPosition;
            int y = event.slot.yDisplayPosition;
            Gui.drawRect(x, y, x + 16, y + 16, Color.RED.getRGB());
        }
    }

    @SubscribeEvent
    public void onMouseInput(GuiScreenEvent.MouseInputEvent.Pre event) {
        if(button.isMouseOver() && Mouse.isButtonDown(0)) {
            Utils.sendModMessage("BUTTON CLICKED");
        }
    }

}
