package zonkbot;

import java.util.ArrayList;

public class Zonkbot {
    ArrayList<Question> questions;

    public Zonkbot() {
        // Default Constructor
        questions = new ArrayList<Question>();
    }

    public void add_question(Question question) {
        questions.add(question);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }


    public int getQuestionIndex(String question) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestion().equals(question)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public String toString() {
        String result = "";
        for (Question element: questions) {
            result += element.toString();
        }
        return result;
    }
}
