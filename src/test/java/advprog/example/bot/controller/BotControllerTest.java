package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import java.util.regex.Pattern;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class BotControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private BotController botController;

    @Autowired
    private DiceSimulationComposer diceComposer;

    @Test
    void testContextLoads() {
        assertNotNull(botController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = botController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    void testCoinFeature() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/coin");

        TextMessage reply = botController.handleTextMessageEvent(event);

        boolean isTailOrHead = Pattern.match("[Hh]ead|[Tt]ail", reply.getText());

        assertTrue(isTailOrHead);
    }

    @Test
    void testCoinFunction() {
        String coinFlip = DiceSimulation.getCoinFlip();

        boolean isTailOrHead = Pattern.match("[Hh]ead|[Tt]ail", coinFlip);

        assertTrue(isTailOrHead);
    }

    @Test
    void testRollFeature() {
        // Result: 10d100 (25, 66, 57, 88, 83, 47, 84, 24, 74, 82)
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/roll 10d100");

        TextMessage reply = botController.handleTextMessageEvent(event);

        boolean formatStringCorrect = Pattern.match("Result: 10d100 \\("
                + "(\\d{1,3}, ){9}, \\d{1,3}\\)", reply.getText());

        assertTrue(formatStringCorrect);
    }

    @Test
    void testMultirollFeature() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/multiroll 10 2d6");

        TextMessage reply = botController.handleTextMessageEvent(event);

        boolean formatStringCorrect;
        for (String line: reply.getText().split("\n")) {
            formatStringCorrect = Pattern.match("2d6 \\((\\d, \\d\\)", line);

            assertTrue(formatStringCorrect);
        }
    }

    @Test
    void testIsLuckyFeature() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_lucky 5 10d10");

        TextMessage reply = botController.handleTextMessageEvent(event);

        boolean isLucky = Pattern.match("5 appears (\\d|10)");
        boolean isUnlucky = Pattern.match("I'm not lucky");

        assertTrue(isLucky || isUnlucky);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        botController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}
