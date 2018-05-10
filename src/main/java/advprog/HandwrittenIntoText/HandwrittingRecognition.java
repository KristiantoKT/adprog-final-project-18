package advprog.HandwrittenIntoText;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class HandwrittingRecognition {
    private final String subscriptionKey = "f2cd37b7c976497ea4c98031f7e32767";
    public static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/recognizeText?handwriting=true";

    public HandwrittingRecognition() {
    }

    public String convertImageToString(String urlImage) {
        try{
            String operationLocation = submitImage(urlImage);
            if(operationLocation == null) {
                return "[ERROR] Can't Recognize The Image.";
            }
            Thread.sleep(8000);
            JSONObject resultJson = retrieveText(operationLocation);
            if(resultJson == null) {
                return "[ERROR] Can't Recognize The Image.";
            }
            return convertResultFromJson(resultJson);

        } catch (InterruptedException e){
            return "[ERROR] Process Interrupted";
        }
    }

    private String convertResultFromJson(JSONObject json) {
        JSONArray lines = json.getJSONObject("recognitionResult").getJSONArray("lines");
        String hasil = "";
        for (int i = 0; i < lines.length(); i++) {
            JSONObject jsonobject = lines.getJSONObject(i);
            hasil += jsonobject.getString("text") + "\n";
        }
        return hasil;
    }

    private String submitImage(String urlImage) {
        HttpClient textClient = new DefaultHttpClient();

        try {
            // Begin the REST API call to submit the image for processing.
            URI uri = new URI(uriBase);
            HttpPost textRequest = new HttpPost(uri);

            // Request headers. Another valid content type is "application/octet-stream".
            textRequest.setHeader("Content-Type", "application/json");
            textRequest.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            StringEntity requestEntity =
                    new StringEntity("{\"url\":\"" + urlImage + "\"}");
            textRequest.setEntity(requestEntity);

            // Execute the first REST API call to detect the text.
            HttpResponse textResponse = textClient.execute(textRequest);

            // Check for success.
            if (textResponse.getStatusLine().getStatusCode() != 202)
            {
                // Format and display the JSON error message.
                HttpEntity entity = textResponse.getEntity();
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                System.out.println(json.toString(2));
                return null;
            }

            String operationLocation = null;

            // The 'Operation-Location' in the response contains the URI to retrieve the recognized text.
            Header[] responseHeaders = textResponse.getAllHeaders();
            for(Header header : responseHeaders) {
                if(header.getName().equals("Operation-Location"))
                {
                    // This string is the URI where you can get the text recognition operation result.
                    operationLocation = header.getValue();
                    break;
                }
            }
            return operationLocation;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private JSONObject retrieveText(String operationLocation){
        HttpClient resultClient = new DefaultHttpClient();

        try{
            // Execute the second REST API call and get the response.
            HttpGet resultRequest = new HttpGet(operationLocation);
            resultRequest.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            HttpResponse resultResponse = resultClient.execute(resultRequest);
            HttpEntity responseEntity = resultResponse.getEntity();

            if (responseEntity != null)
            {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(responseEntity);
                JSONObject json = new JSONObject(jsonString);
                return json;
            } else{
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}