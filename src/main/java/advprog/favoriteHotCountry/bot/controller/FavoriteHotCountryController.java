package advprog.favoritehotcountry.bot.controller;


import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class FavoriteHotCountryController {
    private static final Logger LOGGER = Logger.getLogger(FavoriteHotCountryController.class.getName());
    private String url;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(content='%s')", event.getMessage()));

        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        url = "https://www.billboard.com/charts/country-songs";
        HotCountry songsByArtist = new HotCountry(url);

        String artist = contentText.replace("/billboard hotcountry", "");

        songsByArtist.setHotCountryArtist(artist, url);
        String result = songsByArtist.infoArtist();

        return new TextMessage(result);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(source='%s')", event.getSource()));
    }


}
