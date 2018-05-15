package advprog.handwrittenintotext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Words {
    private int[] boundingBox;
    private String text;


}
