package advprog.example.bot.command;

import advprog.example.bot.livechart.anime.ActionScrap;
import advprog.example.bot.livechart.anime.ComedyScrap;
import advprog.example.bot.livechart.anime.FantasyScrap;
import advprog.example.bot.livechart.anime.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class SeasonCommand {
    private Scrapping scrapping = new Scrapping();
    private ActionScrap as = new ActionScrap();
    private ComedyScrap cs = new ComedyScrap();
    private FantasyScrap fs = new FantasyScrap();
    private String url;
    private String season;
    private String year;
    private String genre;


    String out;

    static final String LINK_URL = "https://www.livechart.me/";

    public TextMessage execute(String input, String genre) throws IOException {
        if (genre.equalsIgnoreCase("Action")) {
            out = as.actionScrap(getUrl());
        } else if (genre.equalsIgnoreCase("Comedy")) {
            out = cs.comedyScrap(getUrl());
        } else if (genre.equalsIgnoreCase("Fantasy")) {
            out = fs.fantasyScrap(getUrl());
        }
        out = scrapping.scrap(LINK_URL + input + "/tv");


        return new TextMessage(out.contains("Invalid")
                ? "There's no anime at " + input : out);
    }

    public void setUrl() {
        this.url = LINK_URL + season + "-" + year + "/tv";
    }

    public String getUrl() {
        return url;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}