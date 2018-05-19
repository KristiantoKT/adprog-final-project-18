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
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/zonkbot");

        TextMessage reply = zonkbotController.handleTextMessageEvent(event);

        assertEquals("zonkbot activated!", reply.getText());
    }



    @Test
    void testZonkbotController() {
        assertEquals("Answer 1:", zonkbotController.add_question("ayam?"));
        assertEquals("Answer 2:", zonkbotController.add_question("tidak"));
    }

    @Test
    void testZonkbotControllerTextDefault(){
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("ayams");
        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
        assertEquals("ayams", reply.getText());

    }

    @Test
    void testZonkbotControllerZonkbotNotActivated() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_question");
        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
        String replyText = "zonkbot are not available."
                + "To activate zonkbot please type \"/zonkbot\"";
        assertEquals(replyText, reply.getText());

    }

    @Test
    void testZonkbotControllerAddQuestion() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/zonkbot");
        zonkbotController.handleTextMessageEvent(event);
        event = EventTestUtil.createDummyTextMessage("/add_question");
        TextMessage reply = zonkbotController.handleTextMessageEvent(event);
        assertEquals("Please input your question",reply.getText());
    }


    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        zonkbotController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}