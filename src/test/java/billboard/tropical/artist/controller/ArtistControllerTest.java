package billboard.tropical.artist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

import billboard.tropical.artist.EventTestUtil;
import billboard.tropical.artist.parser.TropicalParser;

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
    void testHandleTErrorEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard tropical Dummy");
        TextMessage reply = artistController.handleMessageEvent(event);
        assertEquals("Sorry, your artist doesn't make it to tropical chart", reply.getText());
    }

    @Test
    void testHandleSuccessEvent() {
        TropicalParser parser = new TropicalParser();
        ArrayList<String> arrayArtist = parser.getArrayArtist();
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard tropical " + arrayArtist.get(0));
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
