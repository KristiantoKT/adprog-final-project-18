package advprog.billboard.japan;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillboardJapanApplication {
    private static final Logger LOGGER = Logger.getLogger(BillboardJapanApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BillboardJapanApplication.class, args);
    }
}
