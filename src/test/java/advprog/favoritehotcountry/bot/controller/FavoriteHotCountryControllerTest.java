package advprog.favoritehotcountry.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.favoritehotcountry.bot.EventTestUtil;
import advprog.favoritehotcountry.bot.controller.FavoriteHotCountryController;

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
public class FavoriteHotCountryControllerTest {


    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private FavoriteHotCountryController favoriteController;

    @Test
    void testContextLoads() {

        assertNotNull(favoriteController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard hotcountry ArtisNya");

        TextMessage reply = favoriteController.handleTextMessageEvent(event);

        assertEquals("ArtisNya", reply.getText());

        event = EventTestUtil.createDummyTextMessage("/billboard hotcountry LANCO");

        reply = favoriteController.handleTextMessageEvent(event);

        assertNotNull(reply);

        event = EventTestUtil.createDummyTextMessage("/blahblahblah ini test salah hehe");

        reply = favoriteController.handleTextMessageEvent(event);

        assertEquals("Please use a correct input E.g /billboard hotcountry artistname",
                reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        favoriteController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

}


