package advprog.speechtotext.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class FetchStuff {

    static String fetchURL = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
    static String subscriptionKey = "afc6af61d93a4d798378d287919ae9cb";
    static String token = "";

    public static String getTokenFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        String kembalian = restTemplate.postForObject(fetchURL,
                new HttpEntity<String>("", headers),
                String.class);
        return kembalian;
    }

    public static Text getTextFromSpeech(byte[] soundByte) throws IOException {
        if (token.length() == 0) {
            token = getTokenFromApi();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
        headers.add("Authorization", "Bearer " + token);
        headers.set("Content-Type", "audio/wav; codec=\"audio/pcm\"; samplerate=\"16000\"");
        headers.set("Transfer-Encoding", "chunked");
        HttpEntity<byte[]> entityBytes = new HttpEntity<byte[]>(soundByte, headers);
        RestTemplate restTemplate = new RestTemplate();
        String kembalian = restTemplate.postForObject("https://speech.platform.bing.com/speech/recognition/conversation"
                + "/cognitiveservices/v1?language=en-US", entityBytes, String.class);
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        Text text = new Text((String) jacksonJsonParser.parseMap(kembalian).get("DisplayText"));
        return text;
    }
}
