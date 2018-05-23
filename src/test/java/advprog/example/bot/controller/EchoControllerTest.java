package advprog.example.bot.controller;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.acronym.bot.AcronymOperations;
import advprog.example.bot.BotExampleApplication;
import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class EchoControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Mock
    private LineMessagingClient lineMessagingClient;
    @Autowired
    @InjectMocks
    private EchoController echoController;
    @Mock
    private ServletUriComponentsBuilder servlet;

    @Before
    public void setUp() throws IOException {
        BotExampleApplication.downloadedContentDir = Files.createTempDirectory("line-bot");
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }


    @Test
    public void testContextLoads() {
        assertNotNull(echoController);
    }

    @Test
    public void testHandleTextMessageEvent() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    public void testHandleAddAcronym() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/add_acronym");

        MessageEvent<TextMessageContent> event2 =
                EventTestUtil.createDummyTextMessage("AL");


        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Silakan masukkan kependekan", reply.getText());

        MessageEvent<TextMessageContent> event3 =
                EventTestUtil.createDummyTextMessage("Ayy Lmao");

        reply = echoController.handleTextMessageEvent(event2);

        assertEquals("Silakan masukkan kepanjangan", reply.getText());
        reply = echoController.handleTextMessageEvent(event3);
        assertEquals(event2.getMessage().getText() + " - " + event3.getMessage().getText()
                + " Berhasil ditambah", reply.getText());
    }

    @Test
    public void testHandleDeleteAcronym() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/delete_acronym");

        PostbackEvent event2 = EventTestUtil.createPostbackEvent("Delete 2");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Silakan pilih yang mau "
                + "didelete dari carousel", reply.getText());

        MessageEvent<TextMessageContent> event3 =
                EventTestUtil.createDummyTextMessage("yes");


        echoController.handlePostback(event2);
        verify(lineMessagingClient, atLeastOnce()).replyMessage(new ReplyMessage(
                event2.getReplyToken(), new TextMessage("Are you sure?")
        ));

        reply = echoController.handleTextMessageEvent(event3);
        assertEquals(echoController.yangDicari.getKependekan()
                + " - "
                + echoController.yangDicari.getKepanjangan()
                + " Telah didelete", reply.getText());
        AcronymOperations.add(echoController.yangDicari, echoController.file);
    }

    @Test
    public void testHandleUpdateAcronym() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/update_acronym");

        PostbackEvent event2 = EventTestUtil.createPostbackEvent("Update 2");


        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Silakan pilih yang mau "
                + "diupdate dari carousel", reply.getText());

        MessageEvent<TextMessageContent> event3 =
                EventTestUtil.createDummyTextMessage("I De E");

        String oldKepanjangan = echoController.acronyms.get(2).getKepanjangan();

        echoController.handlePostback(event2);
        verify(lineMessagingClient, atLeastOnce()).replyMessage(new ReplyMessage(
                event2.getReplyToken(), new TextMessage("Silakan masukkan kepanjangan")
        ));

        String oldKepanjangann = oldKepanjangan;

        reply = echoController.handleTextMessageEvent(event3);
        assertEquals(EchoController.yangDicari.getKependekan()
                + " - "
                + EchoController.yangDicari.getKepanjangan()
                + " Berhasil diubah", reply.getText());
        AcronymOperations.update(echoController.yangDicari, oldKepanjangann,
                echoController.file);
    }

    @Test
    public void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}