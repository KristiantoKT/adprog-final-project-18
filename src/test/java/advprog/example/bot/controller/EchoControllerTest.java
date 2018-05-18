package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import advprog.example.bot.BotExampleApplication;
import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

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

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

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
    public void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    public void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    public void testHandleSttCommand() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/speech-to-text");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Ready to recognize speech. Due to the "
                + "incompleteness of the current program, "
                + "please make sure the sound file is type .wav and "
                + "the length is less than 10 seconds.", reply.getText());
    }

    @Test
    public void testHandleAudioWithoutSttCommand() {
        MessageEvent<AudioMessageContent> event =
                EventTestUtil.createDummyAudioMessage();
        echoController.handleAudioMessageEvent(event);
        verify(lineMessagingClient, times(1))
                .replyMessage(mock(ReplyMessage.class));

    }

}