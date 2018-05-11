package advprog.youTube.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.youTube.bot.YoutubeInfoAppTest;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class YoutubeInfoControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private YoutubeInfoController youTubeInfoController;

    @Test
    void testContextLoads() {
        assertNotNull(youTubeInfoController);
    }

    @Test
    void testHandleTextMessageEvent() throws IOException {
        MessageEvent<TextMessageContent> event =
                YoutubeInfoAppTest.createDummyTextMessage("youtube.com/watch?v=qonMgEaU0ZQ");

        TextMessage reply = youTubeInfoController.handleTextMessageEvent(event);

        assertEquals("Title : SUCRD - NGOMONGIN INSTAGRAM" + "\n" +
                "Channel : Raditya Dika " + "\n" +
                "Viewers : 4,483,237 views " + "\n" +
                "Likes and Dislikes : 106K & 1.5K", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        youTubeInfoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}