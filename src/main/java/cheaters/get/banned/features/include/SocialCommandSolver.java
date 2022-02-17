package cheaters.get.banned.features.include;

import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.ExpressionParser;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.ThreadUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SocialCommandSolver {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(Config.socialQuickMathsSolver && event.type == 0 && ((Utils.inSkyBlock && LocationUtils.onIsland(LocationUtils.Island.PRIVATE_ISLAND)) || Config.enableMathsOutsideSkyBlock)) {
            String message = event.message.getUnformattedText();
            if(message.startsWith("QUICK MATHS! Solve: ")) {
                message = message.replace("QUICK MATHS! Solve: ", "").replace("x", "*");
                int answer = (int) ExpressionParser.eval(message);
                MiscStats.add(MiscStats.Metric.MATH_PROBLEMS_SOLVED);
                new Thread(() -> {
                    ThreadUtils.sleep(Config.quickMathsAnswerDelay);
                    Utils.sendModMessage("The answer is " + answer);
                    Utils.sendMessageAsPlayer("/ac " + answer);
                }).start();
            }
        }
    }

}
