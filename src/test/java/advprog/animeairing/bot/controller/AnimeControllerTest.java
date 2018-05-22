package advprog.animeairing.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.animeairing.bot.EventTestUtil;
import advprog.animeairing.bot.controller.AnimeController;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class AnimeControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private AnimeController animeController;

    @Test
    void testContextLoads() {
        assertNotNull(animeController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard hotcountry ArtisNya");

        TextMessage reply = animeController.handleTextMessageEvent(event);

        assertEquals("Please use a correct input E.g /is_airing ANIMETITLE", reply.getText());

        event = EventTestUtil.createDummyTextMessage("hari ini nonton apa ya");

        reply = animeController.handleTextMessageEvent(event);

        assertNotNull(reply);

        event = EventTestUtil.createDummyTextMessage("/is_airing naruto");

        reply = animeController.handleTextMessageEvent(event);

        assertEquals("Naruto has finished airing at 2007-02-08",
                reply.getText());

    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        animeController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }


}
