package advprog.example.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YoutubeApplication {

    private static final Logger LOGGER = Logger.getLogger(YoutubeApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(YoutubeApplication.class, args);
    }
}
