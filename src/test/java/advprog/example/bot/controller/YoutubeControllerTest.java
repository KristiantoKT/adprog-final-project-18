package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                + yt.getLikes(body) + " & " + yt.getDislikes(body);

        MessageEvent<TextMessageContent> event1 =
                EventTestUtil.createDummyTextMessage("/youtube https://www.youtube.com/watch?v=kJ5PCbtiCpk");
        TextMessage reply1 = youtubeController.handleTextMessageEvent(event1);
        assertEquals(title + channel + viewers + likesDislikes, reply1.getText());

        // untuk kasus error
        TextMessage errorMessage = yt.returnErrorMessage();
        MessageEvent<TextMessageContent> event2 =
                EventTestUtil.createDummyTextMessage("/error message");
        TextMessage reply2 = youtubeController.handleTextMessageEvent(event2);
        assertEquals(errorMessage.getText(), reply2.getText());

        MessageEvent<TextMessageContent> event3 =
                EventTestUtil.createDummyTextMessage("/youtube https://google.com/");
        TextMessage reply3 = youtubeController.handleTextMessageEvent(event3);
        assertEquals(errorMessage.getText(), reply3.getText());
    }


    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        youtubeController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}