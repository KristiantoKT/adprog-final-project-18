package billboard.tropical.artist.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ArtistController {
    private static final Logger LOGGER = Logger.getLogger(FavArtistController.class.getName());

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
            TropicalParser parser = new TropicalParser();
            ArrayList<String> arrArtist = parser.getArrayArtist();
            String inputArtist = contentText.replace("/billboard tropical ", "").toLowerCase();
            if (arrArtist.contains(inputArtist)) {
                ArrayList<String> arrSong = parser.getArraySong();
                int position = arrArtist.indexOf(inputArtist) + 1;
                return new TextMessage(inputArtist + "\n"
                        + arrSong.get(position - 1) + "\n" + position);
            }
            String error = "Sorry, your artist is not valid or doesn't make it to top 100";
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
