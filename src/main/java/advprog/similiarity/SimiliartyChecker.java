package advprog.similiarity;

import java.net.URL;
import java.net.URLConnection;
import org.springframework.web.client.RestTemplate;


public class SimiliartyChecker {

    public static double checkSimiliarity(String param) {
        // default value is 0
        double result = 0;

        // link to the api
        String link = "https://api.dandelion.eu/datatxt/sim/v1/?";

        // link of my api token
        String token = "d94d7f89ffb74f21bbde0ea160e9364a";

        // parse the input
        String[] data = parseInput(param);

        if (data[0].equals("error")) {
            return -1;
        }

        // generate link to the api with the input content
        String target = link + data[0]
                + "1=" + data[1] + "&" + data[0] + "2=" + data[2] + "&token=" + token;

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

    // method to parse the input
    // return according to text sent by user
    public static String[] parseInput(String input) {
        String[] parsed = new String[3];
        parsed[0] = "error";

        String[] temp = input.split("' '");
        if (temp.length == 2) { // it's a text
            if (temp[0].charAt(0) == '\'' && temp[1].charAt(temp[1].length() - 1) == '\'') {
                // and completely enclosed
                parsed[0] = "text";
                parsed[1] = temp[0].substring(1);
                parsed[2] = temp[1].substring(0,temp[1].length() - 1);
            }
        } else { // it's an url
            temp = input.split(" ");
            if (temp.length == 2 && checkUrlValidity(temp[0],temp[1])) {
                parsed[0] = "url";
                parsed[1] = temp[0];
                parsed[2] = temp[1];
            }
        }
        return parsed;
    }
}
