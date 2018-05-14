package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.jsoup.nodes.Element;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;




@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class YoutubeControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private YoutubeController youtubeController;

    @Test
    void testContextLoads() {
        assertNotNull(youtubeController);
    }

    @Test
    void testHandleTextMessageEvent() throws IOException {
        YoutubeController yt = new YoutubeController();
        Element body = null;


        try {
            body = yt.youtubeHtml("https://www.youtube.com/watch?v=kJ5PCbtiCpk");

        } catch (IOException e) {
            e.printStackTrace();
        }

        String title = "Title : Gordon Ramsay Answers Cooking Questions "
                + "From Twitter | Tech Support | WIRED" + "\n";
        String channel = "Channel : " + yt.getChannel(body) + "\n";
        String viewers = "Viewers : " + yt.getViewers(body) + "\n";
        String likesDislikes = "Likes and Dislikes : "
                + yt.getLikes(body) + " & " + yt.getDislikes(body) + "\n";

        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/youtube https://www.youtube.com/watch?v=kJ5PCbtiCpk");

        TextMessage reply = youtubeController.handleTextMessageEvent(event);

        assertEquals(title + channel + viewers
                + likesDislikes, reply.getText());
    }

    void testHandleErrorMessage() throws IOException {
        YoutubeController yt = new YoutubeController();
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/error message");

        TextMessage reply = youtubeController.handleTextMessageEvent(event);

        TextMessage errorMessage = yt.returnErrorMessage();

        assertEquals(errorMessage.getText(), reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        youtubeController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}