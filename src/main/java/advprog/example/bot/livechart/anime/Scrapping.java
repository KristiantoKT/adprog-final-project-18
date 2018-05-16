package advprog.example.bot.livechart.anime;

import java.io.IOException;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapping {
    public String scrap(String url) throws IOException {
        try {
            Document docs = Jsoup.connect(url).get();
            Elements animes = docs.select("div.anime-card");
            String output = "Here are anime(s) that matches with your genre:\n";
            output += animes.stream().map(this::scrap).collect(Collectors.joining("\n"));
            return output;
        } catch (HttpStatusException e) {
            return "Invalid url";
        }
    }

    private String scrap(Element anime) {
        String title = anime.select("h3.main-title").text();
        String synopsis = anime.select("div.anime-synopsis").text();
        String genre = anime.select("ol.anime-tags").text();
        Anime animes = new Anime(title, synopsis);
        animes.setGenre(genre.split(" "));
        return String.format("%s \n " +
                "   %s",title, synopsis);


    }

    public static void main(String[] args) throws IOException {
        Scrapping s = new Scrapping();
        System.out.println(s.scrap("https://www.livechart.me/summer-2018/tv"));
    }
}
