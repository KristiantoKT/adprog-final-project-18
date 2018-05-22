package advprog.example.bot.controller;

import advprog.anison.bot.ItuneSearch;
import advprog.anison.bot.SongSearch;

import com.linecorp.bot.model.action.DatetimePickerAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.Arrays;
import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] inputan = contentText.split(" ");

        String reply = "";
        String url = "";
        int itunesid = 0;

        if (inputan[0].equals("/listen_song")) {
            StringBuilder songName = new StringBuilder();
            for (int i = 1; i < inputan.length; i++) {
                songName.append(inputan[i]);
                songName.append(" ");
            }
            String song = songName.toString();
            System.out.println(song.substring(0,song.length() - 1));

            itunesid = SongSearch.findItunesId(song);

            if (itunesid == -1) {
                return new TextMessage("Song not found");
            } else if (itunesid == -2) {
                return new TextMessage("Song not available on iTunes");
            }
            url = ItuneSearch.getSongClipLink(itunesid);

            return new AudioMessage(url,30000);

        } else if (inputan[0].equals("/carousel")) {
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn("https://i.schoolido.lu/songs/soldier_game.jpg",
                                    "Soldier Game", "", Arrays.asList(
                                    new MessageAction("Listen",
                                            "/listen_song Soldier Game")
                            )),
                            new CarouselColumn("https://i.schoolido.lu/songs/Snow_halation.jpg",
                                    "Snow Halation", "", Arrays.asList(
                                    new MessageAction("Listen",
                                            "/listen_song Snow Halation")
                            ))
                    ));
            TemplateMessage templateMessage = new TemplateMessage(
                    "Carousel alt text", carouselTemplate);
            return templateMessage;
        }

        String replyText = contentText.replace("/echo", "");
        return new TextMessage(replyText.substring(1));
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
