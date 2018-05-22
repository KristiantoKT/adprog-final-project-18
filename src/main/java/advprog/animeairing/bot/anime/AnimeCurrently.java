package advprog.animeairing.bot.anime;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.util.ArrayList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AnimeCurrently {
    private String anime;
    private ArrayList<AnimeAiring> currentAiring;
    final String urlApi = "https://myanimelist.net/api/anime/search.xml?q=";

    public AnimeCurrently(String anime) {
        this.anime = anime;
        this.currentAiring = new ArrayList<>();
        animeAiring(anime);
    }

    public String getAnime() {
        return anime;
    }

    public ArrayList<AnimeAiring> getCurrentAiring() {
        return currentAiring;
    }

    public String getUrlApi() {
        return urlApi;
    }

    private void animeAiring(String anime) {
        try {
            String plainCreds = "advprobeb:Advpro";
            String basicAuth = "Basic " + printBase64Binary(plainCreds.getBytes());
            String convertTitle = anime.replace(" ", "+");
            String url = urlApi + convertTitle;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", basicAuth);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<String> response = restTemplate
                    .exchange(url, HttpMethod.GET, entity, String.class);
            String str = response.getBody();

            String title = str.split("\n")[4].replace("<title>", "")
                    .replace("</title>", "").replace("    ", "");
            String status = str.split("\n")[10].replace("<status>", "")
                    .replace("</status>", "").replace("    ", "");
            String startDate = str.split("\n")[11].replace("<start_date>", "")
                    .replace("</star_date>", "").replace("    ", "");
            String endDate = str.split("\n")[12].replace("<end_date>", "")
                    .replace("</end_date>", "").replace("    ", "");

            AnimeAiring airing = new AnimeAiring(title, status, startDate, endDate);
            currentAiring.add(airing);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer");
        }
    }

    public String infoAiring() {
        if (currentAiring.size() == 0) {
            System.out.println("Sorry, Anime cannot be found");
        }

        StringBuilder str = new StringBuilder();
        for (AnimeAiring anime : currentAiring) {
            if (anime.getStatus().equalsIgnoreCase("Currently Airing")) {
                str.append(anime.getTitle()
                        + " is airing from "
                        + anime.getStartDate()
                        + " until " + anime.getEndDate());
            } else if (anime.getStatus().equalsIgnoreCase("Finished Airing")) {
                str.append(anime.getTitle() + " has finished airing at " + anime.getEndDate());
            } else {
                str.append(anime.getTitle() + " will air starting at " + anime.getStartDate());
            }
        }

        return str.toString();
    }
}
