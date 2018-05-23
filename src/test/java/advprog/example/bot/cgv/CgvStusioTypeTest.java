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
        String schedule = cgvCinema.cgv4DxCinema();

        assertEquals(schedule, "('4DX2D DEADPOOL 2',[10:50 13:25])\n");
    }

    @Test
    public void testChangeState() {
        cgvCinema.setState(changeCinema);
        String schedule = cgvCinema.cgv4DxCinema();

        assertEquals(schedule, "Sorry your cinema theater is not available");
    }


}