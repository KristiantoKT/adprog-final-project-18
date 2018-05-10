package advprog.similiarity;

import java.net.URL;
import java.net.URLConnection;
import org.springframework.web.client.RestTemplate;


public class SimiliartyChecker {

    public static double checkSimiliarity(String param1, String param2) {
        // default value
        double result = 0;
        String link = "https://api.dandelion.eu/datatxt/sim/v1/?";
        String token = "d94d7f89ffb74f21bbde0ea160e9364a";
        String type;

        type = checkUrlValidity(param1,param2) ? "url" : "text";

        String target = link + type
                + "1=" + param1 + "&" + type + "2=" + param2 + "&token=" + token;

        RestTemplate restTemplate = new RestTemplate();
        Similiarity similiarity = restTemplate.getForObject(target,Similiarity.class);

        result = similiarity.getSimilarity() * 100;
        return result;
    }


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
