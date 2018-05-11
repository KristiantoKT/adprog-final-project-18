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
        System.setProperty("line.bot.channelSecret", "0114ad213310d43cfd73d265ebc5292c");
        System.setProperty("line.bot.channelToken", "pnsYfzh3MlyPet4DpGv679B6Sp+l099hL5GGS6W"
                + "Y2KhXT3CkXR/1FbSF739dwD+yzV1QoEM3/lOpkdhv5soypGvA/evruHlbHXzcnC72ifvaRWTtak"
                + "RywiIxz0VwC3H0exnBKeMUYfKm6ukyR8Xw6AdB04t89/1O/w1cDnyilFU=");
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

        event = EventTestUtil.createDummyTextMessage("wadidaw");
        reply = echoController.handleTextMessageEvent(event);
        assertEquals("Invalid command", reply.getText());

        event = EventTestUtil.createDummyTextMessage(
                "/docs_sim Hello everybody how are you today boy!'"
        + " 'Burger is expensive eat coca cola instead");
        reply = echoController.handleTextMessageEvent(event);
        assertEquals("Incorrect input or maybe your language is not supported."
                + " Try using english and fix your input", reply.getText());


        event = EventTestUtil.createDummyTextMessage(
                "/docs_sim https://en.wikipedia.org/wiki/Wikipedia https://en.wikipedia.org/wiki/Kenn_Whitaker");
        reply = echoController.handleTextMessageEvent(event);
        assertEquals("17.22%", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}