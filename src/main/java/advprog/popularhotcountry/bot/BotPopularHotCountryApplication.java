package advprog.popularhotcountry.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotPopularHotCountryApplication {

    private static final Logger LOGGER = Logger.getLogger(
            BotPopularHotCountryApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BotPopularHotCountryApplication.class, args);
    }
}
