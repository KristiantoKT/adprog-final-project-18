package advprog.example.bot.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageFromLine {
    private String type;
    private String originalContentUrl;
    private String previewImageUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalContentUrl() {
        return originalContentUrl;
    }

    public void setOriginalContentUrl(String originalContentUrl) {
        this.originalContentUrl = originalContentUrl;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }
}
