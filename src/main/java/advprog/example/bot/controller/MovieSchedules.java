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
        String[] studType = studioType.split("_");
        String stTypeFix = "";
        for (String stType : studType) {
            stTypeFix += stType;
        }

        try {
            Document doc = Jsoup.connect(movieUrl).get();
            Elements links = doc.getElementsByClass("schedule-lists");

            for (Element movie : links) {
                String title = movie.getElementsByClass("schedule-title").html(); //("chart-row__song").html();
                String schedule = movie.getElementsByClass("showtime-lists").html();
                String formatStudio = movie.getElementsByClass("audi-nm").html();
                System.out.printf(title);
                System.out.printf(schedule);
                System.out.printf(formatStudio);

                String formatTitle = Parser.unescapeEntities(title, false);
                String formatSchedule = Parser.unescapeEntities(schedule, false);

                if (stTypeFix.contains(formatSchedule)) {
                    songInChart.add(new Song(formatTitle, formatSchedule));
                }
            }

            return printSong();

        } catch (IOException e) {
            return "Illegal IO";
        }
    }

    public String printSong() {
        if (songInChart.size() == 0) {
            return "Oops sorry! That studio type isn't available in this CGV! Please look for another CGV cinemas";
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
