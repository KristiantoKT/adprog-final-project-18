package advprog.top20album.bot;

import advprog.favartist.bot.Song;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopAlbum {

    private String vgmdbUrl;
    private List<Album> listOfAlbums;
    private List<String> listOfUrl;

    public TopAlbum(String vgmdbUrl) {
        this.vgmdbUrl = vgmdbUrl;
        listOfAlbums = new ArrayList<>();
        listOfUrl = new ArrayList<>();
        getTop20Url(vgmdbUrl);
    }

    public String getTop20Url(String vgmdbUrl) {
        try {
            Document doc = Jsoup.connect("https://vgmdb.net/db/statistics.php?do=top_rated").get();
            Elements link = doc.select("div#innermain");
            Elements links = link.select("a[href]");

            for (int i = 0; i < 20; i++) {
                listOfUrl.add(links.get(i).attr("href"));
            }
            return printUrl();
        } catch (IOException e) {
            return "Error!";
        }
    }

    public int getPrice(String albumUrl) {
        return 0;
    }

    public String printUrl() {
        if (listOfUrl.size() == 0) {
            return "Error! There are no albums";
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (String url : listOfUrl) {
            stringBuilder.append(url + "\n");
        }

        return stringBuilder.toString();
    }

    public String getUrl() {
        return vgmdbUrl;
    }

    public List<String> getListOfUrl() {
        return listOfUrl;
    }

}
