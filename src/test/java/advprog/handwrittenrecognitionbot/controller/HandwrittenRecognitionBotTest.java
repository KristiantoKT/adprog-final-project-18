package advprog.handwrittenrecognitionbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import advprog.example.bot.controller.EchoController;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class HandwrittenRecognitionBotTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private HandwrittenRecognitionBot handwrittenRecognitionBot;

    @Test
    void testContextLoads() {
        assertNotNull(handwrittenRecognitionBot);
    }

    @Test
    void testHandleTextMessageEvent() {
        handwrittenRecognitionBot.setUrlImage("https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Cursive_Writing_on_Notebook_paper.jpg/800px-Cursive_Writing_on_Notebook_paper.jpg");
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("ocr this");

        TextMessage reply = handwrittenRecognitionBot.handleTextMessageEvent(event);
        String expected = "dog\nThe quick brown fox jumps over the lazy\nPack "
                + "my box with five dozen liquor jugs\n";
        assertEquals(expected, reply.getText());
    }
}
