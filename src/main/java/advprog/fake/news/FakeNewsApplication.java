package advprog.fake.news;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FakeNewsApplication {

    private static final Logger LOGGER = Logger.getLogger(FakeNewsApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(FakeNewsApplication.class, args);
    }
}
