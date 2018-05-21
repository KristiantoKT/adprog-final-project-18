package advprog.example.bot.controller;

import advprog.anison.bot.ItuneSearch;
import advprog.anison.bot.SongSearch;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

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
