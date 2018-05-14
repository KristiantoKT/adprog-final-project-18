package advprog.example.bot.controller;

import advprog.example.bot.oricon.oriconcommand.DailyRankCommand;
import advprog.example.bot.oricon.oriconcommand.MonthlyRankCommand;
import advprog.example.bot.oricon.oriconcommand.WeeklyRankCommand;
import advprog.example.bot.oricon.oriconcommand.YearlyRankCommand;
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

    private DailyRankCommand drc = new DailyRankCommand();
    private WeeklyRankCommand wrc = new WeeklyRankCommand();
    private MonthlyRankCommand mrc = new MonthlyRankCommand();
    private YearlyRankCommand yrc = new YearlyRankCommand();

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] splitText = contentText.split(" ");
        System.out.println(splitText.length);

        if (splitText[0].equals("/oricon")) {
            if (splitText.length == 4 && splitText[2].equals("daily")) {
                return drc.execute(splitText[3]);
            } else if (splitText.length == 4 && splitText[2].equals("weekly")) {
                return wrc.execute(splitText[3]);
            } else if (splitText.length == 3 && splitText[2].length() == 4) {
                return yrc.execute(splitText[2]);
            } else if (splitText.length == 3 && splitText[2].length() > 4) {
                return mrc.execute(splitText[2]);
            } else {
                return new TextMessage("Oricon command not found");
            }
        }

        return new TextMessage("something wrong");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
