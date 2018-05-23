package zonkbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ZonkbotTest {

    private Zonkbot zonkbot = new Zonkbot();

    @Test
    void getPresentQuestion() {
        assertEquals(null, zonkbot.getPresentQuestion());
    }

    @Test
    void responseMessageAddQuestion() {
        assertEquals("Please input your question", zonkbot.responseMessage("/add_question"));
    }

    @Test
    void responseMessageChangeAnswer() {
        assertEquals("/Choose question", zonkbot.responseMessage("/change_answer"));
    }

    @Test
    void responseMessageOther() {
        assertEquals("/ayams is not a command", zonkbot.responseMessage("/ayams"));
    }

    @Test
    void responseMessageEcho() {
        assertEquals("ayam", zonkbot.responseMessage("/echo ayam"));
    }

    @Test
    void addQuestionSection() {
        assertNotNull(zonkbot.addQuestionSection("string"));
    }

    @Test
    void changeAnswer() {
        assertEquals("/Choose correct answer", zonkbot.responseMessage("/Question: 1"));
    }

    @Test
    void correctAnswer() {
        assertEquals("", zonkbot.responseMessage("/Correct answer: 2"));
    }

    @Test
    void firstAddQuestionSection() {
        zonkbot = new Zonkbot();
        assertEquals("Answer 1:", zonkbot.addQuestionSection("pertanyaan"));
    }

}
