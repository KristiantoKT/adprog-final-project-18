package advprog.example.bot.controller;

import advprog.example.bot.command.SeasonCommand;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;


@LineMessageHandler
public class LivechartController {

    private static final Logger LOGGER = Logger.getLogger(LivechartController.class.getName());
    SeasonCommand seasonCommand = new SeasonCommand();

    @Autowired
    LineMessagingClient lineMessagingClient;


    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String token = event.getReplyToken();
        String toReply = "";
        String year;
        String season;
        String genre;
        if (contentText.contains("echo")) {
            String replyText = contentText.replace("/echo ", "");
            System.out.println(replyText.equalsIgnoreCase("/lookup_anime"));
            if (replyText.equalsIgnoreCase("/lookup_anime")) {
                carousel(token);
            }
        } else if (contentText.contains("/genre")) {
            String replyText = contentText.replace("/genre", "");
            genre = replyText;
            if (replyText.contains("/season")) {
                season = replyText.replace("/season ", "");
                seasonCommand.setSeason(season);
                toReply = season;
                carouselForYear(token);
            } else if (replyText.contains("/year")) {
                year = replyText.replace("/year ", "");
                seasonCommand.setYear(year);
                seasonCommand.setUrl();

                TextMessage textMessage = seasonCommand.execute(seasonCommand.getUrl(), genre);
                List<Message> messages = Arrays.asList(textMessage);
                lineMessagingClient.replyMessage(new ReplyMessage(token, messages));
            } else {
                genre = replyText.replace(" ", "");
                seasonCommand.setGenre(genre);
                toReply = genre;
                carouselForSeason(token);
            }
        } else {
            Message textMessage = new TextMessage(seasonCommand.getUrl());
            List<Message> messages = Arrays.asList(textMessage);
            lineMessagingClient.replyMessage(new ReplyMessage(token,messages));
        }

        return new TextMessage(toReply);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private void reply(String token, TemplateMessage replies) {
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(token, replies)).get();

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error");
        }
    }

    private void carousel(String replyToken) {
        try {
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn("https://u.livechart.me/anime/poster_images/154/0957157a7117cf99523c2042cc46045f:small.jpg", "Genre", "Click One of The Genre", Arrays.asList(
                                    new PostbackAction("Action",
                                            "action", "/genre Action"),
                                    new PostbackAction("Comedy",
                                            "comedy", "/genre Comedy"),
                                    new PostbackAction("Sci-Fi",
                                            "fantasy", "/genre Fantasy")
                            ))
                    ));
            TemplateMessage templateMessage =
                    new TemplateMessage("Carousel alt text", carouselTemplate);
            this.reply(replyToken, templateMessage);
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    private void carouselForSeason(String replyToken) {
        try {
            String imageUrl = "https://u.livechart.me/anime/poster_images/3011/ba6426ad69bea52f2e4dd1d419a2bd72:small.jpg";
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn(imageUrl, "Season", "Spring", Arrays.asList(
                                    new PostbackAction("Spring",
                                            "spring", "/film/season spring")
                            )),
                            new CarouselColumn(imageUrl, "Season", "Summer", Arrays.asList(
                                    new PostbackAction("Summer",
                                            "summer", "/film/season summer")
                            )),
                            new CarouselColumn(imageUrl, "Season", "Fall", Arrays.asList(
                                    new PostbackAction("Fall",
                                            "fall", "/film/season fall")
                            )),
                            new CarouselColumn(imageUrl, "Season", "Winter", Arrays.asList(
                                    new PostbackAction("Winter",
                                            "winter", "/film/season winter")
                            ))
                    ));
            TemplateMessage templateMessage =
                    new TemplateMessage("Carousel alt text", carouselTemplate);
            this.reply(replyToken, templateMessage);
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    private void carouselForYear(String replyToken) {
        try {
            String imageUrl = "https://u.livechart.me/anime/poster_images/3011/ba6426ad69bea52f2e4dd1d419a2bd72:small.jpg";
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn(imageUrl,
                                    "Year", "Click One of The Year", Arrays.asList(
                                    new PostbackAction("2018",
                                            "2018", "/genre/year 2018"),
                                    new PostbackAction("2017",
                                            "2017", "/genre/year 2017"),
                                    new PostbackAction("2016",
                                            "2016", "/genre/year 2016")
                            ))
                    ));
            TemplateMessage templateMessage =
                    new TemplateMessage("Carousel alt text", carouselTemplate);
            this.reply(replyToken, templateMessage);
        } catch (Exception e) {
            System.out.println("error");
        }

    }

}
