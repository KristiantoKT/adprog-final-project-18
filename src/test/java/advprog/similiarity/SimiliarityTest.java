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
        String url1 = "http://ur11.com";
        String url2 = "http://ur12.com";
        String text1 = "text 1";
        String text2 = "text 2";

        Similiarity similiarityObj = new Similiarity();

        similiarityObj.setLang(lang);
        similiarityObj.setLangConfidence(langConfidence);
        similiarityObj.setSimiliarity(similiarity);
        similiarityObj.setTime(time);
        similiarityObj.setTimeStamp(timeStamp);
        similiarityObj.setUrl1(url1);
        similiarityObj.setUrl2(url2);
        similiarityObj.setText1(text1);
        similiarityObj.setText2(text2);

        assertEquals(similiarityObj.getLang(),lang);
        assertEquals(similiarityObj.getLangConfidence(),langConfidence,0.001);
        assertEquals(similiarityObj.getSimiliarity(),similiarity,0.001);
        assertEquals(similiarityObj.getTime(),time,0.001);
        assertEquals(similiarityObj.getTimeStamp(),timeStamp);
        assertEquals(similiarityObj.getUrl1(),url1);
        assertEquals(similiarityObj.getUrl2(),url2);
        assertEquals(similiarityObj.getText1(),text1);
        assertEquals(similiarityObj.getText2(),text2);
    }
}
