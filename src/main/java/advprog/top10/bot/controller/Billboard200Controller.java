package advprog.top10.bot.controller;

import advprog.top10.bot.Billboard200App;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class Billboard200Controller {

    private static final Logger LOGGER = Logger.getLogger(advprog.top10.bot.controller.Billboard200Controller.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));

        String url = "https://www.billboard.com/charts/billboard-200";
        Billboard200App billboard200 = new Billboard200App(url);

        TextMessageContent content = event.getMessage();
        String contextText = content.getText();

        String replyText = contextText.replace("/billboard bill200", "");
        return new TextMessage(billboard200.printTop10());
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
