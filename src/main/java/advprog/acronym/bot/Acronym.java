package advprog.acronym.bot;

public class Acronym {

    private String kependekan;
    private String kepanjangan;

    public Acronym(String kependekan, String kepanjangan) {
        this.kependekan = kependekan;
        this.kepanjangan = kepanjangan;
    }

    public String getKependekan() {
        return kependekan;
    }

    public void setKependekan(String kependekan) {
        this.kependekan = kependekan;
    }

    public String getKepanjangan() {

        return kepanjangan;
    }

    public void setKepanjangan(String kepanjangan) {
        this.kepanjangan = kepanjangan;
    }
}
