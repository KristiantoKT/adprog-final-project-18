package advprog.anison.bot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class ItuneSearch {

    public static final String fixedURL = "https://itunes.apple.com/lookup?id=";

    public static String getSongClipLink(int id) throws Exception {
        String urlString = fixedURL + id;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuffer html = new StringBuffer();

        while ((line = in.readLine()) != null) {
            html.append(line);
        }
        in.close();

        System.out.println(html.toString());

        JSONObject song = new JSONObject(html.toString());
        JSONArray songArr = song.getJSONArray("results");
        String link = songArr.getJSONObject(0).getString("previewUrl");

        return link;

    }
}
