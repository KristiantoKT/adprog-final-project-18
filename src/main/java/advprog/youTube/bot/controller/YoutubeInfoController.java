package advprog.youTube.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.print.Doc;

@LineMessageHandler
public class YoutubeInfoController {

    private static final Logger LOGGER = Logger.getLogger(YoutubeInfoController.class.getName());

//    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String verifyYoutube = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(verifyYoutube);
        Matcher matcher = compiledPattern.matcher(contentText);

        if(matcher.find()){
            Document doc = Jsoup.connect(contentText).header("User-Agent", "Chrome").get();
            Element body = doc.body();

            String title = getTitle(doc, body);
            String channel = getChannel(doc, body);
            String viewers = getViewersCount(doc, body);
            String likesdislike = getLikes(doc, body) + " & " + getDislikes(doc, body);

            String replyText = String.format("Title : %s %n Channel : %s %n Viewers : %s %n " +
                            "Likes and Dislikes : %s", title, channel, viewers, likesdislike);


            return new TextMessage(replyText);


        } else {
            return new TextMessage("You have to text me a correct youtube link if you want to make things right");
        }
    }

    private String getTitle (Document doc, Element body){
        return body.getElementById("eow-title")
                .attr("title");
    }

    private String getChannel (Document doc, Element body) {
        return body.getElementById("watch7-user-header")
                .getElementsByClass("yt-user-info")
                .get(0)
                .child(0)
                .wholeText();
    }

    private String getViewersCount (Document doc, Element body) {
        return body.getElementsByClass("watch-view-count").text();
    }

    private String getLikes (Document doc, Element body) {
        return body.getElementsByAttributeValue("title", "I like this").get(0).text();
    }

    private String getDislikes (Document doc, Element body) {
        return body.getElementsByAttributeValue("title", "I dislike this").get(0).text();
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
