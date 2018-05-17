package advprog.alquran;

public class Verse {
    private String surah;
    private String number;
    private String arabic;
    private String latin;
    private String translation;

    public Verse(String surah, String number, String arabic, String latin, String translation) {
        this.surah = surah;
        this.number = number;
        this.arabic = arabic;
        this.latin = latin;
        this.translation = translation;
    }

    public String getSurah() {
        return surah;
    }

    public String getNumber() {
        return number;
    }

    public String getArabic() {
        return arabic;
    }

    public String getLatin() {
        return latin;
    }

    public String getTranslation() {
        return translation;
    }
}
