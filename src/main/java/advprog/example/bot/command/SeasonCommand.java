package advprog.example.bot.command;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public interface SeasonCommand {
    TextMessage execute(String input, String genre) throws IOException;
}
