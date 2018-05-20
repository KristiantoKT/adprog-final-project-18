package advprog.example.bot.command;

import advprog.example.bot.livechart.anime.ActionScrap;
import advprog.example.bot.livechart.anime.ComedyScrap;
import advprog.example.bot.livechart.anime.FantasyScrap;
import advprog.example.bot.livechart.anime.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class WinterCommand implements SeasonCommand {
    private Scrapping scrapping = new Scrapping();
    private ActionScrap as = new ActionScrap();
    private ComedyScrap cs = new ComedyScrap();
    private FantasyScrap fs = new FantasyScrap();

    String out;

    static final String WINTER_URL = "https://www.livechart.me/winter-";

    @Override
    public TextMessage execute(String input, String genre) throws IOException {
        splitInput(input);
        if (genre.equalsIgnoreCase("Action")) {
            out = as.actionScrap(WINTER_URL + input + "/tv");
        } else if (genre.equalsIgnoreCase("Comedy")) {
            out = cs.comedyScrap(WINTER_URL + input + "/tv");
        } else if (genre.equalsIgnoreCase("Fantasy")) {
            out = fs.fantasyScrap(WINTER_URL + input + "/tv");
        }
        out = scrapping.scrap(WINTER_URL + input + "/tv");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }

    public String splitInput(String input) {
        String[] inputDetail = input.split("-");
        input = inputDetail[1];
        return input;
    }
}
