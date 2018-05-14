package advprog.example.bot.controller;

import java.io.IOException;

import advprog.example.bot.EventTestUtil;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@LineMessageHandler
public class ControllerForOricon {
    private static final Logger LOGGER = Logger.getLogger(ControllerForOricon.class.getName());

    private WeeklyCommand wrc = new WeeklyCommand();
    private MonthlyCommand mrc = new MonthlyCommand();

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] splitText = contentText.split(" ");
        String[] splitDetail =  splitText[2].split("-");

        if (splitText[0].equals("/oricon")) {
            if (splitDetail.length == 2) {
                return mrc.execute(splitText[2]);
            } else if (splitDetail.length == 3) {
                return wrc.execute(splitText[2]);
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
