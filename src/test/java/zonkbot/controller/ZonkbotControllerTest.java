package zonkbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.linecorp.bot.model.event.Event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


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
            zonkbotController.zonkbot.responseMessage("/zonkbot", "skajdhfkjl", zonkbotController);
            String reply = zonkbotController.zonkbot.responseMessage("/deactivate", "sakjdfhk", zonkbotController);
            assertEquals("zonkbot deactivated, all question will be deleted", reply);
        }

        @Test
        void testHandleTextMessageEvent() {
            deactivateZonkbot();
            String reply = zonkbotController.zonkbot.responseMessage("/zonkbot", "sakjdfhk", zonkbotController);
            assertEquals("zonkbot activated!", reply);
        }



        @Test
        void testZonkbotControllerTextDefault() {
            deactivateZonkbot();
            String reply = zonkbotController.zonkbot.responseMessage("/echo ayams", "sakjdfhk", zonkbotController);
            assertEquals("ayams", reply);

        }


        @Test
        void testZonkbotControllerAddQuestion() {
            deactivateZonkbot();
            zonkbotController.zonkbot.responseMessage("/zonkbot", "sdfsad", zonkbotController);
            String reply = zonkbotController.zonkbot.responseMessage("/add_question", "asdf", zonkbotController);
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
        zonkbotController.zonkbot.responseMessage("/zonkbot","2", zonkbotController);
        zonkbotController.zonkbot.responseMessage("/add_question","2", zonkbotController);
        zonkbotController.zonkbot.responseMessage("ayam?","2", zonkbotController);
        zonkbotController.zonkbot.responseMessage("1","2", zonkbotController);
        zonkbotController.zonkbot.responseMessage("2","2", zonkbotController);
        reply = zonkbotController.zonkbot.responseMessage("3","2", zonkbotController);
        assertEquals("Answer 4:", reply);
    }

    @Test
    void testResponseMessageWithEcho() {
        deactivateZonkbot();
        String reply;
        reply = zonkbotController.zonkbot.responseMessage("/echo ayam","2", zonkbotController);
        assertEquals("ayam", reply);
    }
    @Test
    void testResponseMessageWithRandomStringWhileNotActive() {
        String reply;
        deactivateZonkbot();
        reply = zonkbotController.zonkbot.responseMessage("ayam kepo","2", zonkbotController);
        String replyText = "zonkbot are not available."
                + "To activate zonkbot please type \"/zonkbot\"";
        assertEquals(replyText, reply);
    }

    @Test
    void testResponseMessageWithRandomStringWhileActive() {
        deactivateZonkbot();
        String reply;
        zonkbotController.zonkbot.responseMessage("/zonkbot", "asdf", zonkbotController);
        reply = zonkbotController.zonkbot.responseMessage("ayam","sadlfkj", zonkbotController);
        String replyText = "ayam is not a command";
        assertEquals(replyText,reply);
    }
    void deactivateZonkbot() {
        zonkbotController.zonkbot.responseMessage("/deactivate", "sdfh", zonkbotController);
    }
}