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
    void testHandleTextMessageGoldClassEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_gold_class");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals(new StringBuilder()
                .append("(' DEADPOOL 2',[11:15 13:55])\n")
                .append("(' SOLO: A STAR WARS STORY',[16:35 19:30 22:25])\n"), reply.getText());
    }

    @Test
    void testHandleTextMessageRegular2dEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_regular_2d");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals(new StringBuilder()
                .append("(' DEADPOOL 2',[10:45 13:30 16:00 18:35 21:10 23:45])\n\n")
                .append("(' ANON ',[12:40 17:05])\n")
                .append("(' SUBMERGENCE',[12:10 14:30 16:55 19:20 21:45])\n")
                .append("(' SOLO: A STAR WARS STORY',[15:00])\n")
                .append("(' GHOST STORIES',[14:50 19:15 21:25])\n")
                .append("(' AVENGERS: INFINITY WAR',[11:50 17:45 20:50])\n")
                .append("(' 3 AM PART 3',[11:30 13:40 15:45 18:00 20:15 22:30])\n"), reply.getText());
    }

    @Test
    void testHandleTextMessage4DxCinemaEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_4dx_3d_cinema");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("('4DX2D DEADPOOL 2',[10:50 13:25])\n", reply.getText());
    }

    @Test
    void testHandleTextMessageVelvetClassEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_velvet");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals(new StringBuilder()
                .append("(' DEADPOOL 2',[12:45])\n")
                .append("(' SOLO: A STAR WARS STORY',[15:30 18:25 21:20])\n"),reply.getText());
    }

    @Test
    void testHandleTextMessageSweetboxClassEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/cgv_sweet_box");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals(new StringBuilder()
                .append("(' DEADPOOL 2',[12:00 14:35])\n")
                .append("(' SOLO: A STAR WARS STORY',[17:10 20:00 22:50])\n"), reply.getText());
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