package advprog.handwrittenintotext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Handwritting {
    private String status;
    private RecognitionResult recognitionResult;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RecognitionResult getRecognitionResult() {
        return recognitionResult;
    }

    public void setRecognitionResult(RecognitionResult recognitionResult) {
        this.recognitionResult = recognitionResult;
    }

    @Override
    public String toString() {
        String result = "";
        Lines[] rec = recognitionResult.getLines();
        for(int i = 0; i < rec.length; i++) {
            result += rec[i].getText() + "\n";
        }
        return result;
    }
}
