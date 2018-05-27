package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import advprog.randomarticlemediawiki.MediaWikiStorage;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class EchoControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Mock
    private LineMessagingClient lineMessagingClient;
    @InjectMocks
    private EchoController echoController;

    @Test
    void testContextLoads() {
        assertNotNull(echoController);
    }

    @Test
    void testHandleTextMessageEventEcho() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = (TextMessage) echoController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    void testHandleTextMessageEventAddUrl() {
        MediaWikiStorage.hapusFile();
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_wiki https://marvel.wikia.com/api.php");

        TextMessage reply = (TextMessage) echoController.handleTextMessageEvent(event);

        assertEquals("https://marvel.wikia.com/api.php berhasil ditambahkan", reply.getText());
        MediaWikiStorage.hapusFile();
    }

    @Test
    void testHandleTextMessageEventAddUrlFail() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_wiki https://marvel.wikia.com/api.php");

        TextMessage reply = (TextMessage) echoController.handleTextMessageEvent(event);
        reply = (TextMessage) echoController.handleTextMessageEvent(event);

        assertEquals("https://marvel.wikia.com/api.php sudah ada", reply.getText());
        MediaWikiStorage.hapusFile();
    }

    @Test
    void testHandleTextMessageEventRandomArticle() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/random_wiki_article");

        TemplateMessage hasil = (TemplateMessage) echoController.handleTextMessageEvent(event);
        assertTrue(hasil != null);
    }

    @Test
    void testPostBackEvent() throws Exception {
        PostbackEvent event =
                EventTestUtil.createDummyPostback();

        List<Message> hasil = echoController.handlePostbackEvent(event);
        assertTrue(!hasil.isEmpty());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}