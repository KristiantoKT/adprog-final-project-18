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

    public void getTop20Url(String vgmdbUrl) {
        try {
            Document doc = Jsoup.connect("https://vgmdb.net/db/statistics.php?do=top_rated").get();
            Elements link = doc.select("div#innermain");
            Elements links = link.select("a[href]");

            for (int i = 0; i < 20; i++) {
                listOfUrl.add(links.get(i).attr("href"));
            }

/*            for (String url : listOfUrl) {
                int position = 1;
                setTop20(url, position);
            }*/

            setTop20(listOfUrl.get(0),1);

        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public void setTop20(String url, int position) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements link = doc.select("div#innermain");

            // BUAT TITLE
            Element elem = link.get(0);
            String name = elem.getElementsByClass("albumtitle").get(0).html();
            String formattedNames = Parser.unescapeEntities(name, false);
            //System.out.println(formattedNames);

            // BUAT PRICE
            Element table = link.get(0);
            Element e = table.getElementById("album_infobit_large");
            Element rows = e.getElementsByTag("tbody").get(0);
            Element row = rows.getElementsByTag("tr").get(9);
            Element eprice = row.getElementsByTag("td").get(1);
            String price = eprice.text().split(" ")[0];
            //System.out.println(price);

            // BUAT RATING
            String rating = doc.getElementById("ratingmsg-album-4").text().split(" ")[1];
            //System.out.println(rating);

            Album album = new Album(position, formattedNames, rating, price);
            listOfAlbums.add(album);
        } catch (IOException e) {
            System.out.println("Error!");
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

    public List<Album> getListOfAlbums() {
        return listOfAlbums;
    }

}
