package advprog.sentiment.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotSentimentApplication {

    private static final Logger LOGGER = Logger.getLogger(BotSentimentApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BotSentimentApplication.class, args);
    }
}
