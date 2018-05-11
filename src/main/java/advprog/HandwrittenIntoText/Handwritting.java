package advprog.HandwrittenIntoText;

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
}
