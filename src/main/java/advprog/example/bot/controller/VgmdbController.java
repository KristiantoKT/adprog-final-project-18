package advprog.example.bot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;



@LineMessageHandler
public class VgmdbController {

    private static final Logger LOGGER = Logger.getLogger(VgmdbController.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] splitInput = contentText.split(" ");

        if (splitInput[0].equalsIgnoreCase("/echo")) {

            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));

        } else if (splitInput[0].equalsIgnoreCase("/vgmdb")) {

            if (contentText.equalsIgnoreCase("/vgmdb OST this month")) {

                AlbumOfTheMonth albumOfTheMonth = new
                        AlbumOfTheMonth("https://vgmdb.net/db/calendar.php?year=2018&month=5");

                String result = albumOfTheMonth.listAlbum();
                return new TextMessage(result);

            } else {
                return new TextMessage(errorMessage());
            }
        } else {
            return new TextMessage(errorMessage());
        }
    }

    public String errorMessage() {
        return "Incorrect input! it should be: /vgmdb OST this month";
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}