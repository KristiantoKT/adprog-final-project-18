package resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import resources.ImageAnalyzerController;

@SpringBootTest(classes = ImageAnalyzerController.class)
//@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class ImageAnalyzerControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private ImageAnalyzerController imageAnalyzerController;

    @Test
    void testContextLoads() {
        assertNotNull(imageAnalyzerController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = imageAnalyzerController.handleTextMessageEvent(event);

        assertEquals("ngomong apa sih bro", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        imageAnalyzerController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }


    ImageAnalyzer ia = new ImageAnalyzer(null);

    @Test
    void objectImageAnalyzer() {
        assertEquals(new ImageAnalyzer(null).getImage(), ia.getImage());
    }

    @Test
    void getImageAnalyzerString() {
        assertEquals("", ia.getResult());
    }
}