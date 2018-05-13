package advprog.example.bot.controller;

import advprog.example.bot.EventTestUtil;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class OriconControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    private String sampleWeeklyOutput = "Top 10!"
            + "\n(1) シンクロニシティ - 乃木坂46 - 2018-04-25\n"
            + "(2) Fandango - THE RAMPAGE from EXILE TRIBE - 2018-04-25\n"
            + "(3) Fiction e.p - sumika - 2018-04-25\n"
            + "(4) Bumblebee - Lead - 2018-04-25\n"
            + "(5) 人間を被る - DIR EN GREY - 2018-04-25\n"
            + "(6) 泣きたいくらい - 大原櫻子 - 2018-04-25\n"
            + "(7) THE IDOLM@STER MILLION THE@TER GENERATION 07 トゥインクルリズム"
            + "(ZETTAI × BREAK!! トゥインクルリズム) - トゥインクルリズム[中谷育(原嶋あかり),"
            + "七尾百合子(伊藤美来),松田亜利沙(村川梨衣)] - 2018-04-25\n"
            + "(8) 春はどこから来るのか? - NGT48 - 2018-04-11\n"
            + "(9) Ask Yourself - KAT-TUN - 2018-04-18\n"
            + "(10) 鍵穴 - the Raid. - 2018-04-25";

    @Autowired
    private OriconController oriconController;

    @Test
    void testContextLoads() {
        assertNotNull(oriconController);
    }

    @Test
    void testHandleTextMessageEvent() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/oricon jpsingles weekly 2018-05-07");

        TextMessage reply = oriconController.handleTextMessageEvent(event);

        assertEquals(sampleWeeklyOutput, reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        oriconController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}