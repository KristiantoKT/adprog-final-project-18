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


}
