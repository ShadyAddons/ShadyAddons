package cheaters.get.banned.features.connectfoursolver;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.DrawSlotEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ConnectFourSolver {

    private boolean calculating = false;
    private int solutionSlot = -1;

    private boolean isOurTurn = false;
    private boolean inGame = false;
    private Thread thread = null;

    private static final int EMPTY = 0;
    private static final int COMPUTER = 1;
    private static final int OPPONENT = 2;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(Config.connectFourAI && Utils.inSkyBlock && event.type == 0) {
            String message = event.message.getUnformattedText();
            if(message.endsWith(" defeated you in Four in a Row!")) {
                MiscStats.add(MiscStats.Metric.CONNECT_FOUR_LOSSES);
            } else if(message.startsWith("You defeated ") && message.endsWith(" in Four in a Row!")) {
                MiscStats.add(MiscStats.Metric.CONNECT_FOUR_WINS);
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Config.connectFourAI && Utils.inSkyBlock) {
            inGame = Shady.mc.currentScreen instanceof GuiChest && Utils.getInventoryName().startsWith("Four in a Row - ");

            if(inGame) {
                if(Shady.mc.thePlayer.openContainer.inventorySlots.get(1) != null) {
                    if(Shady.mc.thePlayer.openContainer.inventorySlots.get(1).getStack() != null) {
                        isOurTurn = Shady.mc.thePlayer.openContainer.inventorySlots.get(1).getStack().getItem() == Items.item_frame;
                    }
                }
            }

            if(!isOurTurn || !inGame) solutionSlot = -1;
            if(!inGame && thread != null && thread.isAlive()) thread.stop(); // Forgive me Father for I have sinned

            if(isOurTurn && !calculating && inGame && solutionSlot == -1) {
                calculating = true;
                thread = new Thread(() -> {
                    ConnectFourAlgorithm.Board board = serializeInventory(Shady.mc.thePlayer.openContainer);
                    ConnectFourAlgorithm algorithm = new ConnectFourAlgorithm(board);

                    int column = algorithm.getAIMove();
                    algorithm.board.placeMove(column, COMPUTER);

                    for(int row = 5; row >= 0; --row) {
                        if(board.board[row][column] == 0) {
                            solutionSlot = row * 9 + column + 1 + 9;
                            break;
                        }
                    }
                    calculating = false;
                }, "ShadyAddons-ConnectFour");
                thread.start();
            }
        }
    }

    private ConnectFourAlgorithm.Board serializeInventory(Container chest) {
        ConnectFourAlgorithm.Board board = new ConnectFourAlgorithm.Board();
        for(Slot slot : chest.inventorySlots) {
            if(slot.slotNumber > 53 ||
                    slot.getStack() == null ||
                    slot.slotNumber % 9 == 0 ||
                    (slot.slotNumber + 1) % 9 == 0) continue; // Exclude 0th and 8th columns

            int row = slot.slotNumber / 9;
            int column = (slot.slotNumber - 1) % 9;
            Item item = slot.getStack().getItem();

            if(item == Items.item_frame || item == Items.painting) {
                board.board[row][column] = EMPTY;
            }

            if(item == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
                if(slot.getStack().getItemDamage() == 1) { // Orange
                    board.board[row][column] = OPPONENT;
                } else { // Blue
                    board.board[row][column] = COMPUTER;
                }
            }
        }
        return board;
    }

    @SubscribeEvent
    public void onBackgroundRender(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if(Config.connectFourAI && Utils.inSkyBlock && inGame && calculating) {
            int y = event.gui.height / 2 - 105;
            int x = event.gui.width / 2 + 73;
            int textureX = (int) ((System.currentTimeMillis() / 10) % 20 * 8);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 1000);
            GlStateManager.color(64, 64, 64);
            RenderUtils.drawTexture(new ResourceLocation("shadyaddons:loader.png"), x, y, 8, 8, 160, 8, textureX, 0);
            GlStateManager.popMatrix();
        }
    }

    @SubscribeEvent
    public void onDrawSlot(DrawSlotEvent event) {
        if(Config.connectFourAI && Utils.inSkyBlock && inGame && solutionSlot == event.slot.slotNumber) {
            int x = event.slot.xDisplayPosition;
            int y = event.slot.yDisplayPosition;
            Gui.drawRect(x, y, x + 16, y + 16, Color.GREEN.getRGB());
        }
    }

}
