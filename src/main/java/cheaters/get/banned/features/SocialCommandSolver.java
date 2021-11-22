package cheaters.get.banned.features;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.ExpressionParser;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.ThreadUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SocialCommandSolver {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(Config.socialQuickMathsSolver && event.type == 0 && Utils.inSkyBlock && LocationUtils.onIsland(LocationUtils.Island.PRIVATE_ISLAND)) {
            String message = event.message.getUnformattedText();
            if(message.startsWith("QUICK MATHS! Solve: ")) {
                message = message.replace("QUICK MATHS! Solve: ", "").replace("x", "*");
                int answer = (int) ExpressionParser.eval(message);
                new Thread(() -> {
                    ThreadUtils.sleep(Config.quickMathsAnswerDelay);
                    Utils.sendMessageAsPlayer(String.valueOf(answer));
                }).start();
            }
        }
    }

}
