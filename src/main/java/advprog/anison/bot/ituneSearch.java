package advprog.anison.bot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ituneSearch {

    public static final String fixedURL = "https://itunes.apple.com/lookup?id=";

    public static void main(String[] args) throws Exception {
        System.out.println(getSongClipLink(587762424));
    }

    public static String getSongClipLink(int id) throws Exception {
        String urlString = fixedURL+id;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader in = new BufferedReader
                (new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuffer html = new StringBuffer();

        while ((line = in.readLine()) != null) {
            html.append(line);
        }
        in.close();

        JSONObject song = new JSONObject(html.toString());
        JSONArray songArr = song.getJSONArray("results");
        String link = songArr.getJSONObject(0).getString("previewUrl");

        return link;

    }
}
