package advprog.primbon.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.PrimbonEventTestUtil;

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
    void testHandleWrongDateTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                PrimbonEventTestUtil.createDummyTextMessage("/primbon kemashahaha");

        TextMessage reply = primbonController.handleTextMessageEvent(event);

        assertEquals("input tanggal salah, input berupa /primbon yyyy-mm-dd", reply.getText());
    }

    @Test
    void testHandleWrongInputTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                PrimbonEventTestUtil.createDummyTextMessage("/priansdknsadkn");

        TextMessage reply = primbonController.handleTextMessageEvent(event);

        assertEquals("Salah keyword, keyword berupa /primbon tanggal", reply.getText());
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
        assertEquals("Selasa Kliwon", primbonController.tanggalJawa("2018-05-15"));
    }


    @Test
    void hariTest() {
        assertEquals("Selasa", primbonController.hariHelper(0));
        assertEquals("Rabu", primbonController.hariHelper(1));
        assertEquals("Kamis", primbonController.hariHelper(2));
        assertEquals("Jumat", primbonController.hariHelper(3));
        assertEquals("Sabtu", primbonController.hariHelper(4));
        assertEquals("Minggu", primbonController.hariHelper(5));
        assertEquals("Senin", primbonController.hariHelper(6));
    }

    @Test
    void wetonTest() {
        assertEquals("Pahing", primbonController.wetonHelper(0));
        assertEquals("Pon", primbonController.wetonHelper(1));
        assertEquals("Wage", primbonController.wetonHelper(2));
        assertEquals("Kliwon", primbonController.wetonHelper(3));
        assertEquals("Legi", primbonController.wetonHelper(4));
    }
}
