package advprog.similiarity;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimiliarityTest {

    @Test
    public void testObjectCorrectness() {
        double time = 1;
        double similiarity = 0.5;
        double langConfidence = 0.25;
        String lang = "en";
        String timeStamp = "2018-05-10T04:54:05.819";

        Similiarity similiarityObj = new Similiarity();

        similiarityObj.setLang(lang);
        similiarityObj.setLangConfidence(langConfidence);
        similiarityObj.setSimiliarity(similiarity);
        similiarityObj.setTime(time);
        similiarityObj.setTimeStamp(timeStamp);

        assertEquals(similiarityObj.getLang(),lang);
        assertEquals(similiarityObj.getLangConfidence(),langConfidence,0.001);
        assertEquals(similiarityObj.getSimiliarity(),similiarity,0.001);
        assertEquals(similiarityObj.getTime(),time,0.001);
        assertEquals(similiarityObj.getTimeStamp(),timeStamp);
    }
}
