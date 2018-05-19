package zonkbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ZonkbotTest {

    private Zonkbot zonkbot = new Zonkbot();
    private Question question = new Question("apakah adprog penting?");

    @Test
    void testZonkbotQuestion() {
        question.addAnswer("ya");
        question.addAnswer("tidak");
        zonkbot.add_question(question);
        assertEquals("apakah adprog penting?\n"
                + "    ya\n" + "    tidak\n",zonkbot.toString());
        assertEquals(0, zonkbot.getQuestionIndex("apakah adprog penting?"));
        assertEquals("apakah adprog penting?", zonkbot.getQuestions()
                .get(0).getQuestion());
    }
}
