package advprog.similiarity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Similiarity {

    private double time;
    private double similiarity;
    private double langConfidence;
    private String lang;
    private String timeStamp;

    // url cases
    private String url1;
    private String url2;
    private String text1;
    private String text2;


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

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
