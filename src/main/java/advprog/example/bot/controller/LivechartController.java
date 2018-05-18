package advprog.example.bot.controller;

import advprog.example.bot.command.FallCommand;
import advprog.example.bot.command.SpringCommand;
import advprog.example.bot.command.SummerCommand;
import advprog.example.bot.command.WinterCommand;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.logging.Logger;

@LineMessageHandler
public class LivechartController {
    private static final Logger LOGGER = Logger.getLogger(LivechartController.class.getName());

    private String wantedGenre;
    private static final String preferredGenre = "What's your preffered genre?"
            + "  Action"
            + " Comedy"
            + " Fantasy";

    private SpringCommand spc = new SpringCommand();
    private SummerCommand smc = new SummerCommand();
    private FallCommand fac = new FallCommand();
    private WinterCommand wic = new WinterCommand();

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();

        String contentText = content.getText();
        if (contentText.equals("/lookup_anime")) {
            new TextMessage(preferredGenre);
            switch (contentText) {
                default:
                    break;
                case "Action":
                    wantedGenre = "Action";
                    break;
                case "Comedy":
                    wantedGenre = "Comedy";
                    break;
                case "Fantasy":
                    wantedGenre = "Fantasy";
                    break;
            }
            switch (contentText) {
                default:
                    break;
                case "spring-2017":
                    spc.execute("spring-2017", wantedGenre);
                    break;
                case "spring-2018":
                    spc.execute("spring-2018", wantedGenre);
                    break;
                case "spring-2019":
                    spc.execute("spring-2019", wantedGenre);
                    break;
                case "summer-2017":
                    smc.execute("summer-2017", wantedGenre);
                    break;
                case "summer-2018":
                    smc.execute("summer-2018", wantedGenre);
                    break;
                case "summer-2019":
                    smc.execute("summer-2019", wantedGenre);
                    break;
                case "fall-2017":
                    fac.execute("fall-2017", wantedGenre);
                    break;
                case "fall-2018":
                    fac.execute("fall-2018", wantedGenre);
                    break;
                case "fall-2019":
                    fac.execute("fall-2019", wantedGenre);
                    break;
                case "winter-2017":
                    wic.execute("winter-2017", wantedGenre);
                    break;
                case "winter-2018":
                    wic.execute("winter-2018", wantedGenre);
                    break;
                case "winter-2019":
                    wic.execute("winter-2019", wantedGenre);
                    break;
            }
        }
        return new TextMessage("Invalid Command");
    }


    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}

