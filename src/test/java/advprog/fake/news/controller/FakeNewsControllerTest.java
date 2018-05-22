package advprog.fake.news.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.fake.news.EventTestUtil;
import advprog.fake.news.FakeNewsApplication;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)

public class FakeNewsControllerTest {
    static {
        System.setProperty("line.bot.channelSecret", "594f30c554eb4fd226841c1f6ce9d41e");
        System.setProperty("line.bot.channelToken", "og0DgJjZLiCq4aNWSwpU9kCL5QP6v3VygBd2vjiT"
                + "21sQL8QJCFCHxoAICxY/L/oebhEYQlAaX+aUAc9zdZfu80xiAdo7JN7+yNver+1zxvWYrOO"
                + "HXgLBpkAnhG6w6iCLw/4/SRFYOYznD26VS7e9NwdB04t89/1O/w1cDnyilFU=");
    }

    @Autowired
    private FakeNewsController fakeNewsController;

    @Test
    void testContextLoads() {
        assertNotNull(fakeNewsController);
    }

    @Test
    void testHandleTextMessageCheckNull() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_fake boboboi.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "URL doesn't exist");
    }

    @Test
    void testHandleTextMessageGroupMessageTest() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextGroupMessage("boboboi.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "URL doesn't exist");
    }

    @Test
    void testHandleTextMessageGroupMessageTestHttp() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextGroupMessage("http://boboboi.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "URL doesn't exist");
    }

    @Test
    void testHandleTextMessageGroupMessageTestAllType() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextGroupMessage("weeklyworldnews.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "weeklyworldnews.com is fake, rumor,and clickbait");
    }

    @Test
    void testHandleTextMessageGroupMessageTestTwoType() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextGroupMessage("vigilantcitizen.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "vigilantcitizen.com is fake and conspiracy");
    }

    @Test
    void testHandleTextMessageGroupMessageTestOneType() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextGroupMessage("worldrumor.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "worldrumor.com is fake");
    }

    @Test
    void testHandleTextMessageEventIsFake1() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_fake 16wmpo.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsFake2() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_fake conservativespirit.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsFake3() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_fake BB4SP.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventNotFake() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_fake abovetopsecret.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "False");
    }

    @Test
    void testHandleTextMessageEventIsConspiracy1() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_conspiracy abovetopsecret.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsConspiracy2() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_conspiracy clashdaily.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsConspiracy3() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_conspiracy truthfeed.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsNoConspiracy() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_conspiracy returnofkings.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "False");
    }

    @Test
    void testHandleTextMessageEventIsSatire1() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_satire dailyleak.org");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsSatire2() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_satire wtoe5news.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsSatire3() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_satire americannewsx.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "True");
    }

    @Test
    void testHandleTextMessageEventIsSatireWithHttp() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_satire http://conservativespirit.com");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "False");
    }

    @Test
    void testHandleTextMessageEventAddFilter() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter fake.com bias");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "URL doesn't exist");
    }

    @Test
    void testHandleTextMessageEventAddFilterWithHttp() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter http://www.fake.com bias");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "URL doesn't exist");
    }


    @Test
    void testHandleTextMessageEventAddFilterType1Same() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter 100percentfedup.com bias");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "filter is already present");
    }

    @Test
    void testHandleTextMessageEventAddFilterType2() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter 100percentfedup.com conspiracy");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "added");
    }

    @Test
    void testHandleTextMessageEventAddFilterType2Same() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter "
                        + "ushealthyadvisor.com conspiracy");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "filter is already present");
    }

    @Test
    void testHandleTextMessageEventAddFilterType3Same() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter "
                        + "conservativefiringline.com clickbait");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "filter is already present");
    }

    @Test
    void testHandleTextMessageEventAddFilterType3() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter "
                        + "4threvolutionarywar.wordpress.com clickbait");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "added");
    }

    @Test
    void testHandleTextMessageEventAddFilterFull() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_filter "
                        + "conservativefiringline.com satire");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "criteria full");
    }

    @Test
    void testErrorMessageEvent() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/wawiwu");

        TextMessage reply = fakeNewsController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "Command not found!");
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        fakeNewsController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    public void applicationContextTest() {
        FakeNewsApplication.main(new String[]{});
    }

}
