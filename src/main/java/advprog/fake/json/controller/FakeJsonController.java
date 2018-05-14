package advprog.fake.json.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.Random;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;


@LineMessageHandler
public class FakeJsonController {
    private static final Logger LOGGER = Logger.getLogger(FakeJsonController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        try {
            if (content.getText().equals("/fake_json")) {
                String uri = "https://jsonplaceholder.typicode.com/";

                Random random = new Random();
                String[] typeJson = {"posts", "comments", "albums", "photos", "todos", "users"};

                String tipeTerpilih = typeJson[random.nextInt(typeJson.length)];

                int fakeJson = 0;
                if (tipeTerpilih.equalsIgnoreCase("posts")) {
                    fakeJson = random.nextInt(100);
                } else if (tipeTerpilih.equalsIgnoreCase("comments")) {
                    fakeJson = random.nextInt(500);
                } else if (tipeTerpilih.equalsIgnoreCase("albums")) {
                    fakeJson = random.nextInt(100);
                } else if (tipeTerpilih.equalsIgnoreCase("photos")) {
                    fakeJson = random.nextInt(5000);
                } else if (tipeTerpilih.equalsIgnoreCase("todos")) {
                    fakeJson = random.nextInt(200);
                } else if (tipeTerpilih.equalsIgnoreCase("users")) {
                    fakeJson = random.nextInt(10);
                }

                uri += tipeTerpilih + "/";
                uri += fakeJson;

                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForObject(uri, String.class);
                return new TextMessage(result);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new TextMessage("Command not found!");
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
