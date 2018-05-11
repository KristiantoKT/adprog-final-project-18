package advprog.example.bot.oriconsingle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperConfig {

    @Bean
    Scrapper scrapper() {
        return new Scrapper();
    }
}
