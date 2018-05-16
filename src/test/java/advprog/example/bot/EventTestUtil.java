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

    public static MessageEvent<TextMessageContent> createDummyTextMessage(String text) {
        return new MessageEvent<>("replyToken", new UserSource("userId"),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    public static MessageEvent<LocationMessageContent> createDummyLocationMessage(String title, String address,
                                                                                  double lat, double lon) {
        return new MessageEvent<>("replyToken", new UserSource("userId"),
                new LocationMessageContent("id", title, address, lat, lon),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }
}
