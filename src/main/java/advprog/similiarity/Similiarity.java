package advprog.similiarity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Similiarity {

    private double time;
    private double similiarity;
    private double langConfidence;
    private String lang;
    private String timeStamp;

    public Similiarity() {}

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getSimiliarity() {
        return similiarity;
    }

    public void setSimiliarity(double similiarity) {
        this.similiarity = similiarity;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public double getLangConfidence() {
        return langConfidence;
    }

    public void setLangConfidence(double langConfidence) {
        this.langConfidence = langConfidence;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
