package advprog.favArtist.bot;

import static java.lang.String.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class ArtistBill200 {
    private String billboard200;
    private List<Song> songByArtist;

    public ArtistBill200(String billboard200){
        this.billboard200 = billboard200;
        songByArtist = new ArrayList<>();
    }

    public String findArtistInChart(String artistToFind, String url) {
        return "";
    }

    public String printSong() {
        return "";
    }
}
