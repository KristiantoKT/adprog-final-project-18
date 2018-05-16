package billboard.hot100.artist.controller;

import billboard.hot100.artist.parser.Hot100Parser;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.ArrayList;
import java.util.logging.Logger;

@LineMessageHandler
public class ArtistController {
    private static final Logger LOGGER = Logger.getLogger(ArtistController.class.getName());

    @EventMapping
    public TextMessage handleMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        return stringBuilderForEvents(contentText);
    }

    private TextMessage stringBuilderForEvents(String contentText) {
        if (contentText.contains("/billboard tropical ")) {
            Hot100Parser parser = new Hot100Parser();
            ArrayList<String> arrOfArtists = parser.getArrayArtist();
            String inputArtist = contentText.replace("/billboard tropical ", "").toLowerCase();
            if (arrOfArtists.contains(inputArtist)) {
                ArrayList<String> arrOfSongs = parser.getArraySong();
                int position = arrOfArtists.indexOf(inputArtist) + 1;
                return new TextMessage(inputArtist + "\n" + arrOfSongs.get(position - 1)
                        + "\n" + position);
            }
            String error = "Sorry, your artist doesn't make it to tropical chart";
            return new TextMessage(error);
        } else if (contentText.contains("/echo ")) {
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(0));
        }
        return new TextMessage("");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
