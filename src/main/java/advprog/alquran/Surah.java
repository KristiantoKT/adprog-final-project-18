package advprog.alquran;

public class Surah {
    private int number;
    private String name;
    private String translation;
    private Verse[] verses;
    private String type;
    private String info;

    public Surah(int number, String name, String translation,
                 Verse[] verses, String type, String info) {
        this.number = number;
        this.name = name;
        this.translation = translation;
        this.verses = verses;
        this.type = type;
        this.info = info;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getTranslation() {
        return translation;
    }

    public Verse[] getVerses() {
        return verses;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }
}
