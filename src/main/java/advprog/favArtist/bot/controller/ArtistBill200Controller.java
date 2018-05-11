package advprog.favArtist.bot.controller;

import advprog.favArtist.bot.ArtistBill200;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler

public class ArtistBill200Controller {
    private static final Logger LOGGER = Logger.getLogger(ArtistBill200Controller.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));

        String url = "https://www.billboard.com/charts/billboard-200";
        ArtistBill200 billboard200 = new ArtistBill200(url);

        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = contentText.replace("/billboard bill200 ", "");
        return new TextMessage(billboard200.findArtistInChart(replyText, url));
    }

    public String findArtist(String artist) {
        return "";
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
