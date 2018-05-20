package advprog.example.bot.controller;

import advprog.example.bot.EventTestUtil;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class LivechartControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    private String sampleGenre = "Action";

    @Autowired
    private LivechartController livechartcontroller;

    @Test
    void testContextLoads() {
        Assert.assertNotNull(livechartcontroller);
    }

    @Test
    void testHandleTextMessageEvent() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/lookup_anime");

        TextMessage reply = livechartcontroller.handleTextMessageEvent(event);
        reply.getText().contains(sampleGenre);


    }

    @Test
    void testHandleDefaultMessage() {
        Event event = Mockito.mock(Event.class);

        livechartcontroller.handleDefaultMessage(event);

        Mockito.verify(event, VerificationModeFactory.atLeastOnce()).getSource();
        Mockito.verify(event, VerificationModeFactory.atLeastOnce()).getTimestamp();
    }

    @Test
    void testHandleTextMessageEventFilm() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/genre Action");

        TextMessage reply = livechartcontroller.handleTextMessageEvent(event);

        assertEquals("Something Wrong", reply.getText());
    }
    @Test
    void testHandleTextMessageEventFilmSeason() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/genre/season winter");

        TextMessage reply = livechartcontroller.handleTextMessageEvent(event);

        assertEquals("Something Wrong", reply.getText());
    }
}
