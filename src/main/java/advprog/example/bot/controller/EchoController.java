package advprog.example.bot.controller;

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
    private boolean stateStatus = false;
    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] arrContentText = contentText.split(" ");
        if (arrContentText[0].equalsIgnoreCase("/echo")) {
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));
        } else if (arrContentText[0].equalsIgnoreCase("/add_wiki")) {
            return new TextMessage("");
        } else if (arrContentText[0].equalsIgnoreCase("/random_wiki_article")) {
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

    public boolean checkValidEndPoint(String url) {
        return false;
    }
}
