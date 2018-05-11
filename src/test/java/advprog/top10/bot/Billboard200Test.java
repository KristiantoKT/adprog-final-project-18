package advprog.top10.bot;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class Billboard200Test {
    Billboard200App billboard200;
    String rss ="https://www.billboard.com/rss/charts/billboard-200";

    @Before
    public void setUp() {
        billboard200 = new Billboard200App(rss);
    }

    @Test
    public void getTop10(){
        String output = billboard200.printTop10();
        assertTrue(output.contains("Post Malone"));
    }
}
