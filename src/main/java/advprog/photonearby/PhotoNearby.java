package advprog.photonearby;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoNearby {

    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "d28aed97f55b89db2b64aef301be7c21";

    public String[] searchImg(String latitude, String longtitude) {
        //Create map of paramaters
        Map<String, String> params = new HashMap<String,String>();
        params.put("method","flickr.photos.search");
        params.put("api_key",API_KEY);
        params.put("lat", latitude);
        params.put("lon", longtitude);

        //Post request to flick api
        String result;
        try{
            result = post(params);
//            parseXML(result);
            return new String[] {result};
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String post(Map<String,String> params) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(ENDPOINT);

        // Request parameters anf other properties
        List<NameValuePair> postParams = new ArrayList<>();
        for (String k : params.keySet()) {
            postParams.add(new BasicNameValuePair(k, params.get(k)));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(postParams, StandardCharsets.UTF_8));

        // Execute and get the response
        HttpResponse response = httpClient.execute(httpPost);
        InputStream resultStream = response.getEntity().getContent();
        return IOUtils.toString(resultStream, String.valueOf(StandardCharsets.UTF_8));
    }
}
