package advprog.example.bot.controller;

import advprog.example.bot.oricon.oriconcommand.WeeklyRankCommand;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.logging.Logger;

@LineMessageHandler
public class OriconController {

    private static final Logger LOGGER = Logger.getLogger(OriconController.class.getName());
    private WeeklyRankCommand drc = new WeeklyRankCommand();

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] splitText = contentText.split(" ");
        System.out.println(splitText.length);
        System.out.println(splitText[2]);

        if (splitText.length == 4 && splitText[2].equals("weekly")) {
            return drc.execute(splitText[3]);
        }

        return new TextMessage("something wrong");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
