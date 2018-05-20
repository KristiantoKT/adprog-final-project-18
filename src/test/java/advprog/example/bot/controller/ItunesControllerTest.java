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

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class ItunesControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private ItunesController itunesController;

    @Test
    void testContextLoads() {
        assertNotNull(itunesController);
    }

    @Test
    void testHandleTextMessageEvent() throws IOException, JSONException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = itunesController.handleTextMessageEvent(event);

        assertEquals("Wrong input! Please use /itunes_preview ARTIST", reply.getText());
    }

    @Test
    void testHandleTextMessageEventPreview() throws IOException, JSONException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/itunes_preview Jack Johnson");

        TextMessage reply = itunesController.handleTextMessageEvent(event);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        itunesController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testUrlBuilder() {
        String[] param = {"Jack", "Johnson"};
        String url =  itunesController.urlBuilder(param);
        assertEquals(url, "https://itunes.apple.com/search?term=Jack+Johnson");
    }

    @Test
    void testGetSongInformation() throws IOException, JSONException {
        String[] param = {"Jack", "Johnson"};
        JSONObject dummy = itunesController.connectApi(param);
        ItunesController.SongInformation test = itunesController.getSongInformation(dummy);
        assertNotNull(test);
    }

}
