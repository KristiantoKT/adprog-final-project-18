package advprog.youTube.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class YoutubeInfoApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = Logger.getLogger(YoutubeInfoApplication.class.getName());

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(YoutubeInfoApplication.class);
    }

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(YoutubeInfoApplication.class, args);
    }
}
