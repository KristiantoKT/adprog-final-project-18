package advprog.popularhotcountry.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.popularhotcountry.bot.EventTestUtil;
import advprog.popularhotcountry.bot.controller.HotCountry;

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
public class PopularHotCountryControllerTest {
    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private PopularHotCountryController popularController;

    @Test
    void  textContextLoads() {
        assertNotNull(popularController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard bill200");

        TextMessage replyFix = popularController.handleTextMessageEvent(event);
        assertNotNull(replyFix);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        popularController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testInswitch() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard bill200");
        TextMessage replyFix = popularController.handleTextMessageEvent(event);
        assertNotNull(replyFix);

    }


}
