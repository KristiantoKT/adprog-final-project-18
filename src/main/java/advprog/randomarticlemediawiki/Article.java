package advprog.randomarticlemediawiki;

public class Article {
    private String title;
    private String summary;
    private String link;
    private String imageUrl;

    public Article(String title, String summary, String link, String imageUrl) {
        this.title = title;
        this.summary = summary;
        this.link = link;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
