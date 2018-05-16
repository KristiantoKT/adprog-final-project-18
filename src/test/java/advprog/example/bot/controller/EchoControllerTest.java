package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

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
                EventTestUtil.createDummyTextMessage("/billboard japan100 Artistsiapa");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Oops sorry! The artist isn't on the Billboard Japan HOT 100!", reply.getText());

        event = EventTestUtil.createDummyTextMessage("/billboard japan100 BTS");

        reply = echoController.handleTextMessageEvent(event);

        assertEquals("Artist : " + "BTS" + "\n" + "Song : " + "Don't Leave Me" + "\n" + "Rank : " + "14"+
                "\n" + "Artist : " + "BTS" + "\n" + "Song : " + "DNA" + "\n" + "Rank : " + "23"+
                "\n" + "Artist : " + "BTS" + "\n" + "Song : " + "Let Go" + "\n" + "Rank : " + "91"+
                "\n" , reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}