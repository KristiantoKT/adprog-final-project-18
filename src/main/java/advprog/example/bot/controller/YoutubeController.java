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

        if (contentText.contains("/youtube")) {
            String removeTag = contentText.replace("/youtube", "");
            String youtubeLink = removeTag.substring(1);

            if (validUrl(youtubeLink) && (youtubeLink.contains("youtube") || youtubeLink.contains("youtu.be"))) {
                Element body = youtubeHtml(youtubeLink);

                String title = "Title : " + getTitle(body) + "\n";
                String channel = "Channel : " + getChannel(body) + "\n";
                String viewers = "Viewers : " + getViewers(body) + "\n";
                String likesDislikes = "Likes and Dislikes : "
                        + getLikes(body) + " & " + getDislikes(body);

                return new TextMessage(title + channel + viewers + likesDislikes);

            } else {
                return returnErrorMessage();
            }
        } else {
            return returnErrorMessage();
        }
    }

    public Element youtubeHtml(String url) throws IOException {
        Document doc = Jsoup.connect(url).header("User-Agent", "Chrome").get();
        return doc.body();
    }

    public TextMessage returnErrorMessage() {
        return new TextMessage("Mohon tulis link youtube dan format dengan benar ya friends! "
                + "contoh : /youtube <link>");
    }

    public String getTitle(Element body) {
        return body.getElementById("eow-title")
                .attr("title");
    }

    public String getViewers(Element body) {
        return body.getElementsByClass("watch-view-count")
                .text();
    }

    public String getChannel(Element body) {
        return body.getElementById("watch7-user-header")
                .getElementsByClass("yt-user-info")
                .get(0).child(0).wholeText();
    }

    public String getLikes(Element body) {
        return body.getElementsByClass("yt-uix-button-content")
                .get(19).text();
    }

    public String getDislikes(Element body) {
        return body.getElementsByClass("yt-uix-button-content")
                .get(22).text();
    }

    public boolean validUrl(String url) {
        return url.contains("http") || url.contains("https");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
