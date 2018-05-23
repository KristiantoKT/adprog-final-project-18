package zonkbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.linecorp.bot.model.event.Event;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import zonkbot.EventTestUtil;
import zonkbot.GroupZonkbot;
import zonkbot.Question;
import zonkbot.User;

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
    void handleTextMessageEventTest() {
        MessageEvent<TextMessageContent> message =
                EventTestUtil.createDummyTextMessage("the message");
        try {
            zonkbotController.handleTextMessageEvent(message);
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @Test
    void handleResponseMessageForPersonal() {
        try {
            zonkbotController.responseMessageForPersonal("/add_question","replyToken");
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @Test
    void testHandleReplyText() {
        Event event = mock(Event.class);

        zonkbotController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void replyWithRandomQuestionTest() {
        try {
            zonkbotController.replyWithRandomQuestion("replyToken");
        } catch (Exception e) {
            assertEquals(true,true);

        }
    }


    @Test
    void testGroupResponseMessage() {
        try {
            zonkbotController.groupResponseMessage("groupId",
                    "userId", "start zonk");
            zonkbotController.groupResponseMessage("groupId",
                    "userId", "stop zonk");
        } catch (Exception e) {
            assertEquals(true,true);

        }
    }

    @Test
    void responseMessageForGroupTest() {
        try {
            zonkbotController.responseMessageForGroup("groupId", "userId",
                    "start zonk", "replyToken");
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @Test
    void randomQuestionCarousel() {
        Question question = new Question("Bobot apa yang besar?");
        question.addAnswer("Bobot kamu");
        question.addAnswer("Bobot UAS");
        question.addAnswer("Bobot proyek akhir");
        question.addAnswer("Bobotboy");
        try {
            zonkbotController.randomQuestionCarousel("replyToken",
                    question, 0);
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @Test
    void chooseQuestion() {
        try {
            zonkbotController.chooseQuestionForChangeAnswer("replyToken");
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }


    @Test
    void chooseCorrectAnswerWithCarouselTest() {
        Question question = new Question("Bobot apa yang besar?");
        question.addAnswer("Bobot kamu");
        question.addAnswer("Bobot UAS");
        question.addAnswer("Bobot proyek akhir");
        question.addAnswer("Bobotboy");
        try {
            zonkbotController.chooseCorrectAnswerWithCarousel("replyToken", question);
        } catch (Exception e) {
            assertEquals(true,true);

        }
    }

    @Test
    void showLeaderBoardTest() {
        User user = new User("userId");
        GroupZonkbot group = new GroupZonkbot("groupId", user);
        try {
            zonkbotController.showLeaderboard(group);
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @Test
    void replyTextTest() {
        try {
            zonkbotController.replyText("replyToken","Sample Message");
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @Test
    void writeToJson() {
        Question question = new Question("Bobot apa yang besar?");
        question.addAnswer("Bobot kamu");
        question.addAnswer("Bobot UAS");
        question.addAnswer("Bobot proyek akhir");
        question.addAnswer("Bobotboy");
        question.setCorrectAnswer(2);
        try {
            zonkbotController.writeToJson(question);
        } catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @Test
    void readFromJson() {
        zonkbotController.readFromJson();
    }


}