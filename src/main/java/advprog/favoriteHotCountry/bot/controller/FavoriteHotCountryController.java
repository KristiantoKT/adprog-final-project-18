package advprog.favoritehotcountry.bot.controller;

import advprog.favoritehotcountry.bot.controller.HotCountry;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;


@LineMessageHandler
public class FavoriteHotCountryController {

    private static final Logger LOGGER = Logger.getLogger(
            FavoriteHotCountryController.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;


    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        if (contentText.contains("/billboard hotcountry")) {
            String replyTextFix = contentText.replace("/billboard hotcountry", "");
            String artistName = replyTextFix.substring(1);
            HotCountry artistChart = new HotCountry(artistName,"https://www.billboard.com/charts/country-songs");
            String result = artistChart.infoArtist();
            String replyToken = event.getReplyToken();
            replyText(result, replyToken);
            return new TextMessage(replyTextFix.substring(1));
        } else {
            String result = "Please use a correct input E.g /billboard hotcountry artistname";
            String replyToken = event.getReplyToken();
            replyText(result, replyToken);
            return new TextMessage(result);
        }

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
