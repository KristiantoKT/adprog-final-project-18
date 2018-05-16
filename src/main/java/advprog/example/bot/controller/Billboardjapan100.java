package advprog.example.bot.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Billboardjapan100 {
    private String urlBillboard;
    private List<Song> songInChart;

    public Billboardjapan100(String billboardUrl) {
        this.urlBillboard = billboardUrl;
        songInChart = new ArrayList<>();
    }

    public String findArtist(String artistSearch) {
        try {
            Document doc = Jsoup.connect(urlBillboard).get();
            Elements links = doc.getElementsByClass("chart-row");

            for (int i = 0; i < 100; i++) {
                Element elem = links.get(i);
                String name = elem.getElementsByClass("chart-row__song").html();
                String artist = elem.getElementsByClass("chart-row__artist").html();

                String formatName = Parser.unescapeEntities(name, false);
                String formatArtist = Parser.unescapeEntities(artist, false);

                if (formatArtist.equalsIgnoreCase(artistSearch)) {
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
