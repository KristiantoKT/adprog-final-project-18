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

import com.sun.org.apache.xpath.internal.operations.String;
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
                EventTestUtil.createDummyTextMessage("/echo Lorem ipsum");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem ipsum", reply.getText());
    }

    @Test
    void testHandleTextMessage4DxCinemaEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_4dx_3d_cinema");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("('4DX2D DEADPOOL 2',[10:50 13:25])\n", reply.getText());
    }

    @Test
    void testHandleTextMessageChangeCinemaEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil
                        .createDummyTextMessage("/cgv_change_cinema https://www.cgv.id/en/schedule/cinema/037");


        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Yay ! Your Cinema change from Grand Indonesia to Aeon Mall", reply.getText());
    }

    @Test
    void testHandleTextMessageInvalidChangeCinemaEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil
                        .createDummyTextMessage("/cgv_change_cinema https://cgv.id/en/membership");


        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Url is invalid", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}