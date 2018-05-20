package advprog.bikun.bot.controller;

import advprog.bikun.bot.HalteBikun;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@LineMessageHandler
public class PrintController {
    private String path = "./src/main/java/advprog/bikun/bot/halte-bikun.json";
    private BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
    private ObjectMapper objectMapper = new ObjectMapper();
    private HalteBikun[] halteBikuns = objectMapper.readValue(bufferedReader, HalteBikun[].class);

    private static final Logger LOGGER = Logger.getLogger(PrintController.class.getName());

    public PrintController() throws IOException {
    }


    @EventMapping
    public TemplateMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        List<Message> messageList = new ArrayList<>();
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        if (contentText.equals("/bikun") && event.getSource() instanceof UserSource) {
            return BikunController.requestLocation();
        } else {
            return BikunController.requestLocation();
        }

    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }


    @EventMapping
    public List<Message> handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        LocationMessageContent content = event.getMessage();
        return BikunController.searchHalte(event, halteBikuns);
    }

}
