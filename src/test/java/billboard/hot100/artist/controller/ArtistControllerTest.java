package billboard.hot100.artist.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

import billboard.hot100.artist.EventTestUtil;
import billboard.hot100.artist.parser.Hot100Parser;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class ArtistControllerTest {
    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private ArtistController artistController;

    @Test
    void testContextLoads() {
        assertNotNull(artistController);
    }

    @Test
    void testHandleErrorEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard hot100 Faraya");
        TextMessage reply = artistController.handleMessageEvent(event);
        assertEquals("Sorry, your artist doesn't make it to Hot 100 chart", reply.getText());
    }

    @Test
    void testHandleSuccessEvent() {
        Hot100Parser parser = new Hot100Parser();
        ArrayList<String> arrayArtist = parser.getArrayArtist();
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard hot100 " + arrayArtist.get(0));
        TextMessage reply = artistController.handleMessageEvent(event);
        assertNotNull(reply.getText());
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");
        TextMessage reply = artistController.handleMessageEvent(event);
        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);
        artistController.handleDefaultMessage(event);
        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}