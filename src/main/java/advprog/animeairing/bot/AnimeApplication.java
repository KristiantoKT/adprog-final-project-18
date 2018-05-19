package advprog.animeairing.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimeApplication {
    private static final Logger LOGGER = Logger.getLogger(AnimeApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(AnimeApplication.class, args);
    }
}
