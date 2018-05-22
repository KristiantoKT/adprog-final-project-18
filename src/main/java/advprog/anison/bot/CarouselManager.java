package advprog.anison.bot;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class CarouselManager {

    public static ArrayList<Song> songs = SongCsvReader.readSong("test");

    public static TemplateMessage carouselMaker() {
        CarouselColumn[] columns = new CarouselColumn[songs.size()];

        for (int i = 0; i < columns.length; i++) {
            Song current = songs.get(i);
            columns[i] = new CarouselColumn(current.getSongImgUrl(),
                    current.getSongName(),"anison-radio", Arrays.asList(
                            new MessageAction("Listen!",
                                    "/listen_song " + current.getSongName()),
                            new MessageAction("Listen!",
                                    "/listen_song " + current.getSongName()),
                            new MessageAction("Listen!",
                                    "/listen_song " + current.getSongName())
            ));
        }

        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(columns)
        );
        TemplateMessage templateMessage = new TemplateMessage(
                "Carousel alt text", carouselTemplate);
        return templateMessage;
    }

    /*else if (inputan[0].equals("/carousel")) {
            String imageUrl = "https://i.schoolido.lu/songs/soldier_game.jpg";
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                    new URIAction("Go to line.me",
                                            "https://line.me"),
                                    new URIAction("Go to line.me",
                                            "https://line.me"),
                                    new PostbackAction("Say hello1",
                                            "hello こんにちは")
                            )),
                            new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                    new PostbackAction("言 hello2",
                                            "hello こんにちは",
                                            "hello こんにちは"),
                                    new PostbackAction("言 hello2",
                                            "hello こんにちは",
                                            "hello こんにちは"),
                                    new MessageAction("Listen!!",
                                            "/listen_song Soldier Game")
                            )),
                            new CarouselColumn(imageUrl, "Datetime Picker",
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
                            ))
                    ));
            TemplateMessage templateMessage = new TemplateMessage(
                    "Carousel alt text", carouselTemplate);
            return templateMessage;
        }*/
}
