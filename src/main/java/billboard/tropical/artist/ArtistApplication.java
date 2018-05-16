package billboard.tropical.artist;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArtistApplication {
    private static final Logger LOGGER =
            Logger.getLogger(ArtistApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(ArtistApplication.class, args);
    }
}
