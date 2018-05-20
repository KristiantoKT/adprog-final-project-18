package zonkbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import zonkbot.EventTestUtil;

import java.util.ArrayList;


@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class ZonkbotControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }


    @Autowired
    private ZonkbotController zonkbotController;

    @Test
    void testContextLoads() {
        assertNotNull(zonkbotController);
    }

    //    @Test
    //    void testDeactivateZonkbot() {
    //        deactivateZonkbot();
    //        MessageEvent<TextMessageContent> event =
    //                EventTestUtil.createDummyTextMessage("/deactivate_zonkbot");
    //        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
    //        assertEquals("zonkbot deactivated", reply.getText());
    //    }

    //    @Test
    //    void testHandleTextMessageEvent() {
    //        deactivateZonkbot();
    //        MessageEvent<TextMessageContent> event =
    //                EventTestUtil.createDummyTextMessage("/zonkbot");
    //        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
    //        assertEquals("zonkbot activated!", reply.getText());
    //    }



    //    @Test
    //    void testZonkbotControllerTextDefault() {
    //        deactivateZonkbot();
    //        MessageEvent<TextMessageContent> event =
    //                EventTestUtil.createDummyTextMessage("/echo ayams");
    //        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
    //        assertEquals("ayams", reply.getText());
    //
    //    }

    //    @Test
    //    void testZonkbotControllerZonkbotNotActivated() {
    //        deactivateZonkbot();
    //        MessageEvent<TextMessageContent> event =
    //                EventTestUtil.createDummyTextMessage("/add_question");
    //        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
    //        String replyText = "zonkbot are not available."
    //                + "To activate zonkbot please type \"/zonkbot\"";
    //        assertEquals(replyText, reply.getText());
    //
    //    }

    //    @Test
    //    void testZonkbotControllerAddQuestion() {
    //        deactivateZonkbot();
    //        MessageEvent<TextMessageContent> event =
    //                EventTestUtil.createDummyTextMessage("/zonkbot");
    //        zonkbotController.handleTextMessageEvent(event);
    //        event = EventTestUtil.createDummyTextMessage("/add_question");
    //        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
    //        assertEquals("Please input your question",reply.getText());
    //    }


    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        zonkbotController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testHandleReplyText() {
        Event event = mock(Event.class);

        zonkbotController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

//    @Test
//    void testZonkbotToString() {
//        Event event = mock(Event.class);
//        String reply;
//        zonkbotController.responseMessage("/zonkbot");
//        zonkbotController.responseMessage("/add_question");
//        zonkbotController.responseMessage("ayam?");
//        zonkbotController.responseMessage("1");
//        zonkbotController.responseMessage("2");
//        zonkbotController.responseMessage("3");
//        reply = zonkbotController.responseMessage("4");
//        assertEquals("ayam?\n    1\n    2\n    3\n    4", reply);
//    }

    void deactivateZonkbot() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/deactivate_zonkbot");
        zonkbotController.handleTextMessageEvent(event);
    }
}