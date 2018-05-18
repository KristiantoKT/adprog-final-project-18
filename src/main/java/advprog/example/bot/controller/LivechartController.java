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
            return new TextMessage(preferredGenre);
        }
        return new TextMessage("Invalid Command");
    }


    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}

