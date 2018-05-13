package advprog.billboard.japan.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.billboard.japan.BillboardJapanApplication;
import advprog.billboard.japan.EventTestUtil;

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
public class BillboardJapanControllerTest {
    static {
        System.setProperty("line.bot.channelSecret", "594f30c554eb4fd226841c1f6ce9d41e");
        System.setProperty("line.bot.channelToken", "og0DgJjZLiCq4aNWSwpU9kCL5QP6v3VygBd2vjiT"
                + "21sQL8QJCFCHxoAICxY/L/oebhEYQlAaX+aUAc9zdZfu80xiAdo7JN7+yNver+1zxvWYrOO"
                + "HXgLBpkAnhG6w6iCLw/4/SRFYOYznD26VS7e9NwdB04t89/1O/w1cDnyilFU=");

    }

    @Autowired
    private BillboardJapanController billboardJapanController;

    @Test
    void testContextLoads() {
        assertNotNull(billboardJapanController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard japan100");

        TextMessage reply = billboardJapanController.handleTextMessageEvent(event);

        assertTrue(reply.getText().contains("(1)") && reply.getText().contains("(10)"));
    }

    @Test
    void testErrorMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/wawiwu");

        TextMessage reply = billboardJapanController.handleTextMessageEvent(event);

        assertEquals(reply.getText(), "Command not found!");
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        billboardJapanController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    public void applicationContextTest() {
        BillboardJapanApplication.main(new String[]{});
    }

}
