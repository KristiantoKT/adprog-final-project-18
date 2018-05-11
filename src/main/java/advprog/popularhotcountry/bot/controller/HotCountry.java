package advprog.popularhotcountry.bot.controller;

import static java.lang.String.format;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class HotCountry {


    private String url;
    private ArrayList<Charts> hotCountryTopTen;

    public HotCountry(String url) {
        this.url = url;
        hotCountryTopTen = new ArrayList<>();
        setHotCountryTopTenLists(url);
    }

    public String getUrl() {
        return url;
    }
    public ArrayList<Charts> getHotCountryTopTen() {
        return hotCountryTopTen;
    }


    private void setHotCountryTopTenLists(String url) {
        try {
            Document  billboard = Jsoup.connect(url).get();
            Elements links = billboard.getElementsByClass("chart-row");
            for (int i = 0; i < 10; i++) {
                Element element = links.get(i);
                String name = element.getElementsByClass("chart-row__song").html();
                String artist = element.getElementsByClass("chart-row__artist").html();

                String formattedName = Parser.unescapeEntities(name, false);
                String formattedArtist = Parser.unescapeEntities(artist, false);

                Charts chart = new Charts(formattedName, formattedArtist);
                hotCountryTopTen.add(chart);
            }
        } catch (IOException e) {
            System.out.println("Illegal IO ");
        }
    }


    public String listTopTen() {
        StringBuilder str = new StringBuilder();
        int counter = 1;
        for (Charts chart : hotCountryTopTen) {
            str.append(format("(%d) %s\n", counter, chart.toString()));
            counter++;
        }
        return str.toString();
    }


}
