package advprog.similiarity;

import java.net.URL;
import java.net.URLConnection;
import org.springframework.web.client.RestTemplate;


public class SimiliartyChecker {

    public static double checkSimiliarity(String param1, String param2) {
        // default value is 0
        double result = 0;

        // link to the api
        String link = "https://api.dandelion.eu/datatxt/sim/v1/?";

        // link of my api token
        String token = "d94d7f89ffb74f21bbde0ea160e9364a";

        // type of data inserted, url or plain text
        String type;

        // decide type of input, if it can connect, it's an url, if not it's a text.
        // even if its a broken url, it'll be treated as text
        type = checkUrlValidity(param1,param2) ? "url" : "text";

        // generate link to the api with the input content
        String target = link + type
                + "1=" + param1 + "&" + type + "2=" + param2 + "&token=" + token;

        // some language besides english cannot be processed, causing error
        // if so, result will be -1, will be processed as error
        try {
            RestTemplate restTemplate = new RestTemplate();
            Similiarity similiarity = restTemplate.getForObject(target, Similiarity.class);
            result = similiarity.getSimilarity() * 100;
        } catch (Exception e) {
            result = -1;
        }

        return result;
    }


    // method to check url validity
    // by tring to connect, if error return false
    public static boolean checkUrlValidity(String link1, String link2) {
        try {
            URL url1 = new URL(link1);
            URL url2 = new URL(link2);
            URLConnection conn1 = url1.openConnection();
            URLConnection conn2 = url2.openConnection();
            conn1.connect();
            conn2.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
