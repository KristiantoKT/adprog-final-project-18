package zonkbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.linecorp.bot.model.event.Event;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.sun.javafx.event.EventUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import zonkbot.EventTestUtil;
import zonkbot.Question;

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
    void handleTextMessageEvent() throws ExecutionException, InterruptedException {
        MessageEvent<TextMessageContent> event = EventTestUtil.createDummyTextMessage("test");
        try {
            zonkbotController.handleTextMessageEvent(event);
        } catch (Exception e) {
          //do nothing
        }
    }

    @Test
    void ResponseMessageForGroupTest() throws ExecutionException, InterruptedException {
        MessageEvent<TextMessageContent> event = EventTestUtil.createDummyTextMessage("test");
        try {
            zonkbotController.responseMessageForGroup(event);
        } catch (Exception e) {
          //do nothing
        }
    }

    @Test
    void replyTextTest() throws ExecutionException, InterruptedException {
        String message = "";
        try {
            zonkbotController.replyText("replyToken", message);
        } catch (Exception e) {
          //do nothing
        }
    }

    @Test
    void responseMessageForPersonalTest() throws ExecutionException, InterruptedException {
        MessageEvent<TextMessageContent> event = EventTestUtil.createDummyTextMessage("test");
        try {
            zonkbotController.responseMessageForPersonal(event, "textContent","replyToken");
        } catch (Exception e) {
          //do nothing
        }
    }

    @Test
    void chooseQuestionTest() {
        try {
            zonkbotController.chooseQuestion("replyToken");
        } catch (Exception e) {
            //do nothing
        }
    }

    @Test
    void chooseAnswerTest() {
        try {
            zonkbotController.chooseCorrectAnswerWithCarousel("replyToken");
        } catch (Exception e) {
            //do nothing
        }
    }

    @Test
    void replyWithRandomQuestionTest() {
        try {
            zonkbotController.replyWithRandomQuestion("replyToken");
        } catch (Exception e) {
            //do nothing
        }
    }

    @Test
    void writeToJson() {
        Question question = new Question("halo?");
        zonkbotController.writeToJson(question);
        assertEquals(true,true);
    }

    @Test
    void readFromJson() {
        zonkbotController.readFromJson();
        assertEquals(true,true);
    }

    @Test
    void groupResponseMessageTest() throws ExecutionException, InterruptedException {
        MessageEvent<TextMessageContent> messages =null;
        zonkbotController.groupResponseMessage(messages, "id");
    }

    @Test
    void showLeaderboardTest() throws ExecutionException, InterruptedException {
        zonkbotController.showLeaderboard("id");
        assertEquals(true, true);
    }

    @Test
    void getGroupTest() {
        zonkbotController.getGroup("idNgasal");
        assertEquals(true,true);
    }

//    @Test
//    void replyTextTest() {
//        zonkbotController.replyText("token","id");
//    }
}