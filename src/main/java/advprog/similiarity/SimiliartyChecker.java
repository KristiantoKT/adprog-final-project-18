package advprog.similiarity;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SimiliartyChecker {

    public static double checkSimiliarity(String param1, String param2) {
        // default value
        double result = 0;




        return result;
    }


    public static boolean checkUrlValidity(String link) {
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
