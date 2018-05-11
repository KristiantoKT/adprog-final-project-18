package advprog.example.bot.tropical;

import static java.lang.String.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 * BillboardTropical is an implementation of Top 10 Tropical Songs from Billboard.com.
 * @author Kristianto
 */
public class BillboardTropical {
    private String billboardUrl;
    private List<Song> topTenTropicalList;

    public BillboardTropical(String billboardUrl) {
        this.billboardUrl = billboardUrl;
        topTenTropicalList = new ArrayList<>();
        setTopTenTropicalSongs(billboardUrl);
    }

    /**
     * Set top ten Tropical Songs from Billboard.com. &nbsp;
     * It uses web scraping method and Jsoup API to get the data.
     * @param url of Billboard's chart
     */
    private void setTopTenTropicalSongs(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.getElementsByClass("chart-row");
            for (int i = 0; i < 10; i++) {
                Element elem = links.get(i);
                String name = elem.getElementsByClass("chart-row__song").html();
                String artist = elem.getElementsByClass("chart-row__artist").html();

                String formattedName = Parser.unescapeEntities(name, false);
                String formattedArtist = Parser.unescapeEntities(artist, false);

                Song song = new Song(formattedName, formattedArtist);
                topTenTropicalList.add(song);
            }
        } catch (IOException e) {
            System.out.println("Illegal IO ");
        }
    }

    /**
     * Set output for 10 top Tropical Songs from Billboard.com
     * @return 10 top Tropical Songs
     */
    public String printTopTenList() {
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (Song song : topTenTropicalList) {
            sb.append(format("(%d) %s\n", counter, song.toString()));
            counter++;
        }
        return sb.toString();
    }

    public String getBillboardUrl() {
        return billboardUrl;
    }

    public List<Song> getTopTenTropicalList() {
        return topTenTropicalList;
    }
}
