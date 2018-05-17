package advprog.example.bot.command;

import advprog.example.bot.livechart.anime.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class WinterCommand implements SeasonCommand {
    private Scrapping scrapping = new Scrapping();

    static final String WINTER_URL = "https://www.livechart.me/winter-";

    @Override
    public TextMessage execute(String input) throws IOException {
        splitInput(input);
        String out = scrapping.scrap(WINTER_URL + input + "/tv");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }

    public String splitInput(String input) {
        String[] inputDetail = input.split("-");
        input = inputDetail[1];
        return input;
    }
}
