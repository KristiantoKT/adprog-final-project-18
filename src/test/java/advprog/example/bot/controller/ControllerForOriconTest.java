package advprog.example.bot.controller;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)

public class ControllerForOriconTest {
    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    private String sampleWeeklyOutput = "Top 10\n"
            + "(1) キングダム - (画)原泰久\n"
            + "(2) よつばと! - (画)あずまきよひこ\n"
            + "(3) 宇宙兄弟 - (画)小山宙哉\n"
            + "(4) 思い、思われ、ふり、ふられ - (画)咲坂伊緒\n"
            + "(5) 七つの大罪 - (画)鈴木央\n"
            + "(6) GIANT KILLING - (画)ツジトモ/(原案・取材協力)綱本将也\n"
            + "(7) 僕のヒーローアカデミア - (画)堀越耕平\n"
            + "(8) ゴールデンカムイ - (画)野田サトル\n"
            + "(9) 進撃の巨人 - (画)諫山創\n"
            + "(10) 花のち晴れ〜花男 Next Season〜 - (画)神尾葉子";

    @Autowired
    private ControllerForOricon controllerForOricon;

    @Test
    void testContextLoads() {
        Assert.assertNotNull(controllerForOricon);
    }

    @Test
    void testHandleTextMessageEvent() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/oricon comic 2018-05-07");

        TextMessage reply = controllerForOricon.handleTextMessageEvent(event);

        Assert.assertEquals(sampleWeeklyOutput, reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = Mockito.mock(Event.class);

        controllerForOricon.handleDefaultMessage(event);

        Mockito.verify(event, VerificationModeFactory.atLeastOnce()).getSource();
        Mockito.verify(event, VerificationModeFactory.atLeastOnce()).getTimestamp();
    }
}
