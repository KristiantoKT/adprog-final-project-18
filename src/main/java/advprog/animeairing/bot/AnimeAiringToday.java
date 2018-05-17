package advprog.animeairing.bot;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;


public class AnimeAiringToday {
    private String urlAiringToday;
    private ArrayList<Anime> airingToday;

    public AnimeAiringToday(String urlAiringToday) {
        this.urlAiringToday = urlAiringToday;
        airingToday = new ArrayList<>();
        setAiringTodayList(urlAiringToday);
    }

    public String getUrlAiringToday() {
        return urlAiringToday;
    }

    public ArrayList<Anime> getAiringToday() {
        return airingToday;
    }



    private void setAiringTodayList(String urlAiringToday) {
        try {
            Document doc = Jsoup.connect(urlAiringToday).get();

            Elements header = doc.getElementsByClass("schedule-card past");

            for (int i = 0; i < header.size(); i++) {
                Element elem = header.get(i);
                String title = elem.getElementsByClass("schedule-card-title").text();
                String episode = elem.getElementsByClass("schedule-card-countdown")
                        .text().split(" ")[0]
                        .replace("EP","")
                        .replace(":", "");
                Anime anime = new Anime(title,episode);
                airingToday.add(anime);
            }

        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public String listAiringToday() {
        StringBuilder output = new StringBuilder();
        for (Anime anime : airingToday) {
            output.append(anime.toString() + "\n");
        }
        return output.toString();
    }




}
