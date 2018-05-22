package advprog.bikun.bot.controller;

import advprog.bikun.bot.HalteBikun;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@LineMessageHandler
public class PrintController {
    private String path = "./src/main/java/advprog/bikun/bot/halte-bikun.json";
    private BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
    private ObjectMapper objectMapper = new ObjectMapper();
    private HalteBikun[] halteBikuns = objectMapper.readValue(bufferedReader, HalteBikun[].class);

    private static final Logger LOGGER = Logger.getLogger(PrintController.class.getName());

    public PrintController() throws IOException {
    }


    @EventMapping
    public List<Message> handlePostbackEvent(PostbackEvent event) {
        String namaHalte = event.getPostbackContent().getData();
        String waktu = event.getTimestamp().toString();
        List<Message> result = new ArrayList<>();
        HalteBikun halteTerpilih = null;
        for (HalteBikun halteBikun : halteBikuns) {
            if (halteBikun.getNama().equals(namaHalte)) {
                halteTerpilih = halteBikun;
            }
        }
        LocationMessage halteBikunLocation = new LocationMessage(
                halteTerpilih.getNama(), "UI",
                halteTerpilih.getLatitude(), halteTerpilih.getLongitude()
        );
        TextMessage halteBikunDetail = new TextMessage(
                String.format("Anda memilih %s\n\n", halteTerpilih.getNama())
        );

        result.add(halteBikunLocation);
        result.add(halteBikunDetail);
        return result;
    }


    @EventMapping
    public List<Message> handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        if (contentText.equals("/bikun") && event.getSource() instanceof UserSource) {
            return BikunController.requestLocation();
        } else if (contentText.equals("/bikun_stop") && event.getSource() instanceof UserSource) {
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
                                            "Pilih", halteBikuns[9].getNama()))),
                            new CarouselColumn(halteBikuns[10].getImgUrl(), "Halte Bikun 11",
                                    halteBikuns[10].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[10].getNama()))),
                            new CarouselColumn(halteBikuns[11].getImgUrl(), "Halte Bikun 12",
                                    halteBikuns[11].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[11].getNama()))),
                            new CarouselColumn(halteBikuns[12].getImgUrl(), "Halte Bikun 13",
                                    halteBikuns[12].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[12].getNama()))),
                            new CarouselColumn(halteBikuns[13].getImgUrl(), "Halte Bikun 14",
                                    halteBikuns[13].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[13].getNama()))),
                            new CarouselColumn(halteBikuns[14].getImgUrl(), "Halte Bikun 15",
                                    halteBikuns[14].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[14].getNama()))),
                            new CarouselColumn(halteBikuns[15].getImgUrl(), "Halte Bikun 16",
                                    halteBikuns[15].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[15].getNama()))),
                            new CarouselColumn(halteBikuns[16].getImgUrl(), "Halte Bikun 17",
                                    halteBikuns[16].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[16].getNama()))),
                            new CarouselColumn(halteBikuns[17].getImgUrl(), "Halte Bikun 18",
                                    halteBikuns[17].getNama(), Collections.singletonList(
                                    new PostbackAction(
                                            "Pilih", halteBikuns[17].getNama())))
                    )
            );

            TemplateMessage templateMessage = new TemplateMessage("Pilih Halte Bikun",
                    carouselTemplate);
            messageList.add(templateMessage);
            return messageList;
        } else {
            return null;
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }


    @EventMapping
    public List<Message> handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        LocationMessageContent content = event.getMessage();
        return BikunController.searchHalte(event, halteBikuns);
    }


}
