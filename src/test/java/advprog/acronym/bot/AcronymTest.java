package advprog.acronym.bot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AcronymTest {

    private static Acronym acronym;

    @Before
    public void setUp() {
        acronym = new Acronym("JKT48", "Je Ke Ti Forti Eight");
    }

    @Test
    public void getKependekanTest() {
        assertTrue(acronym.getKependekan().equals("JKT48"));
    }

    @Test
    public void getKepanjanganTest() {
        assertTrue(acronym.getKepanjangan().equals("Je Ke Ti Forti Eight"));
    }

    @Test
    public void acronymHasKependekan() {
        assertTrue(acronym.getKependekan() != null);
    }

    @Test
    public void acronymHasKepanjangan() {
        assertTrue(acronym.getKepanjangan() != null);
    }

    @Test
    public void acronymSetKepanjanganTest() {
        acronym.setKepanjangan("Je Ke Tee Fourti Eight");
        assertTrue(acronym.getKepanjangan().equals("Je Ke Tee Fourti Eight"));
    }

    @Test
    public void acronymSetKependekanTest() {
        acronym.setKependekan("JKT488");
        assertTrue(acronym.getKependekan().equals("JKT488"));
    }


}
