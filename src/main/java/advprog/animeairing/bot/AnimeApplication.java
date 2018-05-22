package advprog.animeairing.bot;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class AnimeApplication extends SpringBootServletInitializer {
    private static final Logger LOGGER = Logger
            .getLogger(AnimeApplication.class.getName());

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AnimeApplication.class);
    }


    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(AnimeApplication.class, args);
    }
}
