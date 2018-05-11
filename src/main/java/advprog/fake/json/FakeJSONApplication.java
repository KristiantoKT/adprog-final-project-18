package advprog.fake.json;

import org.springframework.boot.SpringApplication;

import java.util.logging.Logger;

public class FakeJSONApplication {
    private static final Logger LOGGER = Logger.getLogger(FakeJSONApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(FakeJSONApplication.class, args);
    }
}
