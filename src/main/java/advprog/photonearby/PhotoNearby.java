package advprog.photonearby;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
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
        String[] result;
        try{
            String xml = post(params);
            result = parseXML(xml);
            return result;
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

    private static String[] parseXML(String xml)throws Exception{
        String[] result = new String[5];
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(xml));

        Document doc = builder.parse(src);

        NodeList rsp = doc.getElementsByTagName("rsp").item(0).getChildNodes();
        NodeList photoList = rsp.item(1).getChildNodes();
        int counter = 0;
        for(int i = 0; i<photoList.getLength(); i++){
            Node node = photoList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap photo = node.getAttributes();
                String id = photo.getNamedItem("id").getNodeValue();
                String secret = photo.getNamedItem("secret").getNodeValue();
                String server = photo.getNamedItem("server").getNodeValue();
                String farm = photo.getNamedItem("farm").getNodeValue();
                if(counter<5){
                    result[counter] = String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg",
                            farm, server, id, secret);
                }
                counter++;
            }
        }
        return result;
    }
}
