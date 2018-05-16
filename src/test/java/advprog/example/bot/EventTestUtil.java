package advprog.example.bot;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;

import java.time.Instant;

public class EventTestUtil {

    private EventTestUtil() {
        // Default private constructor
    }

    public static MessageEvent<TextMessageContent> dummyText(String text) {
        return new MessageEvent<>("replyToken", new UserSource("userId"),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    public static MessageEvent<LocationMessageContent> dummyLocation(String title, String addr,
                                                                     double lat, double lon) {
        LocationMessageContent content = new LocationMessageContent("id", title, addr, lat, lon);
        return new MessageEvent<>("replyToken", new UserSource("userId"),
                content,
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }
}
