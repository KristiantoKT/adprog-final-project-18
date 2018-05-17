package advprog.alquran;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FathimahApiTest {

    private static final String ALFATIHAH = "Al Fatihah";
    private static final String ALFATIHAHINFO =
            "Surat Al Faatihah (Pembukaan) yang diturunkan"
            + "di Mekah dan terdiri dari 7 ayat adalah surat yang pertama-tama diturunkan dengan "
            + "lengkap diantara surat-surat yang ada dalam Al Quran dan termasuk golongan surat"
            + "Makkiyyah. Surat ini disebut Al Faatihah (Pembukaan), karena dengan surat inilah"
            + "dibuka dan dimulainya Al Quran. Dinamakan Ummul Quran (induk Al Quran) atau Ummul"
            + "Kitaab (induk Al Kitaab) karena dia merupakan induk dari semua isi Al Quran, dan"
            + "karena itu diwajibkan membacanya pada tiap-tiap sembahyang.\n Dinamakan pula As "
            + "Sab'ul matsaany (tujuh yang berulang-ulang) karena ayatnya tujuh dan dibaca "
            + "berulang-ulang dalam sholat.";
    private static final String BASMALAH = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ";
    private static final String BASMALAHTRANSLATION =
            "Dengan menyebut nama Allah Yang Maha Pemurah lagi Maha Penyayang.";
    private static final String HAMDALAH = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ";
    private static final String HAMDALAHTRANSLATION =
            "Segala puji bagi Allah, Tuhan semesta alam";

    @Test
    void testRequestLegitimateSurahFromAlQuran() {
        Surah alFatihah = FathimahApi.getSurah(1);
        assertEquals(ALFATIHAH, alFatihah.getName());
        assertEquals(ALFATIHAHINFO, alFatihah.getInfo());
    }

    @Test
    void invalidSurahFromAlQuranThrowsIndexOutOfBoundException() {
        try {
            FathimahApi.getSurah(500);
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assertThat(e).hasMessage("Al-Quran hanya memiliki 117 ayat");
        }
    }

    @Test
    void testRequestLegitimateVerseFromAlQuran() {
        Verse basmalah = FathimahApi.getVerse(1, 1);
        assertEquals(BASMALAH, basmalah.getArabic());
        assertEquals(BASMALAHTRANSLATION, basmalah.getTranslation());
    }

    @Test
    void testRequestInvalidVerseFromAlQuran() {
        try {
            FathimahApi.getVerse(10,1);
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assertThat(e).hasMessage("Al Fatihah hanya terdiri dari 7 ayat");
        }
    }

    @Test
    void testRequestLegitimateVersesFromAlQuran() {
        Verse[] alFathihah = FathimahApi.getVerses(1,7, 1);
        assertEquals(BASMALAH, alFathihah[0].getArabic());
        assertEquals(BASMALAHTRANSLATION, alFathihah[0].getTranslation());
        assertEquals(HAMDALAH, alFathihah[1].getArabic());
        assertEquals(HAMDALAHTRANSLATION, alFathihah[1].getTranslation());
    }

    @Test
    void testRequestInvalidVersesFromAlQuran() {
        try {
            FathimahApi.getVerses(1, 10, 1);
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assertThat(e).hasMessage("Al Fatihah hanya terdiri dari 7 ayat");
        }
    }
}
