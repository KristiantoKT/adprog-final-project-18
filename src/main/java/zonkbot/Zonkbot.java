package zonkbot;

import java.util.ArrayList;

public class Zonkbot {
    ArrayList<Question> questions;
    private boolean add_question_section;
    private int answer_number;

    public Zonkbot() {
        // Default Constructor
        questions = new ArrayList<Question>();
        add_question_section = false;
        answer_number = 0;
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

    public boolean isAdd_question_section(){
        return add_question_section;
    }

    public int getAnswer_number(){
        return answer_number;
    }

    public void setAdd_question_section(boolean add_question_section){
        this.add_question_section = add_question_section;
    }

    public void setAnswer_number(int answer_number){
        this.answer_number = answer_number;
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
