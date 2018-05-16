package billboard.newage.artist.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

import billboard.newage.artist.EventTestUtil;
import billboard.newage.artist.parser.NewAgeParser;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


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
                EventTestUtil.createDummyTextMessage("/billboard newage Dummy");
        TextMessage reply = artistController.handleMessageEvent(event);
        assertEquals("Sorry, your artist doesn't make it to new age chart", reply.getText());
    }

    @Test
    void testHandleSuccessEvent() {
        NewAgeParser parser = new NewAgeParser();
        ArrayList<String> arrayArtist = parser.getArtistsArray();
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard newage " + arrayArtist.get(0));
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
