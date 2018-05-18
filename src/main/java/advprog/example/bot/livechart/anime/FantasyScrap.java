package advprog.example.bot.livechart.anime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FantasyScrap {
    private ArrayList<Anime> animeList = new ArrayList<Anime>();

    public String fantasyScrap(String url) throws IOException {
        try {
            Document docs = Jsoup.connect(url).get();
            Elements animes = docs.select("div.anime-card");
            String output = "Here are anime(s) that matches with your genre:\n";
            String hehe = animes.stream().map(this::fantasyScrap).collect(Collectors.joining("\n"));
            return output + animeBuilder(animeList);
        } catch (HttpStatusException e) {
            return "Invalid url";
        }
    }

    private String fantasyScrap(Element anime) {
        String title = anime.select("h3.main-title").text();
        String synopsis = anime.select("div.anime-synopsis").text();
        String genre = anime.select("ol.anime-tags").text();
        Anime animes = new Anime(title, synopsis, genre);
        if (genre.contains("Fantasy")) {
            animeList.add(animes);
        }
        return String.format("%s \n "
                + "   %s",title, synopsis);
    }


    private String animeBuilder(ArrayList<Anime> list) {
        StringBuilder out = new StringBuilder();
        for (Anime anime : list) {
            out.append(anime.getTitle() + "\n "
                    + anime.getSynopsis() + "\n");
        }
        return out.toString();
    }

    public static void main(String[] args) throws IOException {
        FantasyScrap s = new FantasyScrap();
        System.out.println(s.fantasyScrap("https://www.livechart.me/spring-2018/tv"));
    }
}
