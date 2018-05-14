package advprog.example.bot.oricon.oriconsingle;

import java.io.IOException;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Scrapper {

    public static void main(String[] args) throws IOException {
        Scrapper py = new Scrapper();
        System.out.println(py.scrap("https://www.oricon.co.jp/rank/js/y/2017/"));
    }

    public Scrapper(){

    }

    public String scrap(String url) throws IOException {
        try {
            Document docs = Jsoup.connect(url).get();
            Elements songs = docs.select("section.box-rank-entry");
            String output = "Top 10!\n";
            output += songs.stream().map(js -> scrap(js)).collect(Collectors.joining("\n"));
            return output;
        } catch (HttpStatusException e) {
            return "Invalid URL";
        }
    }

    private String scrap(Element song) {
        String rank = song.select("p.num").text();
        Element info = song.selectFirst("div.wrap-text");
        String title = info.select("h2.title").text();
        String artist = info.select("p.name").text();
        String date = fixDate(info.select("li").first().text());
        String estimateSales = "";
        try {
            estimateSales = info.select("li").text().substring(24,31);
            if (estimateSales.contains("枚")) {
                estimateSales = estimateSales.replace("枚","");
            } else {
                estimateSales = "Not Available";
            }
        } catch (StringIndexOutOfBoundsException e) {
            estimateSales = "Not Available";
        }
        return String.format("(%s) %s - %s - %s - %s",rank,title,artist,date,estimateSales);
    }

    private String fixDate(String date) {
        return date.replace("発売日： ", "")
                .replace("年", "-")
                .replace("月", "-")
                .replace("日", "");
    }
}
