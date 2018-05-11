package advprog.handwrittenintotext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecognitionResult {
    private Lines[] lines;

    public Lines[] getLines() {
        return lines;
    }

    public void setLines(Lines[] lines) {
        this.lines = lines;
    }
}
