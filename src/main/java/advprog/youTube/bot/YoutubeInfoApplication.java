package advprog.youTube.bot;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YoutubeInfoApplication {

    private static final Logger LOGGER = Logger.getLogger(YoutubeInfoApplication.class.getName());


    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(YoutubeInfoApplication.class, args);

    }
}
