package zonkbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertEquals(question.toString(), question.toString());
        assertEquals("apakah adprog penting?", question.getQuestion());
    }

    @Test
    void getCorrectAnswerIndex() {
        assertNotNull(question.getCorrectAnswerIndex());
    }


    @Test
    void getAnswers() {
        assertNotNull(question.getAnswers());
    }

    @Test
    void getQuestion() {
        assertNotNull(question.getQuestion());
    }


}
