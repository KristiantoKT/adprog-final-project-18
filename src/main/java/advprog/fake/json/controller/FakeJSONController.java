package advprog.fake.json.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.logging.Logger;

@LineMessageHandler
public class FakeJSONController {
    private static final Logger LOGGER = Logger.getLogger(FakeJSONController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        try {
            if (content.getText().equals("/fake_json")){
                String uri = "https://jsonplaceholder.typicode.com/posts/";

                Random random = new Random();
                int fakePost = random.nextInt(99);
                fakePost++;

                uri = uri + fakePost;

                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForObject(uri, String.class);
                return new TextMessage(result);
            }else{
                throw new IllegalArgumentException();
            }
        }catch (IllegalArgumentException e){
            return new TextMessage("Command not found!");
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
