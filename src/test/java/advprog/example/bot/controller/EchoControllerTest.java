package advprog.example.bot.controller;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import advprog.example.bot.BotExampleApplication;
import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import com.linecorp.bot.model.response.BotApiResponse;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

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

        MessageEvent<TextMessageContent> event3 =
                EventTestUtil.createDummyTextMessage("Ayy Lmao");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Silakan masukkan kependekan", reply.getText());

        reply = echoController.handleTextMessageEvent(event2);

        assertEquals("Silakan masukkan kepanjangan", reply.getText());
        reply = echoController.handleTextMessageEvent(event3);
        assertEquals(event2.getMessage().getText() + " - " + event3.getMessage().getText()
                + " Berhasil ditambah", reply.getText());


    }

    @Test
    public void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    public void testHandleAudioWithoutSttCommand() {
        EchoController.canDoMethod = false;
        MessageEvent<AudioMessageContent> event =
                EventTestUtil.createDummyAudioMessage();
        when(lineMessagingClient.replyMessage(new ReplyMessage(
                "replyToken",
                singletonList(new TextMessage("tulis /speech-to-text "
                        + "terlebih dahulu")))
        )).thenReturn(CompletableFuture.completedFuture(
                new BotApiResponse("ok", Collections.emptyList())
        ));
        echoController.handleAudioMessageEvent(event);
        verify(lineMessagingClient).replyMessage(new ReplyMessage("replyToken",
                singletonList(new TextMessage("tulis /speech-to-text "
                        + "terlebih dahulu"))));
    }

    @Test
    public void testHandleSttCommand() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/speech-to-text");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Ready to recognize speech. Due to the "
                + "incompleteness of the current program, "
                + "please make sure the sound file is type .wav and "
                + "the length is less than 10 seconds.", reply.getText());
        EchoController.canDoMethod = false;
    }

    @Test
    public void testHandleAudioWithSttCommand() throws IOException {
        EchoController.canDoMethod = false;
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/speech-to-text");
        MessageEvent<AudioMessageContent> event2 =
                EventTestUtil.createDummyAudioMessage();
        TextMessage response = echoController.handleTextMessageEvent(event);
        assertEquals(response.getText(), "Ready to recognize speech. Due to the "
                + "incompleteness of the current program, "
                + "please make sure the sound file is type .wav and "
                + "the length is less than 10 seconds.");
        try {
            //kosong
            echoController.handleAudioMessageEvent(event2);
        } catch (NullPointerException e) {
            return;
        }
    }
//
//    @Test
//    public void saveContentTestFail()  {
//        String tmp = "tmp";
//        InputStream is = new ByteArrayInputStream(tmp.getBytes());
//        EchoController.saveContent(MessageContentResponse.builder().stream(is).build());
//    }


}