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
                EventTestUtil.createDummyTextMessage("/echo Lorem ipsum");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem ipsum", reply.getText());
    }

    @Test
    void testHandleTextMessageGoldClassEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_gold_class");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("(' DEADPOOL 2',[11:35 14:15 17:00 19:45 22:30])\n", reply.getText());
    }

    @Test
    void testHandleTextMessageRegular2dEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_regular_2d");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals(new StringBuilder()
                .append("(' DEADPOOL 2',[10:30 13:10 15:50 18:30 21:10 23:50])\n")
                .append("(' VFF: SAMA JUGA BOHONG ',[14:00])\n").toString(), reply.getText());
    }

    @Test
    void testHandleTextMessage4DxCinemaEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_4dx_3d_cinema");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("('4DX2D DEADPOOL 2',[11:15 13:45 16:20 19:00 21:35])\n", reply.getText());
    }

    @Test
    void testHandleTextMessageVelvetClassEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_velvet");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("(' DEADPOOL 2',[10:45 13:30 16:10 18:50 21:30 24:10])\n", reply.getText());
    }

    @Test
    void testHandleTextMessageSweetboxClassEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_sweet_box");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("(' DEADPOOL 2',[10:30 13:10 15:50 18:30 21:10 23:50])\n", reply.getText());
    }

    @Test
    void testHandleTextMessageChangeCinemaEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil
                        .createDummyTextMessage("/cgv_change_cinema https://www.cgv.id/en/schedule/cinema/037");


        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Cinema default change from Grand Indonesia to Aeon Mall", reply.getText());
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