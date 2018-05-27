package advprog.example.bot.controller;

import advprog.randomarticlemediawiki.Article;
import advprog.randomarticlemediawiki.MediaWikiStorage;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;



@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] arrContentText = contentText.split(" ");
        if (arrContentText[0].equalsIgnoreCase("/echo")) {
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));

        } else if (arrContentText[0].equalsIgnoreCase("/add_wiki")) {
            if (MediaWikiStorage.isMediaWikiApiActive(arrContentText[1])) {
                boolean hasil = MediaWikiStorage.addUrl(arrContentText[1]);
                if (hasil) {
                    return new TextMessage(arrContentText[1] + " berhasil ditambahkan");
                } else {
                    return new TextMessage(arrContentText[1] + " sudah ada");
                }
            } else {
                return new TextMessage("[ERROR] Link Salah, Format : https://[MediaWikiUrl]/api.php");
            }

        } else if (arrContentText[0].equalsIgnoreCase("/random_wiki_article")) {
            String[] arrUrl = MediaWikiStorage.getUrl();
            List<CarouselColumn> carousel = new ArrayList<>();

            for (String i : arrUrl) {
                URL url = null;
                try {
                    url = new URL(i);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                List<Action> s = new ArrayList<>();
                s.add(new PostbackAction("Baca artikel", i));
                carousel.add(new CarouselColumn(null, null,  url.getHost(), s));
            }

            CarouselTemplate carouselTemplate = new CarouselTemplate(carousel);
            TemplateMessage templateMessage = new TemplateMessage(
                    "Carousel alt text", carouselTemplate);
            return templateMessage;
        } else {
            return new TextMessage("Format penulisan salah."
                    + "\n untuk menambahkan url ketik \n'/add_wiki url'."
                    + "\n untuk membaca artikel ketik \n'/random_wiki_article'");
        }
    }

    @EventMapping
    public List<Message> handlePostbackEvent(PostbackEvent event) throws Exception {
        String replyToken = event.getReplyToken();
        Article article = MediaWikiStorage.getRandomArticle(event.getPostbackContent().getData());

        Message b = new TextMessage(article.getTitle()
                + "\n\n" + article.getSummary() + "\n\n" + article.getLink());

        List<Message> c = new ArrayList<>();

        if (article.getImageUrl() != null) {
            Message a = new ImageMessage(article.getImageUrl(), article.getImageUrl());
            c.add(a);
        }
        c.add(b);
        return c;
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
