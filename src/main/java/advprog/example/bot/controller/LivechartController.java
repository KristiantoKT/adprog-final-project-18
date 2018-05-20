package advprog.example.bot.controller;

import advprog.example.bot.command.FallCommand;
import advprog.example.bot.command.SpringCommand;
import advprog.example.bot.command.SummerCommand;
import advprog.example.bot.command.WinterCommand;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.*;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@LineMessageHandler
public class LivechartController {

    private static final Logger LOGGER = Logger.getLogger(LivechartController.class.getName());
    private SpringCommand spc = new SpringCommand();
    private SummerCommand smc = new SummerCommand();
    private FallCommand fac = new FallCommand();
    private WinterCommand wnc = new WinterCommand();
    private String seasonAndYear;


    @Autowired
    LineMessagingClient lineMessagingClient;


    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String token = event.getReplyToken();
        String year;
        String season;
        if (contentText.contains("echo")) {
            String replyText = contentText.replace("/echo ", "");
            System.out.println(replyText.equalsIgnoreCase("lookup_anime"));
            if (replyText.equalsIgnoreCase("lookup_anime")) {
                carousel(token);
            }
        } else if (contentText.contains("/genre")){
            String replyText = contentText.replace("/genre", "");
            if (replyText.contains("/season")) {
                season = replyText.replace("/season ", "");
                seasonAndYear = season;
                carouselForYear(token);
            } else if (replyText.contains("/year")){
                year = replyText.replace("/year ", "");
                seasonAndYear += "-" + year;
                String[] sy = seasonAndYear.split("-");
                if(sy[0].equals("spring")){
                    return spc.execute(seasonAndYear, replyText);
                }else if(sy[0].equals("summer")){
                    return smc.execute(seasonAndYear, replyText);
                }else if(sy[0].equals("fall")){
                    return fac.execute(seasonAndYear, replyText);
                }else if(sy[0].equals("winter")){
                    return wnc.execute(seasonAndYear, replyText);
                }
            }
        }
        return new TextMessage("Something Wrong");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private void reply(String token, TemplateMessage replies){
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(token, replies)).get();

        } catch (InterruptedException |ExecutionException e) {
            System.out.println("Error");
        }
    }

    private void carousel(String replyToken){
        try {
            String imageUrl = URI.create("https://static/buttons/1040.jpg").toString();
            String imagesUrl = "data:text/plain;charset=utf-8;base64,L3N0YXRpYy9idXR0b25zLzEwNDAuanBn";
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn("https://u.livechart.me/anime/poster_images/154/0957157a7117cf99523c2042cc46045f:small.jpg", "Genre", "Click One of The Genre", Arrays.asList(
                                    /*new URIAction("Go to line.me",
                                            "https://line.me"),
                                    new URIAction("Go to line.me",
                                            "https://line.me"),*/
                                    new PostbackAction("Action",
                                            "Action", "/genre Action"),
                                    new PostbackAction("Comedy",
                                            "Comedy", "/genre Comedy"),
                                    new PostbackAction("Fantasy",
                                            "Fantasy", "/genre Fantasy")
                            ))
                    ));
            TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
            this.reply(replyToken, templateMessage);
        }
        catch (Exception e) {
            System.out.println("error");
        }

    }
    private void carouselForSeason(String replyToken){
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
            TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate) ;
            this.reply(replyToken, templateMessage);
        }
        catch (Exception e) {
            System.out.println("error");
        }

    }

    private void carouselForYear(String replyToken){
        try {
            String imageUrl = "https://u.livechart.me/anime/poster_images/3011/ba6426ad69bea52f2e4dd1d419a2bd72:small.jpg";
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn(imageUrl, "Year", "Click One of The Year", Arrays.asList(
                                    new PostbackAction("2018",
                                            "2018", "/film/year 2018"),
                                    new PostbackAction("2017",
                                            "2017", "/film/year 2017"),
                                    new PostbackAction("2016",
                                            "2016", "/film/year 2016")
                            ))

                    ));
            TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate) ;
            this.reply(replyToken, templateMessage);
        }
        catch (Exception e) {
            System.out.println("error");
        }

    }

}
