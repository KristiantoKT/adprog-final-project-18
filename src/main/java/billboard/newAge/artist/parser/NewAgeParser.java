package billboard.newage.artist.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewAgeParser {
    ArrayList<String> artistsArr = new ArrayList<>();
    ArrayList<String> songsArr = new ArrayList<>();
    Document doc;

    public NewAgeParser() {
        doc = getHtml("https://www.billboard.com/charts/new-age-albums");
        Elements artists = doc.select("article.chart-row");
        for (Element artist: artists) {
            String song = artist.select("h2.chart-row__song").text();
            String artisA = artist.select("a.chart-row__artist").text();
            String artisSpan = artist.select("span.chart-row__artist").text();
            if (artisA.equals("")) {
                artistsArr.add(artisSpan.toLowerCase());
            } else {
                artistsArr.add(artisA.toLowerCase());
            }
            songsArr.add(song);
        }
    }

    public ArrayList<String> getArtistsArray() {
        return artistsArr;
    }

    public ArrayList<String> getSongsArray() {
        return songsArr;
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