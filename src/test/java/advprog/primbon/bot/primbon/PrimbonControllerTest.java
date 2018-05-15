package advprog.primbon.bot.primbon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.PrimbonEventTestUtil;
import advprog.primbon.bot.controller.PrimbonController;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class PrimbonControllerTest {
    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private PrimbonController primbonController;

    @Test
    void testContextLoads() {
        assertNotNull(primbonController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                PrimbonEventTestUtil.createDummyTextMessage("/primbon 1981-09-12");

        TextMessage reply = primbonController.handleTextMessageEvent(event);

        assertEquals("Sabtu Legi", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        primbonController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void tanggalJawaTest() throws ParseException {
        assertEquals("Sabtu Legi", primbonController.tanggalJawa("1981-09-12"));
    }


    @Test
    void hariTest() {
        assertEquals("Minggu", primbonController.days(1));
        assertEquals("Senin", primbonController.days(2));
        assertEquals("Selasa", primbonController.days(3));
        assertEquals("Rabu", primbonController.days(4));
        assertEquals("Kamis", primbonController.days(5));
        assertEquals("Jumat", primbonController.days(6));
        assertEquals("Sabtu", primbonController.days(7));
    }

    @Test
    void wetonTest() {
        assertEquals("Pon", primbonController.weton(0));
    }
}
