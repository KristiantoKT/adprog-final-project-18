package advprog.popularhotcountry.bot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import java.util.concurrent.ExecutionException;





@LineMessageHandler
public class PopularHotCountryController {

    private static final Logger LOGGER = Logger.getLogger(
            PopularHotCountryController.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;


    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = contentText.replace("/billboard hotcountry", "");

        switch (replyText.substring(1)) {
            case "":
                HotCountry topTen = new HotCountry("https://www.billboard.com/charts/country-songs");
                String result = topTen.listTopTen();
                String replyToken = event.getReplyToken();
                replyText(result, replyToken);
                break;
            default:
                result = "Please use a correct input E.g /echo billboard hotcountry";
                replyToken = event.getReplyToken();
                replyText(result, replyToken);
                break;
        }

        return new TextMessage(replyText.substring(1));
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private void replyText(String replies, String token) {
        TextMessage textMessage = new TextMessage(replies);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(token, textMessage)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error");
        }
    }


}
