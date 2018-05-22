package advprog.fake.news;

public class FakeNews {
    private String type;
    private String type2;
    private String type3;
    private String sourceNotes;

    public FakeNews(String type, String type2, String type3, String sourceNotes) {
        this.type = type;
        this.type2 = type2;
        this.type3 = type3;
        this.sourceNotes = sourceNotes;
    }

    public String getType() {
        return type;
    }

    public String getType2() {
        return type2;
    }

    public String getType3() {
        return type3;
    }

    public String getSourceNotes() {
        return sourceNotes;
    }
}
