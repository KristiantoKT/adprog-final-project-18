package advprog.bikun.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotBikunApplication {
    private static final Logger LOGGER = Logger.getLogger(BotBikunApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BotBikunApplication.class, args);
    }
}
