package advprog.speechtotext.bot;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FetchStuff {

    static String fetchURL = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
    static String subscriptionKey = "afc6af61d93a4d798378d287919ae9cb";
    static String token = "";

    public static String getTokenFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        String kembalian = restTemplate.postForObject(fetchURL, new HttpEntity<String>("", headers), String.class);
        return kembalian;
    }

    public static Text getTextFromSpeech() throws IOException {
        if(token.length() == 0) {
            token = getTokenFromAPI();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
        headers.add("Authorization", "Bearer " + token);
        headers.set("Content-Type", "audio/wav; codec=\"audio/pcm\"; samplerate=\"16000\"");
        headers.set("Transfer-Encoding", "chunked");
        String path = "soundfile/soundtest.wav";
        File fileSuara = new File(path);
        FileInputStream fileStream = new FileInputStream(fileSuara);
        byte[] bytes = new byte[(int) fileSuara.length()];
        fileStream.read(bytes);
        fileStream.close();
        HttpEntity<byte[]> entityBytes = new HttpEntity<byte[]>(bytes, headers);
        RestTemplate restTemplate = new RestTemplate();
        String kembalian = restTemplate.postForObject("https://speech.platform.bing.com/speech/recognition/conversation" +
                "/cognitiveservices/v1?language=en-US", entityBytes, String.class);
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        Text text = new Text((String) jacksonJsonParser.parseMap(kembalian).get("DisplayText"));
        return text;
    }
}
