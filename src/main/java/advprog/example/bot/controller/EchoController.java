package advprog.example.bot.controller;

import advprog.anison.bot.CarouselManager;
import advprog.anison.bot.ItuneSearch;
import advprog.anison.bot.Song;
import advprog.anison.bot.SongCsvReader;
import advprog.anison.bot.SongCsvWriter;
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

import java.util.ArrayList;
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

            ArrayList<Song> songs = SongCsvReader.readSong("test");
            SongCsvWriter.writeSongArray("test",songs);

            for (Song target : songs) {
                if (target.getSongName().equalsIgnoreCase(song)) {
                    itunesid = SongSearch.findItunesId(song);

                    if (itunesid == -1) {
                        return new TextMessage("Song not found");
                    } else if (itunesid == -2) {
                        return new TextMessage("Song not available on iTunes");
                    }
                    url = ItuneSearch.getSongClipLink(itunesid);

                    return new AudioMessage(url,30000);
                }
            }

            return new TextMessage("Song not added to list");

        } else if (inputan[0].equals("/carousel")) {
            String imageUrl = "https://i.schoolido.lu/songs/soldier_game.jpg";
            CarouselColumn[] columns = new CarouselColumn[3];
            columns[0] = new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                    new URIAction("Go to line.me",
                            "https://line.me"),
                    new URIAction("Go to line.me",
                            "https://line.me"),
                    new PostbackAction("Say hello1",
                            "hello こんにちは")
            ));
            columns[1] = new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                    new PostbackAction("言 hello2",
                            "hello こんにちは",
                            "hello こんにちは"),
                    new PostbackAction("言 hello2",
                            "hello こんにちは",
                            "hello こんにちは"),
                    new MessageAction("Listen!!",
                            "/listen_song Soldier Game")
            ));
            columns[2] = new CarouselColumn(imageUrl, "Datetime Picker",
                    "Please select a date, time or datetime", Arrays.asList(
                    new DatetimePickerAction("Datetime",
                            "action=sel",
                            "datetime",
                            "2017-06-18T06:15",
                            "2100-12-31T23:59",
                            "1900-01-01T00:00"),
                    new DatetimePickerAction("Date",
                            "action=sel&only=date",
                            "date",
                            "2017-06-18",
                            "2100-12-31",
                            "1900-01-01"),
                    new DatetimePickerAction("Time",
                            "action=sel&only=time",
                            "time",
                            "06:15",
                            "23:59",
                            "00:00")
            ));
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(columns)
            );
            TemplateMessage templateMessage = new TemplateMessage(
                    "Carousel alt text", carouselTemplate);
            return templateMessage;
        } else if (inputan[0].equals("/add_song")) {
            StringBuilder songName = new StringBuilder();
            for (int i = 1; i < inputan.length; i++) {
                songName.append(inputan[i]);
                songName.append(" ");
            }
            String song = songName.toString();
            SongCsvWriter.writeSong("test",song);
        } else if (inputan[0].equals("/delete_song")) {
            StringBuilder songName = new StringBuilder();
            for (int i = 1; i < inputan.length; i++) {
                songName.append(inputan[i]);
                songName.append(" ");
            }
            String song = songName.toString();
            ArrayList<Song> songs = SongCsvReader.readSong("test");
            SongCsvWriter.writeSongArray("test", CarouselManager.deleteSong(songs,song));
        } else if (inputan[0].equals("/list_song")) {
            ArrayList<Song> songs = SongCsvReader.readSong("test");
            StringBuilder songList = new StringBuilder();
            for (Song song : songs) {
                songList.append(song.getSongName());
                songList.append("\n");
            }
            SongCsvWriter.writeSongArray("test",songs);
            return new TextMessage(songList.toString());
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
