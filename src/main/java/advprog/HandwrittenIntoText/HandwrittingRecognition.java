package advprog.HandwrittenIntoText;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

public class HandwrittingRecognition {
    private RestTemplate rest = new RestTemplate();
    private HttpHeaders defaultHeader;
    private final String subscriptionKey = "f2cd37b7c976497ea4c98031f7e32767";
    private static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/recognizeText?handwriting=true";

    public HandwrittingRecognition() {
        defaultHeader = new HttpHeaders();
        defaultHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        defaultHeader.setContentType(MediaType.APPLICATION_JSON);
        defaultHeader.set("Ocp-Apim-Subscription-Key", subscriptionKey);
    }

    public String convertImageToString(String urlImage) {
        try {
            String operationLocation = submitImage(urlImage);
            Handwritting result = retrieveText(operationLocation);
            return result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String submitImage(String urlImage) throws Exception {
        String stringEntity = "{\"url\":\"" + urlImage + "\"}";
        HttpEntity<String> entity = new HttpEntity<String>(stringEntity, this.defaultHeader);
        ResponseEntity<String> postEntity = rest.postForEntity(uriBase, entity, String.class);
        if(postEntity.getStatusCodeValue() != 202) {
            throw new Exception("[ERROR]" + postEntity.getStatusCodeValue());
        }

        String operationLocation = postEntity.getHeaders().get("Operation-Location").get(0);
        return operationLocation;
    }

    private Handwritting retrieveText(String operationLocation) throws Exception {
        HttpEntity<String> entityResult = new HttpEntity<String>(defaultHeader);
        ResponseEntity<Handwritting> resultResponseEntity= rest.exchange(operationLocation, HttpMethod.GET, entityResult, Handwritting.class);
        String status = resultResponseEntity.getBody().getStatus();
        while(status.equals("Running")) {
            if(resultResponseEntity.getStatusCodeValue() != 200) {
                throw new Exception("[ERROR] " + + resultResponseEntity.getStatusCodeValue());
            }
            resultResponseEntity= rest.exchange(operationLocation, HttpMethod.GET, entityResult, Handwritting.class);
            status = resultResponseEntity.getBody().getStatus();
            Thread.sleep(1000);
        }
        if(!status.equals("Succeeded")) {
            throw new Exception("[ERROR] "+ status);
        }
        return resultResponseEntity.getBody();
    }
}
