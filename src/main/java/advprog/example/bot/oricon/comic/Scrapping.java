package advprog.example.bot.oricon.comic;

import org.jsoup.HttpStatusException;


import java.io.IOException;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Scrapping {

    public String scrap(String url) throws IOException {
        try {
            Document docs = Jsoup.connect(url).get();
            Elements comics = docs.select("section.box-rank-entry");
            String output = "Top 10\n";
            output += comics.stream().map(this::scrap).collect(Collectors.joining("\n"));
            return output;
        } catch (HttpStatusException e) {
            return "Invalid url";
        }
    }

    private String scrap(Element comic) {
        String chartPosition = comic.select("p.num").text();
        String title = comic.select("h2.title").text();
        String author = comic.select("li.artist-name").text();
        return String.format("(%s) %s - %s",chartPosition,title, author);

    }

    public static void main(String[] args) throws IOException {
        Scrapping s = new Scrapping();
        System.out.println(s.scrap("https://www.oricon.co.jp/rank/cbm/m/2018-04"));
    }
}
