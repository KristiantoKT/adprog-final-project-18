package advprog.example.bot.command;

import advprog.example.bot.livechart.anime.ActionScrap;
import advprog.example.bot.livechart.anime.ComedyScrap;
import advprog.example.bot.livechart.anime.FantasyScrap;
import advprog.example.bot.livechart.anime.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class SummerCommand implements SeasonCommand {
    private Scrapping scrapping = new Scrapping();
    private ActionScrap as = new ActionScrap();
    private ComedyScrap cs = new ComedyScrap();
    private FantasyScrap fs = new FantasyScrap();

    String out;

    static final String SUMMER_URL = "https://www.livechart.me/summer-";

    @Override
    public TextMessage execute(String input, String genre) throws IOException {
        splitInput(input);
        if (genre.equalsIgnoreCase("Action")) {
            out = as.actionScrap(SUMMER_URL + input + "/tv");
        } else if (genre.equalsIgnoreCase("Comedy")) {
            out = cs.comedyScrap(SUMMER_URL + input + "/tv");
        } else if (genre.equalsIgnoreCase("Fantasy")) {
            out = fs.fantasyScrap(SUMMER_URL + input + "/tv");
        }
        out = scrapping.scrap(SUMMER_URL + input + "/tv");


        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }

    public String splitInput(String input) {
        String[] inputDetail = input.split("-");
        input = inputDetail[1];
        return input;
    }
}
