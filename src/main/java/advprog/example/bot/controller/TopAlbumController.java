package advprog.example.bot.controller;

import advprog.top20album.bot.TopAlbum;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class TopAlbumController {

    private static final Logger LOGGER = Logger.getLogger(TopAlbumController.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = contentText.replace("/vgmdb most_popular", "true");

        switch (replyText) {
            case "true":
                TopAlbum top10 = new TopAlbum("https://vgmdb.net/db/statistics.php?do=top_rated");
                String toReply = top10.printTop20();
                String replyToken = event.getReplyToken();
                reply(toReply, replyToken);
                break;
            default:
                toReply = "Please Use a good input. E.g. /vgmdb most_popular";
                replyToken = event.getReplyToken();
                reply(toReply, replyToken);
                break;
        }
        return new TextMessage(replyText);
    }

    private void reply(String replies, String token) {
        TextMessage textMessage = new TextMessage(replies);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(token, textMessage)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error");
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }



}
