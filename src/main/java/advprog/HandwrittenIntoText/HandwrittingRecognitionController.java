package advprog.HandwrittenIntoText;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import retrofit2.http.POST;

import javax.swing.text.html.parser.Entity;

public class HandwrittingRecognitionController {
    RestTemplate rest = new RestTemplate();
    private final String subscriptionKey = "f2cd37b7c976497ea4c98031f7e32767";
    public static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/recognizeText?handwriting=true";

    public HandwrittingRecognitionController() {


    }

    public String convertImageToString(String urlImage){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Ocp-Apim-Subscription-Key", subscriptionKey);

            String stringEntity = "{\"url\":\"" + urlImage + "\"}";
            HttpEntity<String> entity = new HttpEntity<String>(stringEntity, headers);
            Thread.sleep(8000);

            //URI uri = rest.postForLocation(uriBase, entity);

            ResponseEntity<String> postEntity = rest.postForEntity(uriBase, String.class, entity, String);
            URI uri = postEntity.getHeaders().getLocation();

            //String operationLocation = postEntity.getHeaders().get("Operation-Location");
            //System.out.println(operationLocation);
            Handwritting handwritting = rest.getForObject(uri, Handwritting.class);
            return handwritting.getStatus();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static void main(String[] args) {
        HandwrittingRecognitionController test = new HandwrittingRecognitionController();
        String hasil = test.convertImageToString("http://www.fki.inf.unibe.ch/databases/iam-on-line-handwriting-database/images/processed-strokes.png");
        System.out.println(hasil);
    }

}
