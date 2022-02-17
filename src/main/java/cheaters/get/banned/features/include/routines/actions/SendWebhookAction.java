package cheaters.get.banned.features.include.routines.actions;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SendWebhookAction extends Action {

    private static final String note = "Calm your tits, this is for the Routines action that sends a STATIC webhook message.";

    private String url;
    private String message;

    public SendWebhookAction(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        url = data.keyAsString("url");
        message = data.keyAsString("message");

        if(!HttpUtils.isValidURL(url) || !url.startsWith("https://discord.com/api/webhooks/")) {
            throw new RoutineRuntimeException("Invalid Discord webhook URL in SendWebhookAction");
        }
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        JsonObject payload = new JsonObject();
            JsonArray embeds = new JsonArray();
                JsonObject embed = new JsonObject();
                    embed.addProperty("description", message);
                    JsonObject footer = new JsonObject();
                        footer.addProperty("text", "Sent with ShadyAddons Routines");
                        footer.addProperty("icon_url", "https://shadyaddons.com/assets/chester.png");
                    embed.add("footer", footer);
                embeds.add(embed);
            payload.add("embeds", embeds);
        String json = new Gson().toJson(payload);

        String response = HttpUtils.post(url, json);
        if(response == null) {
            throw new RoutineRuntimeException("Error sending webhook message '" + message + "'");
        }
    }

}
