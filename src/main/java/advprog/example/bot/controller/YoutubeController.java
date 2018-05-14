package advprog.example.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@LineMessageHandler
public class YoutubeController {

    private static final Logger LOGGER = Logger.getLogger(YoutubeController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent>
                                                          event) throws IOException {

        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();


        String removeTag = contentText.replace("/youtube", "");
        String youtubeLink = removeTag.substring(1);

        Document doc = Jsoup.connect(youtubeLink).header("User-Agent", "Chrome").get();
        Element body = doc.body();
        String title = getTitle(body);

        return new TextMessage(title);
    }

    private String getTitle(Element body) {
        return body.getElementById("eow-title")
                .attr("title");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
