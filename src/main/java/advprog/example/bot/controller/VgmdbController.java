package advprog.example.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class VgmdbController {

    private static final Logger LOGGER = Logger.getLogger(VgmdbController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        if (contentText.contains("/echo")) {
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));
        } else if (contentText.contains("/vgmdb")) {
            if (contentText.equalsIgnoreCase("/vgmdb OST this month")) {
                String replyText = "open vgmdb.net";
                return new TextMessage(replyText);
            } else {
                return new TextMessage(errorMessage());
            }
        } else {
            return new TextMessage(errorMessage());
        }
    }

    public String errorMessage() {
        return "incorrect input. it should be is : /vgmdb OST this month";
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}