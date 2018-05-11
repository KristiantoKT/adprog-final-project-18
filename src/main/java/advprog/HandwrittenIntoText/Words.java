package advprog.HandwrittenIntoText;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Words {
    private int[] boundingBox;
    private String text;

    public int[] getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(int[] boundingBox) {
        this.boundingBox = boundingBox;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
