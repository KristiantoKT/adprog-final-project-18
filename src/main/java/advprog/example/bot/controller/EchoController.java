package advprog.example.bot.controller;

import advprog.similiarity.SimiliartyChecker;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String reply;
        String temp;
        switch (contentText.split(" ")[0]) {
            case "/echo":
                temp = contentText.replace("/echo", "");
                reply = temp.substring(1);
                break;

            case "/docs_sim":
                temp = contentText.replace("/docs_sim ","");
                double sameness = SimiliartyChecker.checkSimiliarity(
                        temp.split(" ")[0],temp.split(" ")[1]);
                if (sameness >= 0) {
                    reply = Double.toString(sameness) + "%";
                }  else {
                    reply = "Incorrect input or maybe your language is not supported."
                        + "try using english";
                }
                break;

            default:
                reply = "Invalid input";
        }
        return new TextMessage(reply);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
