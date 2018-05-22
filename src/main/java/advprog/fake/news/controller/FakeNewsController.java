package advprog.fake.news.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class FakeNewsController {
    private static final Logger LOGGER = Logger.getLogger(FakeNewsController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        if (contentText.contains("/echo")) {
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));
        } else {
            if (event.getSource().getClass().getTypeName().endsWith("UserSource")) {
                return checkPersonalMessage(contentText);
            } else {
                return checkGroupMessage(contentText);
            }
        }
    }

    private TextMessage checkGroupMessage(String contentText) {
        return new TextMessage(contentText);
    }

    private TextMessage checkPersonalMessage(String contentText) {
        String command = contentText.split(" ")[0];
        if (command.equals("/is_fake")) {
            return new TextMessage("");
        } else if (command.equals("/is_satire")) {
            return new TextMessage("");
        } else if (command.equals("/is_conspiracy")) {
            return new TextMessage("");
        } else if (command.equals("/add_filter")) {
            return new TextMessage("");
        } else {
            return new TextMessage("");
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}
