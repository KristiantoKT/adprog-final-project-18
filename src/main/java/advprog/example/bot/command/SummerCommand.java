package advprog.example.bot.command;

import advprog.example.bot.livechart.anime.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class SummerCommand implements SeasonCommand{
    private Scrapping scrapping = new Scrapping();

    static final String SUMMER_URL = "https://www.livechart.me/summer-";

    @Override
    public TextMessage execute(String input) throws IOException {
        splitInput(input);
        String out = scrapping.scrap(SUMMER_URL + input + "/tv");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }

    public String splitInput(String input){
        String[] inputDetail = input.split("-");
        input = inputDetail[1];
        return input;
    }
}
