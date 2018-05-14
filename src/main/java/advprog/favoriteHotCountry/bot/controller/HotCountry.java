package advprog.favoritehotcountry.bot.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


public class HotCountry {

    private  String artistName;
    private String url;
    private ArrayList<Charts> artistSongs;

    public HotCountry(String artistName, String url) {
        this.url = url;
        this.artistName = artistName;
        artistSongs = new ArrayList<>();
        setHotCountryArtist(artistName, url);
    }

    public ArrayList<Charts> getArtistSongs() {
        return artistSongs;
    }


    private void setHotCountryArtist(String artistName, String url) {
        try {
            Document  billboard = Jsoup.connect(url).get();
            Elements check = billboard.getElementsByClass("chart-row");
            for (int i = 0; i < 50; i++) {
                Element element = check.get(i);
                String songTitle = element.getElementsByClass("chart-row__song").html();
                String artist = element.getElementsByClass("chart-row__artist").html();

                String songTitleChart = Parser.unescapeEntities(songTitle, false);
                String artistChart = Parser.unescapeEntities(artist, false);

                if (artistChart.equalsIgnoreCase(artistName)) {
                    Charts chart = new Charts(songTitleChart, artistChart, i + 1);
                    artistSongs.add(chart);
                }
            }

        } catch (IOException e) {
            System.out.println("Illegal IO ");
        }
    }


    public String infoArtist() {
        if (artistSongs.size() == 0) {
            return "The artist isn't on weekly Billboard Hot Country Chart";
        }

        StringBuilder str = new StringBuilder();
        int number = 1;
        for (Charts chart : artistSongs) {
            str.append("Artist : " + chart.getArtist()
                    + "\n" + "Title Song : " + chart.getSong()
                    + "\n" + "Position : " + chart.getPosition());
            if (number < artistSongs.size()) {
                number++;
                str.append("\n");
            }
        }
        return str.toString();
    }

    public String getUrl() {

        return url;
    }

    public String getArtistName() {

        return artistName;
    }


}
