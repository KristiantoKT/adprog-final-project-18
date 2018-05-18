package advprog.example.bot.controller;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AlbumOfTheMonth {
    private String url;
    private ArrayList<Soundtrack> soundtracksOfTheMonth;

    public AlbumOfTheMonth(String url) {
        this.url = url;
        soundtracksOfTheMonth = new ArrayList<>();
        setSoundtracksOfTheMonthList(url);
    }

    public void setSoundtracksOfTheMonthList(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements body = document.getElementsByClass("album_infobit_medium");
            for (int i = 0; i < 50; i++) {

                Element element = body.get(i);
                String albumName = element.getElementsByClass("albumtitle").get(1).text();

                if (albumName.contains("Original Soundtrack")
                        || albumName.contains("Original Game Soundtrack")
                        || albumName.contains("Soundtrack")) {
                    String liTag = element.getElementsByTag("li").get(1).text();

                    String[] splitTag = liTag.split(" ");

                    String price = currencyConverter(splitTag[2], splitTag[3]);

                    Soundtrack soundtrack = new Soundtrack(albumName, price);
                    soundtracksOfTheMonth.add(soundtrack);
                }
            }
        } catch (IOException e) {
            System.out.println("Illegal IO");
        }
    }

    public String currencyConverter(String price, String from) throws IOException {
        String to = "IDR";

        URL url = new URL("http://finance.yahoo.com/d/quotes.csv?f=l1&s=" + from + to + "=X");

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = reader.readLine();

        Double idrPriceString = Double.parseDouble(line) * Integer.parseInt(price);

        String result = idrPriceString + " IDR";

        return result;
    }


    public String listAlbum() {
        StringBuilder str = new StringBuilder();
        int number = 1;
        for (Soundtrack soundtrack: soundtracksOfTheMonth) {
            str.append(format("(%d) %s\n", number, soundtrack.toString()));
            number++;
        }
        return str.toString();
    }


    public String getUrl() {
        return url;
    }

    public ArrayList<Soundtrack> getSoundtracksOfTheMonth() {
        return soundtracksOfTheMonth;
    }
}
