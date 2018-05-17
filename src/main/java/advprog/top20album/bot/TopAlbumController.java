package advprog.top20album.bot;

import advprog.favartist.bot.ArtistBill200;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class TopAlbumController {
    private static final Logger LOGGER = Logger.getLogger(
            TopAlbumController.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));

        String url = "https://vgmdb.net/db/statistics.php?do=top_rated";
        TopAlbum topalbum = new TopAlbum(url);

        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        if (contentText.contains("/vgmdb most_popular")) {
            String replyText = contentText.replace("/billboard bill200 ", "");
            return new TextMessage("");
        } else {
            String result = "Please use a correct input E.g /vgmdb most_popular";
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
