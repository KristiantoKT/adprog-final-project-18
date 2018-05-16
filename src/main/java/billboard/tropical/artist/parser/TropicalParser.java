package billboard.tropical.artist.parser;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.swing.text.Document;
import java.io.IOException;
import java.util.ArrayList;

public class TropicalParser {
    ArrayList<String> artistsArr = new ArrayList<>();
    ArrayList<String> songsArr = new ArrayList<>();
    Document doc;

    public TropicalParser() {
        doc = getHtml("https://www.billboard.com/charts/tropical-songs");
        Elements artists = doc.select("article.chart-row");
        for (Element artist: artists) {
            String song = artist.select("h2.chart-row__song").text();
            String artisA = artist.select("a.chart-row__artist").text();
            String artisSpan = artist.select("span.chart-row__artist").text();
            if(artisA.equals("")){
                artistsArr.add(artisSpan.toLowerCase());
            } else{
                artistsArr.add(artisA.toLowerCase());
            }
            songsArr.add(song);
        }
    }

    public ArrayList<String> getArrayArtist() {
        return artistsArr;
    }

    public ArrayList<String> getArraySong() {
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
