package advprog.bikun.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.bikun.bot.EventBikunTestUtil;
import advprog.bikun.bot.HalteBikun;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class PrintControllerTest {
    private String path = "./src/main/java/advprog/bikun/bot/halte-bikun.json";
    private BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
    private ObjectMapper objectMapper = new ObjectMapper();
    private HalteBikun[] halteBikuns = objectMapper.readValue(bufferedReader, HalteBikun[].class);

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private PrintController printController;

    public PrintControllerTest() throws IOException {
    }

    @Test
    void testContextLoads() {
        assertNotNull(printController);
    }


    @Test
    void testBikunStopHandleTextMessageEvent(){
        MessageEvent<TextMessageContent> event =
                EventBikunTestUtil.createDummyTextMessage("/bikun_stop");
        List<Message> messageList = new ArrayList<>();
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(halteBikuns[0].getImgUrl(), "Halte Bikun 1",
                                halteBikuns[0].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[0].getNama()))),
                        new CarouselColumn(halteBikuns[1].getImgUrl(), "Halte Bikun 2",
                                halteBikuns[1].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[1].getNama()))),
                        new CarouselColumn(halteBikuns[2].getImgUrl(), "Halte Bikun 3",
                                halteBikuns[2].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[2].getNama()))),
                        new CarouselColumn(halteBikuns[3].getImgUrl(), "Halte Bikun 4",
                                halteBikuns[3].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[3].getNama()))),
                        new CarouselColumn(halteBikuns[4].getImgUrl(), "Halte Bikun 5",
                                halteBikuns[4].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[4].getNama()))),
                        new CarouselColumn(halteBikuns[5].getImgUrl(), "Halte Bikun 6",
                                halteBikuns[5].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[5].getNama()))),
                        new CarouselColumn(halteBikuns[6].getImgUrl(), "Halte Bikun 7",
                                halteBikuns[6].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[6].getNama()))),
                        new CarouselColumn(halteBikuns[7].getImgUrl(), "Halte Bikun 8",
                                halteBikuns[7].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[7].getNama()))),
                        new CarouselColumn(halteBikuns[8].getImgUrl(), "Halte Bikun 9",
                                halteBikuns[8].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[8].getNama()))),
                        new CarouselColumn(halteBikuns[9].getImgUrl(), "Halte Bikun 10",
                                halteBikuns[9].getNama(), Collections.singletonList(
                                new PostbackAction(
                                        "Pilih", halteBikuns[9].getNama())))
                )
        );

        TemplateMessage templateMessage = new TemplateMessage("Pilih Halte Bikun",
                carouselTemplate);
        messageList.add(templateMessage);
        assertEquals(messageList, printController.handleTextMessageEvent(event));
    }

    @Test
    void testHandleRequestHalteByUser() throws Exception {
        TextMessageContent textMessageContent = new TextMessageContent("123", "/bikun");
        MessageEvent<TextMessageContent> event = new MessageEvent<>(
                "123", new UserSource("1234"), textMessageContent, Instant.now()
        );
        printController.handleTextMessageEvent(event);
        LocationMessageContent locationMessageContent =
                new LocationMessageContent("123",
                        "Faculty of Computer Science, University of Indonesia",
                        "Kampus UI Depok, Pd. Cina, Beji, Kota Depok, Jawa Barat 16424",
                        -6.3646009, 106.8264999);
        MessageEvent<LocationMessageContent> event2 = new MessageEvent<>(
                "123", new UserSource("1234"), locationMessageContent, Instant.now()
        );
        printController.handleLocationMessageEvent(event2);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        printController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }


}
