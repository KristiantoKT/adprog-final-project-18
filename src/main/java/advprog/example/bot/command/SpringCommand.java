package advprog.example.bot.command;

import advprog.example.bot.livechart.anime.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class SpringCommand implements SeasonCommand {
    private Scrapping scrapping = new Scrapping();

    static final String SPRING_URL = "https://www.livechart.me/spring-";

    @Override
    public TextMessage execute(String input, String genre) throws IOException {
        splitInput(input);
        String out = scrapping.scrap(SPRING_URL + input + "/tv");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }

    public String splitInput(String input) {
        String[] inputDetail = input.split("-");
        input = inputDetail[1];
        return input;
    }
}
