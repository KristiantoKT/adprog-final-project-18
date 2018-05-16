package billboard.newAge.artist.controller;

import billboard.newAge.artist.parser.NewAgeParser;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;

import java.util.ArrayList;
import java.util.logging.Logger;

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
        if (contentText.contains("/billboard newage ")) {
            NewAgeParser parser = new NewAgeParser();
            ArrayList<String> arrArtist = parser.getArtistsArray();
            String inputArtist = contentText.replace("/billboard newage ", "").toLowerCase();
            if (arrArtist.contains(inputArtist)) {
                ArrayList<String> arrSong = parser.getSongsArray();
                int position = arrArtist.indexOf(inputArtist) + 1;
                return new TextMessage(inputArtist + "\n" + arrSong.get(position - 1)
                        + "\n" + position);
            }
            String error = "Sorry, your artist doesn't make it to new age chart";
            return new TextMessage(error);
        } else if (contentText.contains("/echo ")) {
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));
        }
        return new TextMessage("");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
