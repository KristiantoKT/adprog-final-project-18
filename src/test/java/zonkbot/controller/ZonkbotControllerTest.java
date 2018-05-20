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

        @Test
        void testDeactivateZonkbot() {
            deactivateZonkbot();
            zonkbotController.responseMessage("/zonkbot", "skajdhfkjl");
            String reply = zonkbotController.responseMessage("/deactivate", "sakjdfhk");
            assertEquals("zonkbot deactivated, all question will be deleted", reply);
        }

        @Test
        void testHandleTextMessageEvent() {
            deactivateZonkbot();
            String reply = zonkbotController.responseMessage("/zonkbot", "sakjdfhk");
            assertEquals("zonkbot activated!", reply);
        }



        @Test
        void testZonkbotControllerTextDefault() {
            deactivateZonkbot();
            String reply = zonkbotController.responseMessage("/echo ayams", "sakjdfhk");
            assertEquals("ayams", reply);

        }


        @Test
        void testZonkbotControllerAddQuestion() {
            deactivateZonkbot();
            zonkbotController.responseMessage("/zonkbot", "sdfsad");
            String reply = zonkbotController.responseMessage("/add_question", "asdf");
            assertEquals("Please input your question",reply);
        }


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

    @Test
    void testResponseMessageWithQuestion() {
        String reply;
        zonkbotController.responseMessage("/zonkbot","2");
        zonkbotController.responseMessage("/add_question","2");
        zonkbotController.responseMessage("ayam?","2");
        zonkbotController.responseMessage("1","2");
        zonkbotController.responseMessage("2","2");
        reply = zonkbotController.responseMessage("3","2");
        assertEquals("Answer 4:", reply);
    }

    @Test
    void testResponseMessageWithEcho() {
        deactivateZonkbot();
        String reply;
        reply = zonkbotController.responseMessage("/echo ayam","2");
        assertEquals("ayam", reply);
    }
    @Test
    void testResponseMessageWithRandomStringWhileNotActive() {
        String reply;
        deactivateZonkbot();
        reply = zonkbotController.responseMessage("ayam kepo","2");
        String replyText = "zonkbot are not available."
                + "To activate zonkbot please type \"/zonkbot\"";
        assertEquals(replyText, reply);
    }

    @Test
    void testResponseMessageWithRandomStringWhileActive() {
        deactivateZonkbot();
        String reply;
        zonkbotController.responseMessage("/zonkbot", "asdf");
        reply = zonkbotController.responseMessage("ayam","sadlfkj");
        String replyText = "ayam is not a command";
        assertEquals(replyText,reply);
    }
    void deactivateZonkbot() {
        zonkbotController.responseMessage("/deactivate", "sdfh");
    }
}