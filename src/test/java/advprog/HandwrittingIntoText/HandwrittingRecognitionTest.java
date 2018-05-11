package advprog.HandwrittingIntoText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import advprog.HandwrittenIntoText.HandwrittingRecognitionController;
import org.junit.Test;

public class HandwrittingRecognitionTest {
    private static final String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Cursive_Writing_on_Notebook_paper.jpg/800px-Cursive_Writing_on_Notebook_paper.jpg";

    @Test
    public void convertImageToStringTest() {
        HandwrittingRecognitionController test = new HandwrittingRecognitionController();
        String hasil = test.convertImageToString(url);
        assertTrue(hasil.contains("dog"));
    }
}
