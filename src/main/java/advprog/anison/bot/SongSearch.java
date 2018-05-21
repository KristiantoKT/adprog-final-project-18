package advprog.anison.bot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;



public class SongSearch {

    public static final String fixedURL = "https://schoolido.lu/api/songs/?search=";

    public static int findItunesId(String song) throws Exception {
        song = song.replace(" ","+");
        String urlString = fixedURL + song;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection.getResponseCode() == 301) {
            String newUrl = connection.getHeaderField("Location");
            connection = (HttpURLConnection) new URL(newUrl).openConnection();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuffer html = new StringBuffer();

        while ((line = in.readLine()) != null) {
            html.append(line);
        }
        in.close();

        JSONObject json = new JSONObject(html.toString());
        int id = 0;
        JSONObject target;
        try {
            target = json.getJSONArray("results").getJSONObject(0);
        } catch (Exception e) {
            return -1;
        }

        try {
            id = target.getInt("itunes_id");
        } catch (Exception e) {
            return -2;
        }

        return id;

    }

}
