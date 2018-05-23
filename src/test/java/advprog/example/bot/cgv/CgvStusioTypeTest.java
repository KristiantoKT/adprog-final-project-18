package advprog.example.bot.cgv;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CgvStusioTypeTest {

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

        assertEquals(schedule, "( ' DEADPOOL 2 '\nSchedules : [11:15 13:55] )\n( ' SOLO: A STAR WARS STORY '\n"+
                "Schedules : [16:35 19:30 22:25] )\n");
    }

    @Test
    public void testChangeState() {
        cgvCinema.setState(changeCinema);
        String schedule = cgvCinema.cgvGoldClass();

        assertEquals(schedule, "( ' DEADPOOL 2 '\nSchedules : [11:10 13:50 19:30] )\n( ' SOLO: A STAR WARS STORY '\n"+
                "Schedules : [16:35 22:20] )\n");
    }

}