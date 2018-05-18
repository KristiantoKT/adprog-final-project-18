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
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class VgmdbControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private VgmdbController vgmdbController;

    @Test
    void testContextLoads() {
        assertNotNull(vgmdbController);
    }

    @Test
    void testHandleTextMessageEvent() throws IOException {
        //echo
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = vgmdbController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());

        //vgmdb
        MessageEvent<TextMessageContent> event1 =
                EventTestUtil.createDummyTextMessage("/vgmdb OST this month");

        TextMessage reply1 = vgmdbController.handleTextMessageEvent(event1);

        assertTrue(reply1.getText().contains("Beat Saber Original Game Soundtrack : 127388.3 IDR"));

        //incorrect input
        String errormessage = vgmdbController.errorMessage();

        MessageEvent<TextMessageContent> event2 =
                EventTestUtil.createDummyTextMessage("/vgmdb test");

        TextMessage reply2 = vgmdbController.handleTextMessageEvent(event2);

        assertEquals(errormessage, reply2.getText());

        MessageEvent<TextMessageContent> event3 =
                EventTestUtil.createDummyTextMessage("/error test");

        TextMessage reply3 = vgmdbController.handleTextMessageEvent(event3);

        assertEquals(errormessage, reply3.getText());

    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        vgmdbController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}