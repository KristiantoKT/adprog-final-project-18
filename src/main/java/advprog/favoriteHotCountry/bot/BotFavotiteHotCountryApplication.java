package advprog.favoriteHotCountry.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotFavotiteHotCountryApplication {
    private static final Logger LOGGER = Logger.getLogger(BotFavotiteHotCountryApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BotFavotiteHotCountryApplication.class, args);
    }
}
