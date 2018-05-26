package advprog.randomarticlemediawiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class MediaWikiStorage {
    private static final String csvFileName = "mediawikistorage.csv";

    public MediaWikiStorage() {
    }

    public static boolean addUrl(String url) {
        FileWriter writer = null;
        try {
            String[] current = getUrl();
            boolean isExist = isExistCheck(current, url);
            if (isExist) {
                writer.close();
                return false;
            } else {
                File file = new File(csvFileName);
                writer = new FileWriter(file, true);
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                sb.append((','));
                writer.write(sb.toString());
                writer.close();
                return true;
            }
        } catch (IOException e) {
            e.getStackTrace();
            return false;
        }
    }

    public static boolean isExistCheck(String[] arr, String text) {
        boolean hasil = false;
        for (String e: arr) {
            if (e.equalsIgnoreCase("text")) {
                hasil = true;
            }
        }
        return hasil;
    }

    public static String[] getUrl() {
        String[] hasil = new String[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFileName));
            hasil = br.readLine().split(",");
        } catch (IOException e) {
            hasil = new String[0];
        }
        return hasil;
    }

    public static Article getRandomArticle(String url) {
        RestTemplate rest = new RestTemplate();

        //Get Title And URL
        String titleAndUrl = url + "?format=json&action=query&"
                + "generator=random&grnnamespace=0&grnlimit=1&prop=info&inprop=url";
        ResponseEntity<String> responseEntity = rest.getForEntity(titleAndUrl, String.class);
        JSONObject json1 = new JSONObject(responseEntity.getBody());
        JSONObject pages = json1.getJSONObject("query").getJSONObject("pages");
        Set<String> keysSet = pages.keySet();
        String key = keysSet.toArray()[0].toString();
        String title = pages.getJSONObject(key).getString("title");
        String urlArticle = pages.getJSONObject(key).getString("fullurl");

        return new Article(title,"Can't get summary", urlArticle, null);
    }

    public static boolean isMediaWikiApiActive(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(url, String.class);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return response != null
                && response.getStatusCode() == HttpStatus.OK
                && response.getBody().contains("MediaWiki")
                && response.getBody().contains("API");
    }
}
