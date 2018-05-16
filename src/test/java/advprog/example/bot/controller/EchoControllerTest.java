package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import advprog.example.bot.EventTestUtil;

import advprog.photonearby.PhotoNearby;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
    void testHandleTextMessageEventEcho() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        Message reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", ((TextMessage) reply).getText());
    }

    @Test
    void testHandleTextMessageEventPhotoNearby() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("nearby photo");

        Message reply = echoController.handleTextMessageEvent(event);

        assertEquals("Confirm Location", ((TemplateMessage) reply).getAltText());
    }

    @Test
    void testHandleLocationMessageEvent() {
        MessageEvent<LocationMessageContent> event = EventTestUtil.createDummyLocationMessage("Search Nearby Phooto",
                "Universitas Indonesia", 6, -10);

        List<Message> reply = echoController.handleLocationMessageEvent(event);

        assertNotNull(reply);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}