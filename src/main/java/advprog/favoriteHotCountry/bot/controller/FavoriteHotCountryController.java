package advprog.favoriteHotCountry.bot.controller;


import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.ArrayList;
import java.util.logging.Logger;

@LineMessageHandler
public class FavoriteHotCountryController {
    private static final Logger LOGGER = Logger.getLogger(FavoriteHotCountryController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(content='%s')", event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = contentText.replace("/billboard hotcountry ", "");
        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public String artistInfo(String artist) {
        String tempArtist = "";
        if (artist.matches("[a-zA-Z0-9]")) {
            tempArtist += artist;
        }

        return tempArtist;
    }
}
