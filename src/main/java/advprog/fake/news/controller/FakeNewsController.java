package advprog.fake.news.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import org.json.JSONObject;
import java.util.logging.Logger;

@LineMessageHandler
public class FakeNewsController {
    private static final Logger LOGGER = Logger.getLogger(FakeNewsController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        JSONObject
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        try {
            if (content.getText().contains("/is_fake")) {
                boolean isFake = determineFake();
                return new TextMessage("ehe");
            } else if (content.getText().contains("/is_satire")) {
                boolean isSatire = determineSatire();
                return new TextMessage("ehe");
            } else if (content.getText().contains("/is_conspiracy")) {
                boolean isConspiracy = determineConspiracy();
                return new TextMessage("ehe");
            } else if (content.getText().contains("/add_filter")) {
                addFilter();
                return new TextMessage("ehe");
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new TextMessage("Command not found!");
        }

    }

    private void addFilter() {
    }

    private boolean determineConspiracy() {
        return true;
    }

    private boolean determineSatire() {
        return true;
    }

    private boolean determineFake() {
        return true;
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}
