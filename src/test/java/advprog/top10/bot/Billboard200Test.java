package advprog.top10.bot;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.Before;
import org.junit.Test;



public class Billboard200Test {
    Billboard200App billboard200;
    String url = "https://www.billboard.com/charts/billboard-200";

    @Before
    public void setUp() {
        billboard200 = new Billboard200App(url);
    }

    @Test
    public void urlErrorTest() {
        new Billboard200App("https://hehe.id/");
    }

    @Test
    public void getBillboardUrl() {
        assertEquals(url, billboard200.getBillboardUrl());
    }

    @Test
    public void getTop10() {
        String output = billboard200.printTop10();
        System.out.println(output);
        assertTrue(output.contains("Post Malone"));
    }

    @Test
    public void getTop10List() {
        List<Song> list = billboard200.getTop10List();
        assertEquals(10, list.size());
    }

}
