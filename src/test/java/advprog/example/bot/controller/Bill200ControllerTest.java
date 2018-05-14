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
public class Bill200ControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private Bill200Controller bill200Controller;

    @Test
    void testContextLoads() {
        assertNotNull(bill200Controller);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard bill200 ArtistRandom");

        TextMessage reply = bill200Controller.handleTextMessageEvent(event);

        assertEquals("Error! The artist you are looking for isn't"
                + " on the Billboard 200 List!", reply.getText());

        event = EventTestUtil.createDummyTextMessage("/billboard bill200 Post Malone");

        reply = bill200Controller.handleTextMessageEvent(event);

        assertEquals("Post Malone\n" + "beerbongs & bentleys\n"
                + "1\n" + "Post Malone\n" + "Stoney\n" + "9", reply.getText());

        event = EventTestUtil.createDummyTextMessage("oi");

        reply = bill200Controller.handleTextMessageEvent(event);

        assertEquals("Please use a correct input E.g /billboard bill200 ARTIST", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        bill200Controller.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}
