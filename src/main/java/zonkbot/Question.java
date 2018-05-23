package zonkbot;

import java.util.ArrayList;

public class Question {

    String question;
    ArrayList<String> answers;
    int correctAnswerIndex;

    public Question(String question) {
        this.question = question;
        answers = new ArrayList<String>();
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswer(int index) {
        correctAnswerIndex = index;
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

    private String answerStringBuilder(int i) {
        String result;
        if (correctAnswerIndex == i && i != answers.size() - 1) {
            result = "  *" + answers.get(i) + "*\n";
        } else if (correctAnswerIndex == i && i == answers.size() - 1) {
            result = "  *" + answers.get(i) + "*";
        } else if (i == answers.size()) {
            result = "    " + answers.get(i);
        } else {
            result = "    " + answers.get(i) + "\n";
        }
        return result;
    }


}
