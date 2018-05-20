package advprog.example.bot.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class MovieSchedules {
    private String movieUrl;
    private List<Song> songInChart;

    public MovieSchedules(String billboardUrl) {
        this.movieUrl = billboardUrl;
        songInChart = new ArrayList<>();
    }

    public String findArtist(String studioType) {
        try {
            Document doc = Jsoup.connect(movieUrl).get();
            Elements links = doc.getElementsByClass("chart-row");

            for (int i = 0; i < 100; i++) {
                Element elem = links.get(i);
                String title = elem.getElementsByClass("schedule-title").html(); //("chart-row__song").html();
                String schedule = elem.getElementsByClass("showtime-lists").html();

                String formatName = Parser.unescapeEntities(title, false);
                String formatArtist = Parser.unescapeEntities(schedule, false);

                if (formatArtist.equalsIgnoreCase(studioType)) {
                    songInChart.add(new Song(formatName, formatArtist, i + 1));
                }
            }

            return printSong();

        } catch (IOException e) {
            return "Illegal IO";
        }
    }

    public String printSong() {
        if (songInChart.size() == 0) {
            return "Oops sorry! The artist isn't on the Billboard Japan HOT 100!";
        }

        StringBuilder stringBuilder = new StringBuilder();

        int counter = 1;
        for (Song song : songInChart) {
            stringBuilder.append("Artist : " + song.getArtist()
                    + "\n" + "Song : " + song.getName() + "\n" + "Rank : " + song.getPosition());
            if (counter < songInChart.size()) {
                counter++;
                stringBuilder.append("\n");
            }
        }

        songInChart.clear();

        return stringBuilder.toString();
    }
}
