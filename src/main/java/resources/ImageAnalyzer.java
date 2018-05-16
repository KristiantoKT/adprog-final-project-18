import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


@LineMessageHandler
public class ImageAnalyzer {
    private final String subscriptionKey = "efafa6ed2589473e82e7a4ae95531c85";
    private final String uriBase = "https://southeastasia.api.cognitive.microsoft.com/vision/v1.0/analyze";
    private byte[] image = null;
    private String result = "";

    public ImageAnalyzer(byte[] image) {
        this.image = image;
    }

    public void imageAnalyzer() {
        HttpClient httpclient;
        httpclient = HttpClientBuilder.create().build();
        String result = "initialize";

        try {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters. All of them are optional.
            builder.setParameter("visualFeatures", "Categories,Description,Color");
            builder.setParameter("language", "en");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
            request.setHeader("Authorization", "Bearer DtMIA6q8oiBXP7+V265e/tkVGqMDBS2UtSOne"
                    + "PNnYuKwTfkf70xsybY41BPyr+3pgzywmMZsn7eFF3BeEtyKKf2lZbKnFmtJ3aMCkT"
                    + "bA6K2aGbnk54Ew2aMTeZFVBhpkkylO"
                    + "HXHKuY4TUzRqJqUgYAdB04t89/1O/w1cDnyilFU=");

            // Request body
            HttpEntity reqEntity = EntityBuilder.create().setBinary(image).build();
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                result = "REST Response:\n";
                result = result + json.toString(2);
            }
        } catch (Exception e) {
            // Display error message.
            result = e.getMessage();
        }
    }

    public String getResult() {
        return result;
    }

}
