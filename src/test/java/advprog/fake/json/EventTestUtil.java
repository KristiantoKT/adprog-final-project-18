package advprog.fake.json;

import advprog.example.bot.BotExampleApplication;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class EventTestUtil {

    public EventTestUtil(){

    }

    public static MessageEvent<TextMessageContent> createDummyTextMessage(String text) {
        return new MessageEvent<>("replyToken", new UserSource("userId"),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    @Test
    public void applicationContextLoaded() {
    }

    @Test
    public void applicationContextTest() {
        FakeJSONApplication.main(new String[]{});
    }
}

