package advprog.top20album.bot;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

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
            Document doc = Jsoup.connect(vgmdbUrl).get();
            Elements link = doc.select("div#innermain");
            Elements links = link.select("a[href]");

            for (int i = 0; i < 20; i++) {
                listOfUrl.add(links.get(i).attr("href"));
            }

            for (String url : listOfUrl) {
                int position = 1;
                setTop20(url, position);
                position++;
            }

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
            Elements e = doc.select("table#album_infobit_large");
            Element a = e.get(0);
            Elements f = a.getElementsByClass("vbmenu_option");
            int temp = 0;

            for (Element g : f) {
                temp++;
            }

            Element b;
            if (temp == 0) {
                b = a.getElementsByTag("tr").get(temp + 3);
            } else {
                b = a.getElementsByTag("tr").get(temp + 4);
            }

            String price = b.getElementsByTag("td").get(1).text().split(" ")[0];
            //System.out.println(price);

            // BUAT RATING
            String rating = doc.select("span[rel=rating]").text().split(" ")[1];
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

    public String printTop20() {
        StringBuilder sb = new StringBuilder();

        for (Album album : listOfAlbums) {
            sb.append(album.getPosition() + " - " + album.getName() + " - " + album.getRating()
                    + " " + album.getPrice());
        }
        return sb.toString();

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
