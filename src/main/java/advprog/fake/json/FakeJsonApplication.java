package advprog.fake.json;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FakeJsonApplication {
    private static final Logger LOGGER = Logger.getLogger(FakeJsonApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(FakeJsonApplication.class, args);
    }
}
