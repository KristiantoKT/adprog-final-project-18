package advprog.example.bot.cgv;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CgvCinemaTest {

    CgvStudioType cgvCinema;
    State defaultCinema;
    State changeCinema;

    @Before
    public void setUp() {
        cgvCinema = new CgvStudioType();
        defaultCinema = new DefaultCinema();
        changeCinema = new ChangeCinema("https://www.cgv.id/en/schedule/cinema/003");
        cgvCinema.setState(defaultCinema);
    }

    @Test
    public void testDefaultState() {
        String schedule = cgvCinema.cgvGoldClass();

        assertEquals(schedule, "(' DEADPOOL 2',[11:35 14:15 17:00 19:45 22:30])\n");
    }

    @Test
    public void testChangeState() {
        cgvCinema.setState(changeCinema);
        String schedule = cgvCinema.cgvGoldClass();

        assertEquals(schedule, "(' DEADPOOL 2',[11:00 13:40 16:20 19:00 21:40 24:20])\n");
    }

}