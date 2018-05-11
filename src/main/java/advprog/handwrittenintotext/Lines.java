package advprog.handwrittenintotext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lines {
    private int[] boundingBox;
    private Words[] words;
    private String text;

    public int[] getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(int[] boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Words[] getWords() {
        return words;
    }

    public void setWords(Words[] words) {
        this.words = words;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
