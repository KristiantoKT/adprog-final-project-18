package advprog.randomarticlemediawiki;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;

public class MediaWikiStorage {
    private final String csvFileName = "mediawikistorage.csv";

    public MediaWikiStorage() {
    }

    public void addUrl(String url) {
        FileWriter writer = null;
        try {
            File file = new File(csvFileName);
            writer = new FileWriter(file, true);
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append((','));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    public String[] getUrl() {
        String[] hasil = new String[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFileName));
            hasil = br.readLine().split(",");
        } catch (IOException e) {
            hasil = new String[0];
        }
        return hasil;
    }

    public Article getRandomArticle(String url) {
        RestTemplate rest = new RestTemplate();
        url = url + "?format=json&action=query&generator=random&grnnamespace=0&grnlimit=1&prop=info&inprop=url";
        ResponseEntity<String> responseEntity = rest.getForEntity(url, String.class);
        JSONObject json1 = new JSONObject(responseEntity.getBody());
        JSONObject pages = json1.getJSONObject("query").getJSONObject("pages");
        Set keysSet = pages.keySet();
        String key = keysSet.toArray()[0].toString();
        String title = pages.getJSONObject(key).getString("title");
        String urlArticle = pages.getJSONObject(key).getString("fullurl");
        return new Article(title,"",urlArticle);
    }
}
