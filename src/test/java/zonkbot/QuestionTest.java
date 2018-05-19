package zonkbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;


public class QuestionTest {

    private Question question = new Question("apakah adprog penting?");

    @Test
    void testQuestion() {
        question.addAnswer("ya");
        question.addAnswer("tidak");
        ArrayList<String> answers = new ArrayList<String>();
        answers.add("ya");
        answers.add("tidak");
        assertEquals(question.getAnswers(), answers);
        assertEquals("apakah adprog penting?\n"
                + "    ya\n" + "    tidak\n", question.toString());
        assertEquals("apakah adprog penting?", question.getQuestion());
    }
}
