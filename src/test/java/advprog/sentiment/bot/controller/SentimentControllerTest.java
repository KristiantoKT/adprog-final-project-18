package advprog.sentiment.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.sentiment.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class SentimentControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private SentimentController sentimentController;

    @Test
    void testContextLoads() {
        assertNotNull(sentimentController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/sentiment I'm Hungry");

        TextMessage reply = sentimentController.handleTextMessageEvent(event);

        assertEquals("Sentiment: 2.67%", reply.getText());
    }

    @Test
    void testHandleTextMessageEvent2() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/sentiment You look pretty");

        TextMessage reply = sentimentController.handleTextMessageEvent(event);

        assertEquals("Sentiment: 85.19%", reply.getText());
    }

    @Test
    void testHandleTextMessageEvent3() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/sentiment Let's eat some food");

        TextMessage reply = sentimentController.handleTextMessageEvent(event);

        assertEquals("Sentiment: 96.29%", reply.getText());
    }

    @Test
    void testHandleTextMessageEvent4() {
        MessageEvent<TextMessageContent> event =
            EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = sentimentController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }


    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        sentimentController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}
