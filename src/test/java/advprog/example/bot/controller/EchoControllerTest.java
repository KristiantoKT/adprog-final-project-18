package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class EchoControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private EchoController echoController;

    @Test
    void testContextLoads() {
        assertNotNull(echoController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");
        TextMessage reply = echoController.handleTextMessageEvent(event);
        assertEquals("Lorem Ipsum", reply.getText());

        event = EventTestUtil.createDummyTextMessage("/weather");
        reply = echoController.handleTextMessageEvent(event);
        assertEquals("Waiting for location input",reply.getText());

        event = EventTestUtil.createDummyTextMessage("/configure_weather metric");
        reply = echoController.handleTextMessageEvent(event);
        assertEquals("Weather configuration changed to metric system",reply.getText());

        event = EventTestUtil.createDummyGroupTextMessage("gua pengen tau cuaca di depok dah");
        reply = echoController.handleTextMessageEvent(event);
        assertTrue(reply.getText().contains("Weather at your position (Depok, ID)"));

        event = EventTestUtil.createDummyGroupTextMessage(
                "gua pengen tau cuaca di wadidiw_wadidaw dah");
        reply = (TextMessage) echoController.handleTextMessageEvent(event);
        assertEquals("Invalid City name / coordinate",reply.getText());
    }

    @Test
    void testHandleLocationMessage() {
        echoController.handleTextMessageEvent(EventTestUtil.createDummyTextMessage("/weather"));
        MessageEvent<LocationMessageContent> event =
                EventTestUtil.createDummyLocationMessage(-6.391256, 106.819511);
        TextMessage reply = (TextMessage) echoController.handleLocationMessageEvent(event);
        assertTrue(reply.getText().contains("Weather at your position (Depok, ID)"));

        echoController.handleTextMessageEvent(EventTestUtil.createDummyTextMessage("/weather"));
        event = EventTestUtil.createDummyLocationMessage(1231.0,1312.0);
        reply = (TextMessage) echoController.handleLocationMessageEvent(event);
        assertEquals("Invalid City name / coordinate",reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}