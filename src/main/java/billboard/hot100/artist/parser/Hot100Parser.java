package billboard.hot100.artist.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Hot100Parser {
    ArrayList<String> arrOfArtist = new ArrayList<>();
    ArrayList<String> arrOfSong = new ArrayList<>();
    Document doc;

    public Hot100Parser() {
        doc = getHtml("https://www.billboard.com/charts/hot-100");
        Elements artists = doc.select("article.chart-row");
        for (Element artist: artists) {
            String song = artist.select("h2.chart-row__song").text();
            String artisA = artist.select("a.chart-row__artist").text();
            String artisSpan = artist.select("span.chart-row__artist").text();
            if (artisA.equals("")) {
                arrOfArtist.add(artisSpan.toLowerCase());
            } else {
                arrOfArtist.add(artisA.toLowerCase());
            }
            arrOfSong.add(song);
        }
    }

    public ArrayList<String> getArtistsArr() {

        return arrOfArtist;
    }

    public ArrayList<String> getSongsArr() {

        return arrOfSong;
    }

    public Document getHtml(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            doc = null;
        }
        return doc;
    }
}