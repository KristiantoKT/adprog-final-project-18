package advprog.anison.bot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;



public class SongSearch {

    public static final String fixedURL = "https://schoolido.lu/api/songs/?search=";

    public static void main(String[] args) throws Exception{
        SongCsvWriter.writeSong("test","bokutachi wa hitotsu no hikari");
        //SongCsvReader.readSong("test");
    }

    public static String findSongTrueName(String song) throws Exception {
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
        String trueName;
        JSONObject target;

        target = json.getJSONArray("results").getJSONObject(0);
        trueName = target.getString("romaji_name");
        if (trueName.equalsIgnoreCase("null")) {
            trueName = target.getString("name");
        }
        return trueName;
    }

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

        System.out.println(html);

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

    public static String findImageUrl(String song) throws Exception {
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
        String imageUrl;
        JSONObject target;

        target = json.getJSONArray("results").getJSONObject(0);
        imageUrl = target.getString("image");
        return imageUrl;
    }

}
