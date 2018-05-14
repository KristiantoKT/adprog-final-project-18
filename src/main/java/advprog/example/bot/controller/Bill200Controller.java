package advprog.example.bot.controller;

import advprog.favartist.bot.ArtistBill200;

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
public class Bill200Controller {

    private static final Logger LOGGER = Logger.getLogger(
            Bill200Controller.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));

        String url = "https://www.billboard.com/charts/billboard-200";
        ArtistBill200 billboard200 = new ArtistBill200(url);

        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        if (contentText.contains("/billboard bill200")) {
            String replyText = contentText.replace("/billboard bill200 ", "");
            return new TextMessage(billboard200.findArtistInChart(replyText));
        } else {
            String result = "Please use a correct input E.g /billboard bill200 ARTIST";
            String replyToken = event.getReplyToken();
            reply(result, replyToken);
            return new TextMessage(result);
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private void reply(String reply, String token) {
        TextMessage textMessage = new TextMessage(reply);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(token, textMessage)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error");
        }
    }
}
