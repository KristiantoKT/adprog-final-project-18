package advprog.alquran;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class FathimahApi {

    private static final String ENDPOINT = "https://api.fathimah.xyz/quran/format/json/";
    private static final String SURAH = "surat/";
    private static final String VERSE = "/ayat/";

    public static Surah getSurah(int surah)
            throws IndexOutOfBoundsException, IOException {
        String url = ENDPOINT + SURAH + surah;
        String getResult = getRequest(url);
        System.out.println(getResult);
        return null;
    }

    public static Verse getVerse(int verse, int surah)
            throws IndexOutOfBoundsException, IOException {
        String url = ENDPOINT + SURAH + surah + VERSE + verse;
        String getResult = getRequest(url);
        System.out.println(getResult);
        return null;
    }

    public static Verse[] getVerses(int startVerse, int endVerse, int surah)
            throws IndexOutOfBoundsException, IOException {
        String url = ENDPOINT + SURAH + surah + VERSE + startVerse + "-" + endVerse;
        String getResult = getRequest(url);
        System.out.println(getResult);
        return null;
    }

    private static Surah toSurah(String json) {
        return null;
    }

    private static Verse toVerse(String json) {
        return null;
    }

    private static Verse[] toVerses(String json) {
        return null;
    }

    private static String getRequest(String url) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        InputStream resultStream = response.getEntity().getContent();
        return IOUtils.toString(resultStream, String.valueOf(StandardCharsets.UTF_8));
    }
}
