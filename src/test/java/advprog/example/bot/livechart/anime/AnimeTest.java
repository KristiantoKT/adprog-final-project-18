package advprog.example.bot.livechart.anime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnimeTest {
    Anime anime;

    @BeforeEach
    void setUp() {
        anime = new Anime("FLCL Progressive",
                "Many years have passed since "
                        + "Naota and Haruhara Haruko shared their "
                        + "adventure together. "
                        + "Meanwhile, the war between the two entities known as Medical "
                        + "Mechanica and Fraternity "
                        + "rages across the galaxy. "
                        + "Enter Hidomi, a young teenaged girl who believes "
                        + "there is nothing amazing to expect from her "
                        + "average life, until one day when a new teacher named "
                        + "Haruko arrives at her school. "
                        + "Soon enough, "
                        + "Medical Mechanica is attacking her town and "
                        + "Hidomi discovers a secret within her that could "
                        + "save everyone, a secret that only Haruko can unlock.\n"
                        + "\n"
                        + "But why did Haruko return to Earth? "
                        + "What happened to her Rickenbacker 4001 she left with Naota? "
                        + "And where did the human-type robot ‘Canti’ go?"
        ,"Action");
    }

    @Test
    void getTitle() {

        Assertions.assertEquals("FLCL Progressive", anime.getTitle());
    }

    @Test
    void setTitle() {
        anime.setTitle("Dave");
        Assertions.assertEquals("Dave", anime.getTitle());
    }

    @Test
    void getSynopsis() {
        Assertions.assertEquals("Many years have passed since "
                + "Naota and Haruhara Haruko shared their "
                + "adventure together. "
                + "Meanwhile, the war between the two entities known as Medical "
                + "Mechanica and Fraternity "
                + "rages across the galaxy. "
                + "Enter Hidomi, a young teenaged girl who believes "
                + "there is nothing amazing to expect from her "
                + "average life, until one day when a new teacher named "
                + "Haruko arrives at her school. "
                + "Soon enough, "
                + "Medical Mechanica is attacking her town and "
                + "Hidomi discovers a secret within her that could "
                + "save everyone, a secret that only Haruko can unlock.\n"
                + "\n"
                + "But why did Haruko return to Earth? "
                + "What happened to her Rickenbacker 4001 she left with Naota? "
                + "And where did the human-type robot ‘Canti’ go?", anime.getSynopsis());
    }

    @Test
    void setSynopsis() {
        anime.setSynopsis("heheheheheh");
        Assertions.assertEquals("heheheheheh", anime.getSynopsis());
    }

    @Test
    void getGenre() {

        Assertions.assertEquals("Action", anime.getGenre());
    }

    @Test
    void setGenre() {
        anime.setGenre("Action");
        Assertions.assertEquals("Action", anime.getGenre());
    }


}
