package zonkbot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Question {

    String question;
    ArrayList<String> answers;
    int correct_answer_index;

    public Question(String question) {
        this.question = question;
        answers = new ArrayList<String>();
    }

    public void setCorrectAnswer(String string){
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).equals(string)) {
                correct_answer_index = i;
                return;
            }
        }
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
        for (int i = 0; i < answers.size() - 1; i++) {
            result += "    " + answers.get(i) + "\n";
        }
        result += "    " + answers.get(answers.size() - 1);
        return result;
    }


}
