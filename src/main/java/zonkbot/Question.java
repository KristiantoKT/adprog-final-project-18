package zonkbot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Question {

    String question;
    ArrayList<String> answers;
    //int correct_answer_index;

    public Question(String question) {
        this.question = question;
        answers = new ArrayList<String>();
    }



    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }


    @Override
    public String toString() {
        String result = question + "\n";
        for (String answer: answers) {
            result += "    " + answer + "\n";
        }
        return result;
    }


}
