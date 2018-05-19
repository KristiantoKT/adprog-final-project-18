package advprog.animeairing.bot.controller;

import advprog.animeairing.bot.anime.AnimeAiringToday;
import advprog.animeairing.bot.anime.AnimeParsing;
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
public class AnimeController {

    private static final Logger LOGGER = Logger.getLogger(AnimeController.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;


    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String result = null;

        if (contentText.equalsIgnoreCase("hari ini nonton apa ya?")) {
            AnimeAiringToday airingToday =
                    new AnimeAiringToday("https://www.livechart.me/schedule/tv?layout=compact");
            result = airingToday.listAiringToday();
            String replyToken = event.getReplyToken();
            replyText(result, replyToken);
            return new TextMessage(result);
        } else if (contentText.contains("/is_airing")) {
            String replyTextFix = contentText.replace("/is_airing", "");
            String animeTitle = replyTextFix.substring(1);
            AnimeParsing currentAiring = new AnimeParsing(animeTitle);
            result = "";
            String replyToken = event.getReplyToken();
            replyText(result, replyToken);
            return new TextMessage(result);
        } else {
            result = "Please use a correct input E.g /is_airing ANIMETITLE";
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
