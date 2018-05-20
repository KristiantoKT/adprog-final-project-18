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
        int i = Integer.parseInt(string);
        correct_answer_index = i;
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
        for (int i = 0; i < answers.size(); i++) {
            result += answerStringBuilder(i);
        }
        return result;
    }

    private String answerStringBuilder(int i){
        String result = "";
        if (correct_answer_index == i && i != answers.size())
            result = "  *" + answers.get(i) + "*\n";
        else if (correct_answer_index == i && i == answers.size())
            result = "  *" + answers.get(i) + "*";
        else if (i == answers.size())
            result = "    " + answers.get(i);
        else
            result = "    " + answers.get(i) + "\n";
        return result;
    }


}
