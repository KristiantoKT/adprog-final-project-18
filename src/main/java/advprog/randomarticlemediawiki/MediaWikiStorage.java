package advprog.randomarticlemediawiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class MediaWikiStorage {
    private static final String csvFileName = "mediawikistorage.csv";

    public MediaWikiStorage() {
    }

    public static boolean addUrl(String url) {
        FileWriter writer = null;
        try {
            File file = new File(csvFileName);
            writer = new FileWriter(file, true);
            String[] current = getUrl();
            boolean isExist = isExistCheck(current, url);
            if (isExist) {
                writer.close();
                return false;
            } else {
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
            if (e.equalsIgnoreCase(text)) {
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
        } catch (Exception e) {
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

    public static boolean isMediaWikiApiActive(String webPage) {
        try {
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();
            return ((HttpURLConnection)urlConnection).getResponseCode() == 200
                    && result.contains("MediaWiki")
                    && result.contains("API");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static void hapusFile() {
        File file = new File(csvFileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
