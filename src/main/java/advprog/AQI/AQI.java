package advprog.AQI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

public class AQI {

    private static final Logger log = LoggerFactory.getLogger(AQI.class);

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        Response response = restTemplate.getForObject("https://api.waqi.info/feed/shanghai/?token=2f6ceb4348889952bdb952ba97f59efc8b5db2d0", Response.class);
        log.info(response.toString());
        System.out.println(response.data.aqi);
    }
}